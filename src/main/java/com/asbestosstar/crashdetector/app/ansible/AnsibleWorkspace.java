package com.asbestosstar.crashdetector.app.ansible;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedHashMap;
import java.util.Map;

import com.asbestosstar.crashdetector.Statics;

/**
 * Área temporal privada utilizada para playbooks integrados y callbacks.
 */
public final class AnsibleWorkspace implements AutoCloseable {

	private static final String TASK_CAPTURE = "CrashDetector capture remote log";

	private final Path root;
	private final Path project;
	private final Path env;
	private final Path output;
	private final Path artifacts;
	private boolean keep;

	private AnsibleWorkspace(Path root, Path project, Path env, Path output, Path artifacts, boolean keep) {
		this.root = root;
		this.project = project;
		this.env = env;
		this.output = output;
		this.artifacts = artifacts;
		this.keep = keep;
	}

	public static AnsibleWorkspace crearParaLogs(AnsibleLogRequest request) throws AnsibleIntegrationException {
		try {
			Path base = obtenerBase();
			Files.createDirectories(base);
			Path root = Files.createTempDirectory(base, "ansible-");
			Path project = Files.createDirectories(root.resolve("project"));
			Path env = Files.createDirectories(root.resolve("env"));
			Path output = Files.createDirectories(root.resolve("captured_logs"));
			Path artifacts = Files.createDirectories(root.resolve("artifacts"));
			Path callbacks = Files.createDirectories(project.resolve("callback_plugins"));

			AnsibleWorkspace workspace = new AnsibleWorkspace(root, project, env, output, artifacts,
					request.getOptions().isKeepArtifacts());
			workspace.escribirConfiguracion(callbacks);
			workspace.escribirCallback(callbacks);
			workspace.escribirPlaybookLogs(request);
			return workspace;
		} catch (IOException e) {
			throw new AnsibleIntegrationException("No se pudo crear el área temporal de Ansible.", e);
		}
	}

	private static Path obtenerBase() {
		try {
			if (Statics.carpeta != null) {
				return Statics.carpeta.resolve("ansible_cli_temporales").toAbsolutePath().normalize();
			}
		} catch (Throwable ignorado) {
		}
		return new File("ansible_cli_temporales").toPath().toAbsolutePath().normalize();
	}

	private void escribirConfiguracion(Path callbacks) throws IOException {
		String rutaCallbacks = callbacks.toAbsolutePath().normalize().toString();
		String contenido = "[defaults]" + System.lineSeparator() + "callback_plugins = " + rutaCallbacks
				+ System.lineSeparator() + "callbacks_enabled = crashdetector_capture" + System.lineSeparator()
				+ "callback_whitelist = crashdetector_capture" + System.lineSeparator() + "retry_files_enabled = False"
				+ System.lineSeparator() + "nocows = True" + System.lineSeparator() + "force_color = False"
				+ System.lineSeparator();
		Files.write(project.resolve("ansible.cfg"), contenido.getBytes(StandardCharsets.UTF_8));
	}

	private void escribirCallback(Path callbacks) throws IOException {
		String python = "from __future__ import absolute_import, division, print_function\n" + "__metaclass__ = type\n"
				+ "from ansible.plugins.callback import CallbackBase\n" + "import hashlib\n" + "import os\n"
				+ "import re\n" + "\n" + "class CallbackModule(CallbackBase):\n" + "    CALLBACK_VERSION = 2.0\n"
				+ "    CALLBACK_TYPE = 'aggregate'\n" + "    CALLBACK_NAME = 'crashdetector_capture'\n"
				+ "    NEEDS_ENABLED = True\n" + "    CALLBACK_NEEDS_WHITELIST = True\n" + "\n"
				+ "    def v2_runner_on_ok(self, result):\n" + "        try:\n"
				+ "            task_name = result._task.get_name().strip()\n"
				+ "            if not task_name.endswith('" + TASK_CAPTURE + "'):\n" + "                return\n"
				+ "            output_dir = os.environ.get('CRASHDETECTOR_ANSIBLE_OUTPUT_DIR')\n"
				+ "            if not output_dir:\n" + "                return\n"
				+ "            host = result._host.get_name()\n"
				+ "            value = result._result.get('stdout', '')\n" + "            if value is None:\n"
				+ "                value = ''\n" + "            if not isinstance(value, str):\n"
				+ "                value = str(value)\n"
				+ "            encoded = value.encode('utf-8', errors='replace')\n"
				+ "            max_bytes = int(os.environ.get('CRASHDETECTOR_ANSIBLE_MAX_HOST_BYTES', '67108864'))\n"
				+ "            safe = re.sub(r'[^A-Za-z0-9_.-]', '_', host)[:120] or 'host'\n"
				+ "            suffix = hashlib.sha256(host.encode('utf-8', errors='replace')).hexdigest()[:12]\n"
				+ "            base = safe + '-' + suffix\n" + "            if len(encoded) > max_bytes:\n"
				+ "                marker = os.path.join(output_dir, base + '.too-large')\n"
				+ "                with open(marker, 'w', encoding='utf-8') as handle:\n"
				+ "                    handle.write(str(len(encoded)))\n" + "                return\n"
				+ "            remote_path = os.environ.get('CRASHDETECTOR_ANSIBLE_REMOTE_PATH', '')\n"
				+ "            target = os.path.join(output_dir, base + '.log')\n"
				+ "            with open(target, 'w', encoding='utf-8', errors='replace') as handle:\n"
				+ "                handle.write('CrashDetector Ansible source\\n')\n"
				+ "                handle.write('Host: ' + host + '\\n')\n"
				+ "                handle.write('Path: ' + remote_path + '\\n\\n')\n"
				+ "                handle.write(value)\n" + "                if value and not value.endswith('\\n'):\n"
				+ "                    handle.write('\\n')\n" + "        except Exception as exc:\n"
				+ "            output_dir = os.environ.get('CRASHDETECTOR_ANSIBLE_OUTPUT_DIR')\n"
				+ "            if output_dir:\n"
				+ "                with open(os.path.join(output_dir, 'callback-error.txt'), 'a', encoding='utf-8') as handle:\n"
				+ "                    handle.write(str(exc) + '\\n')\n";
		Files.write(callbacks.resolve("crashdetector_capture.py"), python.getBytes(StandardCharsets.UTF_8));
	}

	private void escribirPlaybookLogs(AnsibleLogRequest request) throws IOException {
		String ruta = yamlSingleQuoted(request.getRemotePath());
		StringBuilder yaml = new StringBuilder();
		yaml.append("---\n");
		yaml.append("- name: CrashDetector read-only remote log collection\n");
		yaml.append("  hosts: all\n");
		yaml.append("  gather_facts: false\n");
		yaml.append("  tasks:\n");
		yaml.append("    - name: CrashDetector verify remote log\n");
		yaml.append("      ansible.builtin.stat:\n");
		yaml.append("        path: ").append(ruta).append("\n");
		yaml.append("        follow: true\n");
		yaml.append("      register: crashdetector_log_stat\n");
		yaml.append("      changed_when: false\n");
		yaml.append("\n");
		yaml.append("    - name: CrashDetector reject missing or non-regular log\n");
		yaml.append("      ansible.builtin.fail:\n");
		yaml.append("        msg: 'CrashDetector can only collect an existing regular file.'\n");
		yaml.append("      when: not crashdetector_log_stat.stat.exists or not crashdetector_log_stat.stat.isreg\n");
		yaml.append("\n");
		yaml.append("    - name: ").append(TASK_CAPTURE).append("\n");
		yaml.append("      ansible.builtin.command:\n");
		yaml.append("        argv:\n");
		if ("all".equalsIgnoreCase(request.getTail())) {
			yaml.append("          - cat\n");
			yaml.append("          - ").append(ruta).append("\n");
		} else {
			yaml.append("          - tail\n");
			yaml.append("          - -n\n");
			yaml.append("          - '").append(request.getTail()).append("'\n");
			yaml.append("          - ").append(ruta).append("\n");
		}
		yaml.append("      register: crashdetector_log_capture\n");
		yaml.append("      changed_when: false\n");

		Files.write(project.resolve("crashdetector-collect-logs.yml"),
				yaml.toString().getBytes(StandardCharsets.UTF_8));
	}

	private static String yamlSingleQuoted(String valor) {
		return "'" + valor.replace("'", "''") + "'";
	}

	public Map<String, String> entorno(AnsibleLogRequest request) {
		Map<String, String> entorno = new LinkedHashMap<String, String>();
		entorno.put("ANSIBLE_CONFIG", project.resolve("ansible.cfg").toAbsolutePath().toString());
		entorno.put("ANSIBLE_CALLBACK_PLUGINS", project.resolve("callback_plugins").toAbsolutePath().toString());
		entorno.put("ANSIBLE_CALLBACKS_ENABLED", "crashdetector_capture");
		entorno.put("ANSIBLE_CALLBACK_WHITELIST", "crashdetector_capture");
		entorno.put("ANSIBLE_LOAD_CALLBACK_PLUGINS", "1");
		entorno.put("ANSIBLE_NOCOLOR", "1");
		entorno.put("NO_COLOR", "1");
		entorno.put("CRASHDETECTOR_ANSIBLE_OUTPUT_DIR", output.toAbsolutePath().toString());
		entorno.put("CRASHDETECTOR_ANSIBLE_REMOTE_PATH", request.getRemotePath());
		entorno.put("CRASHDETECTOR_ANSIBLE_MAX_HOST_BYTES", Long.toString(request.getOptions().getMaxBytes()));
		return entorno;
	}

	public Path getRoot() {
		return root;
	}

	public Path getProject() {
		return project;
	}

	public Path getEnv() {
		return env;
	}

	public Path getOutput() {
		return output;
	}

	public Path getArtifacts() {
		return artifacts;
	}

	public Path getLogPlaybook() {
		return project.resolve("crashdetector-collect-logs.yml");
	}

	public void conservar() {
		keep = true;
	}

	@Override
	public void close() throws AnsibleIntegrationException {
		if (keep) {
			System.out.println("Artefactos Ansible conservados en: " + root.toAbsolutePath());
			return;
		}
		eliminarRecursivamente(root);
	}

	public static void eliminarRecursivamente(Path root) throws AnsibleIntegrationException {
		if (root == null || !Files.exists(root)) {
			return;
		}
		try {
			Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.deleteIfExists(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					if (exc != null) {
						throw exc;
					}
					Files.deleteIfExists(dir);
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			throw new AnsibleIntegrationException("No se pudieron eliminar los temporales Ansible: " + root, e);
		}
	}
}

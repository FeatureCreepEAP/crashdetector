package com.asbestosstar.crashdetector.app.ansible;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * Parser de las integraciones opcionales con Ansible.
 */
public final class AnsibleCli {

	private AnsibleCli() {
	}

	public static boolean esComandoAnsible(String command) {
		if (command == null) {
			return false;
		}
		String normalized = command.toLowerCase(Locale.ROOT);
		return "ansible".equals(normalized) || "automation".equals(normalized) || "automatizacion".equals(normalized)
				|| "automatización".equals(normalized);
	}

	/**
	 * @return true when an analysis input was prepared and normal application
	 *         startup must continue; false for help, status and errors.
	 */
	public static boolean procesar(String[] args) {
		try {
			if (args == null || args.length < 2 || esAyuda(args[1])) {
				mostrarAyuda();
				return false;
			}

			String action = normalizar(args[1]);
			if (contieneAyuda(args, 2)) {
				mostrarAyuda();
				return false;
			}
			if ("status".equals(action) || "estado".equals(action)) {
				procesarStatus(args, 2);
				return false;
			}
			if ("logs".equals(action) || "log".equals(action) || "collect-logs".equals(action)
					|| "recolectar-logs".equals(action)) {
				AnsibleLogRequest request = parsearLogs(args, 2);
				AnsibleLogCollectionResult result = new AnsibleLogCollector().collect(request);
				System.out.println("Logs Ansible preparados para análisis: " + result.getHostCount() + " host(s), "
						+ result.getBytes() + " bytes.");
				System.out.println("Motor: " + result.getEngine());
				System.out.println("Comando ejecutado: " + result.getCommand());
				return true;
			}
			if ("playbook".equals(action) || "run-playbook".equals(action) || "ejecutar-playbook".equals(action)) {
				AnsiblePlaybookRequest request = parsearPlaybook(args, 2);
				AnsibleCommandResult result = new AnsibleExternalPlaybookExecutor().execute(request);
				if (result.getExitCode() == 0) {
					System.out.println("Salida del playbook preparada para análisis.");
				} else {
					System.err.println("El playbook terminó con código " + result.getExitCode()
							+ "; su salida fue preparada para análisis.");
				}
				System.out.println("Comando ejecutado: " + result.getComandoVisible());
				return true;
			}

			throw new AnsibleIntegrationException(
					"Acción Ansible desconocida: " + args[1] + ". Use logs, playbook, status o --help.");
		} catch (AnsibleIntegrationException e) {
			System.err.println("Error de integración Ansible: " + e.getMessage());
			return false;
		}
	}

	public static void mostrarAyuda() {
		System.out.println("Integración opcional con Ansible:");
		System.out.println("  ansible status [opciones de binario]");
		System.out.println("  ansible logs --inventory <ruta> --path <log-remoto> [opciones]");
		System.out.println(
				"  ansible playbook --inventory <ruta> --playbook <archivo> --allow-external-playbook [opciones]");
		System.out.println();
		System.out.println("ansible logs recopila una entrada independiente por host mediante un playbook");
		System.out.println("integrado de solo lectura. Prefiere ansible-runner y usa ansible-playbook como fallback.");
		System.out.println();
		System.out.println("Opciones de logs:");
		System.out.println("  -i, --inventory <ruta>       Inventario obligatorio.");
		System.out.println("  -l, --hosts, --limit <patrón> Hosts o grupo. Predeterminado: all.");
		System.out.println("  --path <ruta-absoluta>        Archivo remoto Unix que se leerá.");
		System.out.println("  --tail <n|all>                Últimas líneas. Predeterminado: 5000.");
		System.out.println("  --engine <auto|runner|playbook>");
		System.out.println("  --keep-artifacts              Conserva el workspace para diagnóstico.");
		System.out.println();
		System.out.println("Opciones comunes:");
		System.out.println("  -u, --user <usuario>");
		System.out.println("  --private-key <archivo>");
		System.out.println("  -b, --become");
		System.out.println("  --become-user <usuario>");
		System.out.println("  -f, --forks <n>               1..1000. Predeterminado: 5.");
		System.out.println("  --extra-vars-file <archivo>   YAML/JSON; no se aceptan secretos inline.");
		System.out.println("  --vault-password-file <archivo>");
		System.out.println("  --timeout-seconds <n>         1..86400. Predeterminado: 300.");
		System.out.println("  --max-mib <n>                 1..1024. Predeterminado: 64.");
		System.out.println("  --runner-binary <ruta>");
		System.out.println("  --playbook-binary <ruta>");
		System.out.println();
		System.out.println("Opciones de playbook externo:");
		System.out.println("  --project-dir <directorio>    Raíz de roles, includes y ansible.cfg.");
		System.out.println("  --allow-external-playbook     Obligatorio porque el playbook puede modificar hosts.");
		System.out.println("  --check                       Solicita check mode.");
		System.out.println("  --syntax-check                Solo comprueba la sintaxis.");
		System.out.println();
		System.out.println(
				"No se permiten prompts interactivos. Use SSH agent, inventario, vault o archivos de contraseña.");
		System.out.println("Binarios: opción explícita, propiedad Java, variable de entorno, deps/bin y PATH.");
	}

	private static void procesarStatus(String[] args, int start) throws AnsibleIntegrationException {
		String runner = null;
		String playbook = null;
		for (int i = start; i < args.length; i++) {
			Option option = Option.parse(args[i]);
			if ("--runner-binary".equals(option.name)) {
				runner = requiredValue(args, option, ++i);
				if (option.inlineValue != null) {
					i--;
				}
			} else if ("--playbook-binary".equals(option.name)) {
				playbook = requiredValue(args, option, ++i);
				if (option.inlineValue != null) {
					i--;
				}
			} else if (esAyuda(option.name)) {
				mostrarAyuda();
				return;
			} else {
				throw new AnsibleIntegrationException("Opción desconocida de status: " + args[i]);
			}
		}
		AnsibleStatus.mostrar(runner, playbook);
	}

	private static AnsibleLogRequest parsearLogs(String[] args, int start) throws AnsibleIntegrationException {
		AnsibleCommonOptions options = new AnsibleCommonOptions();
		String remotePath = null;
		String tail = "5000";

		for (int i = start; i < args.length; i++) {
			String raw = args[i];
			Option option = Option.parse(raw);
			String name = option.name;

			if ("-i".equals(name) || "--inventory".equals(name)) {
				String value = requiredValue(args, option, ++i);
				if (option.inlineValue != null)
					i--;
				options.setInventory(validarEntradaInventario(value));
			} else if ("-l".equals(name) || "--hosts".equals(name) || "--limit".equals(name)) {
				String value = requiredValue(args, option, ++i);
				if (option.inlineValue != null)
					i--;
				options.setLimit(validarTexto(value, name));
			} else if ("--path".equals(name) || "--log-path".equals(name)) {
				String value = requiredValue(args, option, ++i);
				if (option.inlineValue != null)
					i--;
				remotePath = validarRutaRemota(value);
			} else if ("--tail".equals(name)) {
				String value = requiredValue(args, option, ++i);
				if (option.inlineValue != null)
					i--;
				tail = validarTail(value);
			} else if ("--engine".equals(name)) {
				String value = requiredValue(args, option, ++i);
				if (option.inlineValue != null)
					i--;
				options.setEngine(AnsibleEngine.parsear(value));
			} else if ("--keep-artifacts".equals(name)) {
				ensureNoInlineValue(option);
				options.setKeepArtifacts(true);
			} else {
				i = parsearOpcionComun(args, i, option, options);
			}
		}

		validarComunes(options);
		if (remotePath == null) {
			throw new AnsibleIntegrationException("Falta --path <archivo-remoto>.");
		}
		return new AnsibleLogRequest(options, remotePath, tail);
	}

	private static AnsiblePlaybookRequest parsearPlaybook(String[] args, int start) throws AnsibleIntegrationException {
		AnsibleCommonOptions options = new AnsibleCommonOptions();
		File playbook = null;
		File project = null;
		boolean allowed = false;
		boolean check = false;
		boolean syntaxCheck = false;

		for (int i = start; i < args.length; i++) {
			String raw = args[i];
			Option option = Option.parse(raw);
			String name = option.name;

			if ("-i".equals(name) || "--inventory".equals(name)) {
				String value = requiredValue(args, option, ++i);
				if (option.inlineValue != null)
					i--;
				options.setInventory(validarEntradaInventario(value));
			} else if ("-l".equals(name) || "--hosts".equals(name) || "--limit".equals(name)) {
				String value = requiredValue(args, option, ++i);
				if (option.inlineValue != null)
					i--;
				options.setLimit(validarTexto(value, name));
			} else if ("--playbook".equals(name)) {
				String value = requiredValue(args, option, ++i);
				if (option.inlineValue != null)
					i--;
				playbook = validarArchivo(value, name);
			} else if ("--project-dir".equals(name)) {
				String value = requiredValue(args, option, ++i);
				if (option.inlineValue != null)
					i--;
				project = validarDirectorio(value, name);
			} else if ("--allow-external-playbook".equals(name)) {
				ensureNoInlineValue(option);
				allowed = true;
			} else if ("--check".equals(name)) {
				ensureNoInlineValue(option);
				check = true;
			} else if ("--syntax-check".equals(name)) {
				ensureNoInlineValue(option);
				syntaxCheck = true;
			} else if ("--engine".equals(name) || "--runner-binary".equals(name) || "--keep-artifacts".equals(name)) {
				throw new AnsibleIntegrationException(
						name + " solo se admite con 'ansible logs'. Los playbooks externos usan ansible-playbook.");
			} else {
				i = parsearOpcionComun(args, i, option, options);
			}
		}

		validarComunes(options);
		if (playbook == null) {
			throw new AnsibleIntegrationException("Falta --playbook <archivo>.");
		}
		if (project == null) {
			project = playbook.getParentFile();
		}
		asegurarDentroDelProyecto(playbook, project);
		return new AnsiblePlaybookRequest(options, playbook, project, allowed, check, syntaxCheck);
	}

	private static int parsearOpcionComun(String[] args, int index, Option option, AnsibleCommonOptions options)
			throws AnsibleIntegrationException {
		String name = option.name;
		if ("-u".equals(name) || "--user".equals(name)) {
			String value = requiredValue(args, option, index + 1);
			options.setRemoteUser(validarTexto(value, name));
			return option.inlineValue == null ? index + 1 : index;
		}
		if ("--private-key".equals(name)) {
			String value = requiredValue(args, option, index + 1);
			options.setPrivateKey(validarArchivo(value, name));
			return option.inlineValue == null ? index + 1 : index;
		}
		if ("-b".equals(name) || "--become".equals(name)) {
			ensureNoInlineValue(option);
			options.setBecome(true);
			return index;
		}
		if ("--become-user".equals(name)) {
			String value = requiredValue(args, option, index + 1);
			options.setBecomeUser(validarTexto(value, name));
			options.setBecome(true);
			return option.inlineValue == null ? index + 1 : index;
		}
		if ("-f".equals(name) || "--forks".equals(name)) {
			String value = requiredValue(args, option, index + 1);
			options.setForks((int) parseLongRange(value, name, 1L, 1000L));
			return option.inlineValue == null ? index + 1 : index;
		}
		if ("--timeout-seconds".equals(name)) {
			String value = requiredValue(args, option, index + 1);
			options.setTimeoutSeconds(parseLongRange(value, name, 1L, 86400L));
			return option.inlineValue == null ? index + 1 : index;
		}
		if ("--max-bytes".equals(name)) {
			String value = requiredValue(args, option, index + 1);
			options.setMaxBytes(parseLongRange(value, name, 1024L, 1024L * 1024L * 1024L));
			return option.inlineValue == null ? index + 1 : index;
		}
		if ("--max-mib".equals(name)) {
			String value = requiredValue(args, option, index + 1);
			long mib = parseLongRange(value, name, 1L, 1024L);
			options.setMaxBytes(mib * 1024L * 1024L);
			return option.inlineValue == null ? index + 1 : index;
		}
		if ("--runner-binary".equals(name)) {
			String value = requiredValue(args, option, index + 1);
			options.setRunnerBinary(validarTexto(value, name));
			return option.inlineValue == null ? index + 1 : index;
		}
		if ("--playbook-binary".equals(name)) {
			String value = requiredValue(args, option, index + 1);
			options.setPlaybookBinary(validarTexto(value, name));
			return option.inlineValue == null ? index + 1 : index;
		}
		if ("--extra-vars-file".equals(name)) {
			String value = requiredValue(args, option, index + 1);
			options.setExtraVarsFile(validarArchivo(value, name));
			return option.inlineValue == null ? index + 1 : index;
		}
		if ("--vault-password-file".equals(name)) {
			String value = requiredValue(args, option, index + 1);
			options.setVaultPasswordFile(validarArchivo(value, name));
			return option.inlineValue == null ? index + 1 : index;
		}

		throw new AnsibleIntegrationException("Opción Ansible desconocida: " + name);
	}

	private static void validarComunes(AnsibleCommonOptions options) throws AnsibleIntegrationException {
		if (options.getInventory() == null) {
			throw new AnsibleIntegrationException("Falta --inventory <ruta>.");
		}
		if (options.getBecomeUser() != null && !options.isBecome()) {
			throw new AnsibleIntegrationException("--become-user requiere --become.");
		}
	}

	private static File validarEntradaInventario(String value) throws AnsibleIntegrationException {
		File file = canonical(value, "--inventory");
		if (!file.exists()) {
			throw new AnsibleIntegrationException("El inventario no existe: " + file.getAbsolutePath());
		}
		if (!file.canRead()) {
			throw new AnsibleIntegrationException("El inventario no se puede leer: " + file.getAbsolutePath());
		}
		return file;
	}

	private static File validarArchivo(String value, String option) throws AnsibleIntegrationException {
		File file = canonical(value, option);
		if (!file.exists() || !file.isFile()) {
			throw new AnsibleIntegrationException(option + " no es un archivo: " + file.getAbsolutePath());
		}
		if (!file.canRead()) {
			throw new AnsibleIntegrationException(option + " no se puede leer: " + file.getAbsolutePath());
		}
		return file;
	}

	private static File validarDirectorio(String value, String option) throws AnsibleIntegrationException {
		File file = canonical(value, option);
		if (!file.exists() || !file.isDirectory()) {
			throw new AnsibleIntegrationException(option + " no es un directorio: " + file.getAbsolutePath());
		}
		if (!file.canRead()) {
			throw new AnsibleIntegrationException(option + " no se puede leer: " + file.getAbsolutePath());
		}
		return file;
	}

	private static File canonical(String value, String option) throws AnsibleIntegrationException {
		value = validarTexto(value, option);
		try {
			return new File(value).getCanonicalFile();
		} catch (IOException e) {
			throw new AnsibleIntegrationException("No se pudo resolver " + option + ": " + value, e);
		}
	}

	private static String validarRutaRemota(String value) throws AnsibleIntegrationException {
		value = validarTexto(value, "--path");
		if (!value.startsWith("/")) {
			throw new AnsibleIntegrationException("--path debe ser una ruta Unix absoluta.");
		}
		return value;
	}

	private static String validarTail(String value) throws AnsibleIntegrationException {
		value = validarTexto(value, "--tail");
		if ("all".equalsIgnoreCase(value)) {
			return "all";
		}
		long number = parseLongRange(value, "--tail", 1L, 10000000L);
		return Long.toString(number);
	}

	private static String validarTexto(String value, String name) throws AnsibleIntegrationException {
		if (value == null || value.trim().isEmpty()) {
			throw new AnsibleIntegrationException("El valor de " + name + " está vacío.");
		}
		if (value.indexOf('\0') >= 0 || value.indexOf('\n') >= 0 || value.indexOf('\r') >= 0) {
			throw new AnsibleIntegrationException("El valor de " + name + " contiene caracteres no permitidos.");
		}
		return value;
	}

	private static long parseLongRange(String value, String name, long min, long max)
			throws AnsibleIntegrationException {
		try {
			long number = Long.parseLong(value);
			if (number < min || number > max) {
				throw new NumberFormatException();
			}
			return number;
		} catch (NumberFormatException e) {
			throw new AnsibleIntegrationException(name + " debe estar entre " + min + " y " + max + ".");
		}
	}

	private static String requiredValue(String[] args, Option option, int proposedIndex)
			throws AnsibleIntegrationException {
		if (option.inlineValue != null) {
			return option.inlineValue;
		}
		if (proposedIndex >= args.length) {
			throw new AnsibleIntegrationException("Falta el valor de " + option.name + ".");
		}
		return args[proposedIndex];
	}

	private static void ensureNoInlineValue(Option option) throws AnsibleIntegrationException {
		if (option.inlineValue != null) {
			throw new AnsibleIntegrationException(option.name + " no acepta un valor.");
		}
	}

	private static void asegurarDentroDelProyecto(File playbook, File project) throws AnsibleIntegrationException {
		try {
			String playbookPath = playbook.getCanonicalPath();
			String projectPath = project.getCanonicalPath();
			String prefix = projectPath.endsWith(File.separator) ? projectPath : projectPath + File.separator;
			if (!playbookPath.startsWith(prefix)) {
				throw new AnsibleIntegrationException(
						"El playbook debe estar dentro de --project-dir para conservar roles e includes relativos.");
			}
		} catch (IOException e) {
			throw new AnsibleIntegrationException("No se pudo validar el directorio del playbook.", e);
		}
	}

	private static boolean contieneAyuda(String[] args, int start) {
		for (int i = start; i < args.length; i++) {
			if (esAyuda(args[i])) {
				return true;
			}
		}
		return false;
	}

	private static boolean esAyuda(String value) {
		String normalized = normalizar(value);
		return "--help".equals(normalized) || "-h".equals(normalized) || "help".equals(normalized);
	}

	private static String normalizar(String value) {
		return value == null ? "" : value.toLowerCase(Locale.ROOT);
	}

	private static final class Option {
		private final String name;
		private final String inlineValue;

		private Option(String name, String inlineValue) {
			this.name = name;
			this.inlineValue = inlineValue;
		}

		private static Option parse(String raw) throws AnsibleIntegrationException {
			if (raw == null || raw.trim().isEmpty()) {
				throw new AnsibleIntegrationException("Se recibió un argumento Ansible vacío.");
			}
			int equals = raw.indexOf('=');
			if (equals > 0 && raw.startsWith("--")) {
				return new Option(raw.substring(0, equals), raw.substring(equals + 1));
			}
			return new Option(raw, null);
		}
	}
}

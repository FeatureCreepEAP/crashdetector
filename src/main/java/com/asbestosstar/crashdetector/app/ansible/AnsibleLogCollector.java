package com.asbestosstar.crashdetector.app.ansible;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.app.ansible.playbook.AnsiblePlaybookAdapter;
import com.asbestosstar.crashdetector.app.ansible.runner.AnsibleRunnerAdapter;

/**
 * Recopila logs por host y los entrega como entradas independientes.
 */
public final class AnsibleLogCollector {

	public AnsibleLogCollectionResult collect(AnsibleLogRequest request) throws AnsibleIntegrationException {
		AnsibleWorkspace workspace = AnsibleWorkspace.crearParaLogs(request);
		try {
			AnsibleEngine engine = resolverEngine(request);
			AnsibleCommandResult commandResult;
			String engineName;

			if (engine == AnsibleEngine.RUNNER) {
				commandResult = new AnsibleRunnerAdapter().ejecutar(request, workspace);
				engineName = "ansible-runner";
			} else {
				commandResult = new AnsiblePlaybookAdapter().ejecutarLogs(request, workspace);
				engineName = "ansible-playbook";
			}

			if (commandResult.getExitCode() != 0) {
				throw new AnsibleIntegrationException(formatearErrorComando(commandResult));
			}

			List<Path> logs = validarYListarLogs(workspace.getOutput(), request.getOptions().getMaxBytes());
			List<String> temporalesCrashDetector = new ArrayList<String>();
			long bytes = 0L;

			try {
				for (Path log : logs) {
					byte[] contenido = Files.readAllBytes(log);
					bytes += contenido.length;
					String texto = new String(contenido, StandardCharsets.UTF_8);
					Path temporal = MonitorDePID.crearArchivoTemporalTextoCLI(texto);
					temporalesCrashDetector.add(temporal.toFile().getCanonicalPath());
				}

				MonitorDePID.configurarEntradasCLI(Collections.<String>emptyList(), temporalesCrashDetector);
				MonitorDePID.forzarIdeMode = true;
			} catch (IOException e) {
				eliminarTemporales(temporalesCrashDetector);
				throw new AnsibleIntegrationException("No se pudieron preparar los logs recopilados.", e);
			} catch (RuntimeException e) {
				eliminarTemporales(temporalesCrashDetector);
				throw new AnsibleIntegrationException("No se pudieron registrar los logs recopilados.", e);
			}

			return new AnsibleLogCollectionResult(engineName, commandResult.getComandoVisible(), logs.size(), bytes);
		} finally {
			try {
				workspace.close();
			} catch (AnsibleIntegrationException e) {
				System.err.println("Advertencia: " + e.getMessage());
			}
		}
	}

	private static AnsibleEngine resolverEngine(AnsibleLogRequest request) throws AnsibleIntegrationException {
		AnsibleEngine requested = request.getOptions().getEngine();
		if (requested == AnsibleEngine.RUNNER) {
			String runner = AnsibleBinaryResolver.resolverRunner(request.getOptions().getRunnerBinary());
			if (!AnsibleBinaryResolver.estaDisponible(runner)) {
				throw new AnsibleIntegrationException("ansible-runner no está disponible: " + runner);
			}
			return AnsibleEngine.RUNNER;
		}
		if (requested == AnsibleEngine.PLAYBOOK) {
			String playbook = AnsibleBinaryResolver.resolverPlaybook(request.getOptions().getPlaybookBinary());
			if (!AnsibleBinaryResolver.estaDisponible(playbook)) {
				throw new AnsibleIntegrationException("ansible-playbook no está disponible: " + playbook);
			}
			return AnsibleEngine.PLAYBOOK;
		}

		String runner = AnsibleBinaryResolver.resolverRunner(request.getOptions().getRunnerBinary());
		if (AnsibleBinaryResolver.estaDisponible(runner)) {
			return AnsibleEngine.RUNNER;
		}

		String playbook = AnsibleBinaryResolver.resolverPlaybook(request.getOptions().getPlaybookBinary());
		if (AnsibleBinaryResolver.estaDisponible(playbook)) {
			return AnsibleEngine.PLAYBOOK;
		}

		throw new AnsibleIntegrationException(
				"No se encontró ansible-runner ni ansible-playbook. Use deps/bin, PATH o las opciones --runner-binary/--playbook-binary.");
	}

	private static List<Path> validarYListarLogs(Path output, long maxBytes) throws AnsibleIntegrationException {
		Path callbackError = output.resolve("callback-error.txt");
		if (Files.isRegularFile(callbackError)) {
			try {
				throw new AnsibleIntegrationException("El callback de captura falló: "
						+ new String(Files.readAllBytes(callbackError), StandardCharsets.UTF_8).trim());
			} catch (IOException e) {
				throw new AnsibleIntegrationException("El callback de captura de Ansible falló.", e);
			}
		}

		try (DirectoryStream<Path> markers = Files.newDirectoryStream(output, "*.too-large")) {
			for (Path marker : markers) {
				String bytes = new String(Files.readAllBytes(marker), StandardCharsets.UTF_8).trim();
				throw new AnsibleIntegrationException(
						"Un host devolvió un log mayor que el límite permitido (" + bytes + " bytes).");
			}
		} catch (IOException e) {
			throw new AnsibleIntegrationException("No se pudieron revisar los límites de los logs Ansible.", e);
		}

		List<Path> logs = new ArrayList<Path>();
		long total = 0L;
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(output, "*.log")) {
			for (Path path : stream) {
				if (!Files.isRegularFile(path)) {
					continue;
				}
				long size = Files.size(path);
				total += size;
				if (total > maxBytes) {
					throw new AnsibleIntegrationException(
							"Los logs recopilados superaron el límite total de " + maxBytes + " bytes.");
				}
				logs.add(path);
			}
		} catch (IOException e) {
			throw new AnsibleIntegrationException("No se pudieron leer los logs producidos por Ansible.", e);
		}

		Collections.sort(logs, new Comparator<Path>() {
			@Override
			public int compare(Path a, Path b) {
				return a.getFileName().toString().compareTo(b.getFileName().toString());
			}
		});

		if (logs.isEmpty()) {
			throw new AnsibleIntegrationException(
					"Ansible terminó correctamente, pero no produjo ningún log por host. Revise el patrón --hosts y el callback.");
		}
		return logs;
	}

	private static String formatearErrorComando(AnsibleCommandResult result) {
		String detalle = result.salidaCombinada().trim();
		if (detalle.length() > 8000) {
			detalle = detalle.substring(0, 8000) + System.lineSeparator() + "[salida truncada]";
		}
		return "Ansible terminó con código " + result.getExitCode() + ": " + result.getComandoVisible()
				+ (detalle.isEmpty() ? "" : System.lineSeparator() + detalle);
	}

	private static void eliminarTemporales(List<String> paths) {
		for (String path : paths) {
			try {
				Files.deleteIfExists(new java.io.File(path).toPath());
			} catch (IOException ignored) {
			}
		}
	}
}

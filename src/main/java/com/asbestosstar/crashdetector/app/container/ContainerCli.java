package com.asbestosstar.crashdetector.app.container;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Locale;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.app.container.docker.DockerContainerLogs;
import com.asbestosstar.crashdetector.app.container.kubernetes.KubernetesContainerLogs;
import com.asbestosstar.crashdetector.app.container.openshift.OpenShiftContainerLogs;
import com.asbestosstar.crashdetector.app.container.podman.PodmanContainerLogs;
import com.asbestosstar.crashdetector.app.container.rancher.RancherContainerLogs;
import com.asbestosstar.crashdetector.app.container.solaris.SolarisZoneContainerLogs;

/**
 * Parser y enrutador de las integraciones opcionales de contenedores.
 */
public final class ContainerCli {

	private ContainerCli() {
	}

	public static boolean esComandoContainer(String comando) {
		if (comando == null) {
			return false;
		}
		String normalizado = comando.toLowerCase(Locale.ROOT);
		return "container".equals(normalizado) || "containers".equals(normalizado) || "contenedor".equals(normalizado)
				|| "contenedores".equals(normalizado) || esPlataforma(normalizado);
	}

	/**
	 * @return true cuando los logs fueron preparados y CrashDetector debe continuar
	 *         arrancando; false cuando se mostró ayuda o ocurrió un error.
	 */
	public static boolean procesar(String[] args) {
		try {
			Indices indices = resolverIndices(args);

			if (indices.soloAyuda) {
				mostrarAyuda();
				return false;
			}

			ContainerLogProvider proveedor = proveedor(indices.plataforma);

			if (!esAccionLogs(indices.accion)) {
				throw new ContainerIntegrationException(
						"Acción de contenedor desconocida: " + indices.accion + ". Use 'logs'.");
			}

			ContainerLogRequest solicitud = parsearSolicitud(args, indices.primerArgumento);
			ContainerLogResult resultado = proveedor.obtenerLogs(solicitud);

			if (!resultado.getAdvertencias().trim().isEmpty()) {
				System.err.println("[Container/" + proveedor.getId() + "] " + resultado.getAdvertencias().trim());
			}

			Path temporal = MonitorDePID.crearArchivoTemporalTextoCLI(resultado.getContenido());
			String rutaTemporal = temporal.toFile().getCanonicalPath();

			try {
				MonitorDePID.configurarEntradasCLI(Collections.<String>emptyList(),
						Collections.singletonList(rutaTemporal));
				MonitorDePID.forzarIdeMode = true;
			} catch (RuntimeException e) {
				Files.deleteIfExists(temporal);
				throw e;
			}

			System.out.println("Logs de " + proveedor.getId() + " preparados para análisis: "
					+ resultado.getContenido().getBytes(java.nio.charset.StandardCharsets.UTF_8).length + " bytes.");
			System.out.println("Comando ejecutado: " + resultado.getComandoVisible());
			return true;

		} catch (AyudaMostradaException e) {
			return false;
		} catch (ContainerIntegrationException e) {
			System.err.println("Error de integración de contenedores: " + e.getMessage());
			return false;
		} catch (IOException e) {
			System.err.println("Error preparando los logs del contenedor: " + e.getMessage());
			return false;
		}
	}

	public static void mostrarAyuda() {
		System.out.println("Integraciones opcionales de contenedores:");
		System.out.println("  container kubernetes logs <pod|tipo/nombre> [opciones]");
		System.out.println("  container openshift logs <pod|recurso> [opciones]");
		System.out.println("  container rancher logs <pod|tipo/nombre> [opciones]");
		System.out.println("  container docker logs <contenedor> [opciones]");
		System.out.println("  container podman logs <contenedor> [opciones]");
		System.out.println("  container solaris logs <zona> --path <archivo-absoluto> [opciones]");
		System.out.println();
		System.out.println("También puede omitir 'container':");
		System.out.println("  kubernetes logs mi-pod --namespace produccion");
		System.out.println();
		System.out.println("Opciones comunes:");
		System.out.println("  --binary <ruta>           Cliente alternativo.");
		System.out.println("  --timeout-seconds <n>     Timeout, 1..3600. Predeterminado: 120.");
		System.out.println("  --max-mib <n>             Límite de captura, 1..1024 MiB. Predeterminado: 64.");
		System.out.println("  --tail <n|all>            Últimas líneas.");
		System.out.println("  --since <duración>        Logs posteriores al intervalo/fecha aceptado por el cliente.");
		System.out.println("  --until <duración>        Docker y Podman solamente.");
		System.out.println("  --timestamps              Solicita marcas de tiempo.");
		System.out.println();
		System.out.println("Kubernetes, OpenShift y Rancher:");
		System.out.println("  -n, --namespace <nombre>");
		System.out.println("  -c, --container <nombre>");
		System.out.println("  --context <nombre>");
		System.out.println("  --kubeconfig <ruta>");
		System.out.println("  --previous");
		System.out.println("  --prefix");
		System.out.println("  --all-containers");
		System.out.println();
		System.out.println("Docker:");
		System.out.println("  --details");
		System.out.println();
		System.out.println("Solaris Zones:");
		System.out.println("  --path <ruta-absoluta-dentro-de-la-zona>");
		System.out.println();
		System.out.println("No se admite --follow porque CrashDetector necesita una captura finita.");
		System.out.println("Los clientes se buscan en --binary, propiedades/variables, deps/bin y PATH.");
	}

	private static Indices resolverIndices(String[] args) throws ContainerIntegrationException {
		if (args == null || args.length == 0) {
			throw new ContainerIntegrationException("Faltan argumentos de contenedor.");
		}

		String primero = normalizar(args[0]);

		if ("container".equals(primero) || "containers".equals(primero) || "contenedor".equals(primero)
				|| "contenedores".equals(primero)) {
			if (args.length == 1 || esAyuda(args[1])) {
				return Indices.ayuda();
			}
			if (args.length == 2) {
				throw new ContainerIntegrationException("Use: container <plataforma> logs <objetivo> [opciones].");
			}
			if (esAyuda(args[2])) {
				return Indices.ayuda();
			}
			return new Indices(normalizar(args[1]), normalizar(args[2]), 3, false);
		}

		if (esPlataforma(primero)) {
			if (args.length == 1 || esAyuda(args[1])) {
				return Indices.ayuda();
			}
			if (args.length < 2) {
				throw new ContainerIntegrationException("Use: <plataforma> logs <objetivo> [opciones].");
			}
			return new Indices(primero, normalizar(args[1]), 2, false);
		}

		throw new ContainerIntegrationException("Comando de contenedor desconocido.");
	}

	private static ContainerLogRequest parsearSolicitud(String[] args, int inicio)
			throws ContainerIntegrationException {
		ContainerLogRequest.Builder builder = ContainerLogRequest.builder();
		String objetivo = null;
		boolean finOpciones = false;

		for (int i = inicio; i < args.length; i++) {
			String argumento = args[i];

			if (argumento == null) {
				throw new ContainerIntegrationException("Se recibió un argumento null.");
			}

			if (!finOpciones && "--".equals(argumento)) {
				finOpciones = true;
				continue;
			}

			if (!finOpciones && ("--follow".equals(argumento) || "-f".equals(argumento))) {
				throw new ContainerIntegrationException(
						"--follow no es compatible con el análisis finito de CrashDetector.");
			}

			if (!finOpciones && argumento.startsWith("-")) {
				Opcion opcion = Opcion.de(argumento);
				String nombre = opcion.nombre;
				String valor = opcion.valor;

				if ("--help".equals(nombre) || "-h".equals(nombre)) {
					mostrarAyuda();
					throw new AyudaMostradaException();
				} else if ("-n".equals(nombre) || "--namespace".equals(nombre)) {
					valor = valorObligatorio(args, ++i, valor, nombre);
					if (opcion.valor != null) {
						i--;
					}
					builder.namespace(validarTexto(valor, nombre));
				} else if ("-c".equals(nombre) || "--container".equals(nombre)) {
					valor = valorObligatorio(args, ++i, valor, nombre);
					if (opcion.valor != null) {
						i--;
					}
					builder.contenedor(validarTexto(valor, nombre));
				} else if ("--context".equals(nombre)) {
					valor = valorObligatorio(args, ++i, valor, nombre);
					if (opcion.valor != null) {
						i--;
					}
					builder.contexto(validarTexto(valor, nombre));
				} else if ("--kubeconfig".equals(nombre)) {
					valor = valorObligatorio(args, ++i, valor, nombre);
					if (opcion.valor != null) {
						i--;
					}
					builder.kubeconfig(validarTexto(valor, nombre));
				} else if ("--tail".equals(nombre)) {
					valor = valorObligatorio(args, ++i, valor, nombre);
					if (opcion.valor != null) {
						i--;
					}
					builder.tail(validarTail(valor));
				} else if ("--since".equals(nombre)) {
					valor = valorObligatorio(args, ++i, valor, nombre);
					if (opcion.valor != null) {
						i--;
					}
					builder.since(validarTexto(valor, nombre));
				} else if ("--until".equals(nombre)) {
					valor = valorObligatorio(args, ++i, valor, nombre);
					if (opcion.valor != null) {
						i--;
					}
					builder.until(validarTexto(valor, nombre));
				} else if ("--binary".equals(nombre)) {
					valor = valorObligatorio(args, ++i, valor, nombre);
					if (opcion.valor != null) {
						i--;
					}
					builder.binario(validarTexto(valor, nombre));
				} else if ("--path".equals(nombre) || "--log-path".equals(nombre)) {
					valor = valorObligatorio(args, ++i, valor, nombre);
					if (opcion.valor != null) {
						i--;
					}
					builder.rutaLogSolaris(validarTexto(valor, nombre));
				} else if ("--timeout-seconds".equals(nombre)) {
					valor = valorObligatorio(args, ++i, valor, nombre);
					if (opcion.valor != null) {
						i--;
					}
					builder.timeoutSegundos(parseLongRango(valor, nombre, 1L, 3600L));
				} else if ("--max-bytes".equals(nombre)) {
					valor = valorObligatorio(args, ++i, valor, nombre);
					if (opcion.valor != null) {
						i--;
					}
					builder.maxBytes(parseLongRango(valor, nombre, 1024L, 1024L * 1024L * 1024L));
				} else if ("--max-mib".equals(nombre)) {
					valor = valorObligatorio(args, ++i, valor, nombre);
					if (opcion.valor != null) {
						i--;
					}
					long mib = parseLongRango(valor, nombre, 1L, 1024L);
					builder.maxBytes(mib * 1024L * 1024L);
				} else if ("--previous".equals(nombre) || "-p".equals(nombre)) {
					asegurarSinValor(opcion, nombre);
					builder.previous(true);
				} else if ("--timestamps".equals(nombre)) {
					asegurarSinValor(opcion, nombre);
					builder.timestamps(true);
				} else if ("--prefix".equals(nombre)) {
					asegurarSinValor(opcion, nombre);
					builder.prefix(true);
				} else if ("--all-containers".equals(nombre)) {
					asegurarSinValor(opcion, nombre);
					builder.todosLosContenedores(true);
				} else if ("--details".equals(nombre)) {
					asegurarSinValor(opcion, nombre);
					builder.details(true);
				} else {
					throw new ContainerIntegrationException("Opción desconocida: " + argumento);
				}

				continue;
			}

			if (objetivo != null) {
				throw new ContainerIntegrationException(
						"Solo se admite un objetivo por ejecución. Argumento adicional: " + argumento);
			}
			objetivo = validarTexto(argumento, "objetivo");
		}

		if (objetivo == null) {
			throw new ContainerIntegrationException("Falta el pod, recurso, contenedor o zona.");
		}

		return builder.objetivo(objetivo).build();
	}

	private static String valorObligatorio(String[] args, int indicePropuesto, String valorEnIgual, String opcion)
			throws ContainerIntegrationException {
		if (valorEnIgual != null) {
			return valorEnIgual;
		}
		if (indicePropuesto >= args.length) {
			throw new ContainerIntegrationException("Falta el valor de " + opcion + ".");
		}
		return args[indicePropuesto];
	}

	private static void asegurarSinValor(Opcion opcion, String nombre) throws ContainerIntegrationException {
		if (opcion.valor != null) {
			throw new ContainerIntegrationException(nombre + " no acepta un valor.");
		}
	}

	private static String validarTexto(String valor, String nombre) throws ContainerIntegrationException {
		if (valor == null || valor.trim().isEmpty()) {
			throw new ContainerIntegrationException("El valor de " + nombre + " está vacío.");
		}
		if (valor.indexOf('\0') >= 0 || valor.indexOf('\n') >= 0 || valor.indexOf('\r') >= 0) {
			throw new ContainerIntegrationException("El valor de " + nombre + " contiene caracteres no permitidos.");
		}
		return valor;
	}

	private static String validarTail(String valor) throws ContainerIntegrationException {
		valor = validarTexto(valor, "--tail");
		if ("all".equalsIgnoreCase(valor)) {
			return "all";
		}
		try {
			long numero = Long.parseLong(valor);
			if (numero < 0L) {
				throw new NumberFormatException();
			}
			return Long.toString(numero);
		} catch (NumberFormatException e) {
			throw new ContainerIntegrationException("--tail debe ser un entero no negativo o 'all'.");
		}
	}

	private static long parseLongRango(String valor, String opcion, long minimo, long maximo)
			throws ContainerIntegrationException {
		try {
			long numero = Long.parseLong(valor);
			if (numero < minimo || numero > maximo) {
				throw new NumberFormatException();
			}
			return numero;
		} catch (NumberFormatException e) {
			throw new ContainerIntegrationException(opcion + " debe estar entre " + minimo + " y " + maximo + ".");
		}
	}

	private static ContainerLogProvider proveedor(String plataforma) throws ContainerIntegrationException {
		if ("kubernetes".equals(plataforma) || "k8s".equals(plataforma) || "kubectl".equals(plataforma)) {
			return new KubernetesContainerLogs();
		}
		if ("openshift".equals(plataforma) || "ocp".equals(plataforma) || "oc".equals(plataforma)) {
			return new OpenShiftContainerLogs();
		}
		if ("rancher".equals(plataforma)) {
			return new RancherContainerLogs();
		}
		if ("docker".equals(plataforma)) {
			return new DockerContainerLogs();
		}
		if ("podman".equals(plataforma)) {
			return new PodmanContainerLogs();
		}
		if ("solaris".equals(plataforma) || "solaris-zone".equals(plataforma) || "solaris-zones".equals(plataforma)
				|| "zone".equals(plataforma) || "zones".equals(plataforma)) {
			return new SolarisZoneContainerLogs();
		}

		throw new ContainerIntegrationException("Plataforma no compatible: " + plataforma);
	}

	private static boolean esPlataforma(String valor) {
		try {
			proveedor(valor);
			return true;
		} catch (ContainerIntegrationException e) {
			return false;
		}
	}

	private static boolean esAccionLogs(String accion) {
		return "logs".equals(accion) || "log".equals(accion) || "analizar-logs".equals(accion)
				|| "analyze-logs".equals(accion) || "analyse-logs".equals(accion);
	}

	private static boolean esAyuda(String valor) {
		String normalizado = normalizar(valor);
		return "--help".equals(normalizado) || "-h".equals(normalizado) || "help".equals(normalizado);
	}

	private static String normalizar(String valor) {
		return valor == null ? "" : valor.toLowerCase(Locale.ROOT);
	}

	private static final class AyudaMostradaException extends ContainerIntegrationException {
		private static final long serialVersionUID = 1L;

		private AyudaMostradaException() {
			super("Ayuda mostrada.");
		}
	}

	private static final class Indices {
		private final String plataforma;
		private final String accion;
		private final int primerArgumento;
		private final boolean soloAyuda;

		private Indices(String plataforma, String accion, int primerArgumento, boolean soloAyuda) {
			this.plataforma = plataforma;
			this.accion = accion;
			this.primerArgumento = primerArgumento;
			this.soloAyuda = soloAyuda;
		}

		private static Indices ayuda() {
			return new Indices("", "", 0, true);
		}
	}

	private static final class Opcion {
		private final String nombre;
		private final String valor;

		private Opcion(String nombre, String valor) {
			this.nombre = nombre;
			this.valor = valor;
		}

		private static Opcion de(String argumento) {
			int igual = argumento.indexOf('=');
			if (igual > 0 && argumento.startsWith("--")) {
				return new Opcion(argumento.substring(0, igual), argumento.substring(igual + 1));
			}
			return new Opcion(argumento, null);
		}
	}
}

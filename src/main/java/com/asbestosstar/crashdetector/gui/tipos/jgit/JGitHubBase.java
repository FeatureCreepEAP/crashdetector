package com.asbestosstar.crashdetector.gui.tipos.jgit;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JFrame;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.deps.DescargadorDependenciasMaven;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * Base abstracta para el panel principal de JGit.
 *
 * La base contiene la lógica general. Las implementaciones contienen
 * apariencia.
 *
 * Esta clase ya no descarga JARs manualmente desde Maven Central. Ahora usa
 * DescargadorDependenciasMaven, que resuelve dependencias transitivas y usa
 * todos los repositorios configurados.
 */
public abstract class JGitHubBase extends JFrame implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	public static final Map<String, Supplier<JGitHubBase>> GUIS = new HashMap<String, Supplier<JGitHubBase>>();

	protected File carpetaActual() {
		return new File(System.getProperty("user.dir")).getAbsoluteFile();
	}

	protected boolean jgitDisponible() {
		return BuscarParaJGit.estaJGitBasicoEnClasspath();
	}

	protected boolean todosLosArtefactosEnClasspath() {
		return BuscarParaJGit.estanTodosLosArtefactosEnClasspath();
	}

	protected List<DependenciaJGit> dependenciasFaltantes() {
		return BuscarParaJGit.dependenciasFaltantesEnClasspath();
	}

	protected boolean repoExiste() {
		return JGitReflexivo.repositorioExiste(carpetaActual());
	}

	protected boolean crearRepo() {
		return JGitReflexivo.inicializarRepositorio(carpetaActual());
	}

	protected boolean establecerRemote(String url) {
		return JGitReflexivo.establecerRemote(carpetaActual(), url);
	}

	protected String obtenerRemote() {
		return JGitReflexivo.obtenerRemote(carpetaActual());
	}

	protected void abrirGuiSwing() {
		JGitReflexivo.abrirGuiSwing(carpetaActual());
	}

	protected boolean hacerCommitManual() {
		return JGitReflexivo.commitAutomatico(carpetaActual(), "Commit manual de CrashDetector");
	}

	protected boolean hacerPushManual() {
		return JGitReflexivo.push(carpetaActual());
	}

	protected boolean abrirCarpetaDependencias() {
		try {
			File carpeta = BuscarParaJGit.CARPETA_JGIT;

			if (!carpeta.exists()) {
				carpeta.mkdirs();
			}

			Desktop.getDesktop().open(carpeta);
			return true;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return false;
		}
	}

	/**
	 * Descarga una dependencia raiz usando el descargador Maven general.
	 *
	 * Importante: este metodo descarga tambien dependencias transitivas. El archivo
	 * devuelto es la carpeta de instalacion, no necesariamente un JAR individual.
	 */
	protected ResultadoDescargaJGit descargarDependencia(DependenciaJGit dependencia) {
		if (dependencia == null) {
			return new ResultadoDescargaJGit(false, null, "Dependencia nula.");
		}

		if (!dependencia.coordenadaValida()) {
			return new ResultadoDescargaJGit(false, null, "Coordenada Maven invalida: " + dependencia.nombreVisible());
		}

		try {
			if (!BuscarParaJGit.CARPETA_JGIT.exists()) {
				BuscarParaJGit.CARPETA_JGIT.mkdirs();
			}

			DescargadorDependenciasMaven.ResultadoDescarga r = DescargadorDependenciasMaven
					.descargarDependencia(dependencia.groupId, dependencia.artifactId, dependencia.version);

			return new ResultadoDescargaJGit(r.exito, r.carpeta, r.mensaje);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return new ResultadoDescargaJGit(false, null, t.getMessage());
		}
	}

	/**
	 * Descarga todas las dependencias raiz faltantes en la carpeta de instalacion.
	 *
	 * Como el descargador nuevo resuelve transitivas, esta funcion manda solo las
	 * dependencias raiz que BuscarParaJGit considere necesarias.
	 */
	protected int descargarTodasLasDependenciasFaltantes() {
		List<DependenciaJGit> faltantes = dependenciasFaltantesEnCarpetaInstalacion();

		if (faltantes == null || faltantes.isEmpty()) {
			return 0;
		}

		List<DescargadorDependenciasMaven.CoordenadaMaven> coordenadas = new ArrayList<DescargadorDependenciasMaven.CoordenadaMaven>();

		for (DependenciaJGit dep : faltantes) {
			if (dep == null || !dep.coordenadaValida()) {
				continue;
			}

			coordenadas.add(new DescargadorDependenciasMaven.CoordenadaMaven(dep.groupId, dep.artifactId, dep.version));
		}

		if (coordenadas.isEmpty()) {
			return 0;
		}

		DescargadorDependenciasMaven.ResultadoDescarga r = DescargadorDependenciasMaven
				.descargarDependencias(coordenadas);

		if (!r.exito) {
			CrashDetectorLogger.log("Error descargando dependencias JGit: " + r.mensaje);
		}

		/*
		 * Contamos cuantas de las dependencias raiz que faltaban ahora aparecen en la
		 * carpeta. Las transitivas no se cuentan aqui porque la GUI habla de las
		 * dependencias principales faltantes.
		 */
		return contarDependenciasInstaladas(faltantes);
	}

	private int contarDependenciasInstaladas(List<DependenciaJGit> deps) {
		if (deps == null || deps.isEmpty()) {
			return 0;
		}

		int total = 0;
		List<File> jars = BuscarParaJGit.encontrarJarsInstalados();

		for (DependenciaJGit dep : deps) {
			if (dep == null) {
				continue;
			}

			for (File jar : jars) {
				if (jar == null || !jar.isFile()) {
					continue;
				}

				if (jar.length() <= 0L) {
					continue;
				}

				if (dep.coincideConNombreJar(jar.getName())) {
					total++;
					break;
				}
			}
		}

		return total;
	}

	protected abstract void actualizarEstadoBotones();

	protected List<DependenciaJGit> dependenciasFaltantesEnCarpetaInstalacion() {
		return BuscarParaJGit.dependenciasFaltantesEnCarpetaInstalacion();
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.JGIT_HUB;
	}
}
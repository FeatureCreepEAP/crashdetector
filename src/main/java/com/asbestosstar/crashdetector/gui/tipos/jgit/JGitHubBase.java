package com.asbestosstar.crashdetector.gui.tipos.jgit;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JFrame;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * Base abstracta para el panel principal de JGit.
 *
 * La base contiene la lógica general. Las implementaciones contienen
 * apariencia.
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

	protected ResultadoDescargaJGit descargarDependencia(DependenciaJGit dependencia) {
		if (dependencia == null) {
			return new ResultadoDescargaJGit(false, null, "Dependencia nula.");
		}

		try {
			if (!BuscarParaJGit.CARPETA_JGIT.exists()) {
				BuscarParaJGit.CARPETA_JGIT.mkdirs();
			}

			File destino = new File(BuscarParaJGit.CARPETA_JGIT, dependencia.nombreJar());

			if (destino.exists() && destino.isFile() && destino.length() > 0L) {
				return new ResultadoDescargaJGit(true, destino, "Ya existe: " + destino.getAbsolutePath());
			}

			URL url = new URL(dependencia.urlMavenCentral());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(15000);
			con.setReadTimeout(30000);
			con.setRequestProperty("User-Agent", "CrashDetector-JGit-Installer");

			int codigo = con.getResponseCode();

			if (codigo < 200 || codigo >= 300) {
				return new ResultadoDescargaJGit(false, destino,
						"HTTP " + codigo + " descargando " + dependencia.urlMavenCentral());
			}

			InputStream in = con.getInputStream();

			try {
				FileOutputStream out = new FileOutputStream(destino);

				try {
					byte[] buffer = new byte[8192];
					int leido;

					while ((leido = in.read(buffer)) >= 0) {
						out.write(buffer, 0, leido);
					}
				} finally {
					out.close();
				}
			} finally {
				in.close();
			}

			if (!destino.exists() || destino.length() <= 0L) {
				return new ResultadoDescargaJGit(false, destino, "El archivo descargado quedó vacío.");
			}

			return new ResultadoDescargaJGit(true, destino, "Descargado: " + destino.getAbsolutePath());

		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return new ResultadoDescargaJGit(false, null, t.getMessage());
		}
	}

	protected int descargarTodasLasDependenciasFaltantes() {
		int descargadas = 0;

		for (DependenciaJGit dep : dependenciasFaltantes()) {
			ResultadoDescargaJGit r = descargarDependencia(dep);

			if (r.exito) {
				descargadas++;
			}
		}

		return descargadas;
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
package com.asbestosstar.crashdetector.gui.tipos.jgit;

import java.awt.BorderLayout;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Operaciones reales de JGit. Esta clase puede fallar al cargar si JGit no está
 * en classpath, por eso debe llamarse solamente desde JGitReflexivo.
 */
public class OperacionesJGitDirectas {

	public static boolean repositorioExiste(File carpeta) {
		try {
			File git = new File(carpeta, ".git");
			return git.exists() && git.isDirectory();
		} catch (Throwable t) {
			return false;
		}
	}

	public static boolean inicializarRepositorio(File carpeta) {
		try {
			if (repositorioExiste(carpeta)) {
				return true;
			}

			Git git = Git.init().setDirectory(carpeta).call();
			git.close();
			return true;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return false;
		}
	}

	public static boolean commitAutomatico(File carpeta, String mensaje) {
		try {
			if (!repositorioExiste(carpeta)) {
				return false;
			}

			Git git = Git.open(carpeta);

			try {
				Status status = git.status().call();

				if (status.isClean()) {
					CrashDetectorLogger.log("JGit: no hay cambios para commit.");
					return false;
				}

				// Agregar archivos nuevos/modificados.
				git.add().addFilepattern(".").call();

				// Agregar eliminaciones.
				git.add().addFilepattern(".").setUpdate(true).call();

				git.commit().setMessage(mensaje).setAuthor("CrashDetector", "crashdetector@local").call();

				CrashDetectorLogger.log("JGit: commit automático creado.");
				return true;
			} finally {
				git.close();
			}
		} catch (Throwable t) {
			CrashDetectorLogger.log("JGit: error en commit automático: " + t.getMessage());
			CrashDetectorLogger.logException(t);
			return false;
		}
	}

	public static boolean tieneRemote(File carpeta) {
		String remote = obtenerRemote(carpeta);
		return remote != null && !remote.trim().isEmpty();
	}

	public static String obtenerRemote(File carpeta) {
		try {
			Repository repo = abrirRepo(carpeta);

			try {
				StoredConfig config = repo.getConfig();
				String url = config.getString("remote", "origin", "url");
				return url == null ? "" : url;
			} finally {
				repo.close();
			}
		} catch (Throwable t) {
			return "";
		}
	}

	public static boolean establecerRemote(File carpeta, String url) {
		try {
			if (url == null || url.trim().isEmpty()) {
				return false;
			}

			Repository repo = abrirRepo(carpeta);

			try {
				StoredConfig config = repo.getConfig();
				config.setString("remote", "origin", "url", url.trim());
				config.setString("remote", "origin", "fetch", "+refs/heads/*:refs/remotes/origin/*");
				config.save();
				return true;
			} finally {
				repo.close();
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return false;
		}
	}

	public static boolean push(File carpeta) {
		try {
			if (!repositorioExiste(carpeta) || !tieneRemote(carpeta)) {
				return false;
			}

			Git git = Git.open(carpeta);

			try {
				git.push().setRemote("origin").setPushAll().call();

				CrashDetectorLogger.log("JGit: push completado.");
				return true;
			} finally {
				git.close();
			}
		} catch (Throwable t) {
			CrashDetectorLogger.log("JGit: error en push: " + t.getMessage());
			CrashDetectorLogger.logException(t);
			return false;
		}
	}

	public static void abrirGuiSwing(final File carpeta) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mostrarVentanaEstado(carpeta);
			}
		});
	}

	public static void mostrarVentanaEstado(File carpeta) {
		JFrame frame = new JFrame(MonitorDePID.idioma.jgitTituloVentanaSwing());
		frame.setSize(800, 600);
		frame.setLayout(new BorderLayout());

		JTextArea area = new JTextArea();
		area.setEditable(false);

		StringBuilder sb = new StringBuilder();

		try {
			if (!repositorioExiste(carpeta)) {
				sb.append(MonitorDePID.idioma.jgitNoHayRepositorio()).append("\n");
			} else {
				Git git = Git.open(carpeta);

				try {
					sb.append(MonitorDePID.idioma.jgitRepositorio()).append(": ").append(carpeta.getAbsolutePath())
							.append("\n\n");

					Status status = git.status().call();

					sb.append(MonitorDePID.idioma.jgitArchivosModificados()).append(":\n");
					for (String s : status.getModified()) {
						sb.append("  M ").append(s).append("\n");
					}

					sb.append("\n").append(MonitorDePID.idioma.jgitArchivosNuevos()).append(":\n");
					for (String s : status.getUntracked()) {
						sb.append("  ? ").append(s).append("\n");
					}

					sb.append("\n").append(MonitorDePID.idioma.jgitUltimosCommits()).append(":\n");
					Iterable<RevCommit> logs = git.log().setMaxCount(20).call();

					for (RevCommit c : logs) {
						sb.append("  ").append(c.getName().substring(0, 8)).append("  ")
								.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.format(new Date(((long) c.getCommitTime()) * 1000L)))
								.append("  ").append(c.getShortMessage()).append("\n");
					}
				} finally {
					git.close();
				}
			}
		} catch (Throwable t) {
			sb.append(MonitorDePID.idioma.jgitError()).append(": ").append(t.getMessage());
			CrashDetectorLogger.logException(t);
		}

		area.setText(sb.toString());
		area.setCaretPosition(0);

		frame.add(new JScrollPane(area), BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static Repository abrirRepo(File carpeta) throws Exception {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		return builder.setGitDir(new File(carpeta, ".git")).readEnvironment().findGitDir().build();
	}
}
package com.asbestosstar.crashdetector.gui.tipos.jgit.avanzado;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.gui.tipos.jgit.avanzado.CambioGitAvanzado.Estado;

/**
 * Operaciones JGit reales del cliente avanzado.
 *
 * Esta clase importa JGit directamente y solo debe cargarse mediante
 * JGitAvanzadoReflexivo después de comprobar las dependencias.
 */
public final class OperacionesJGitAvanzadasDirectas {

	private OperacionesJGitAvanzadasDirectas() {
	}

	public static EstadoGitAvanzado obtenerEstado(File carpeta, int maximoHistorial) {
		try (Git git = Git.open(carpeta)) {
			Repository repo = git.getRepository();
			Status status = git.status().call();

			Map<String, CambioGitAvanzado> preparados = new LinkedHashMap<String, CambioGitAvanzado>();
			Map<String, CambioGitAvanzado> noPreparados = new LinkedHashMap<String, CambioGitAvanzado>();

			agregar(preparados, status.getAdded(), Estado.AGREGADO, true);
			agregar(preparados, status.getChanged(), Estado.MODIFICADO, true);
			agregar(preparados, status.getRemoved(), Estado.ELIMINADO, true);

			agregar(noPreparados, status.getModified(), Estado.MODIFICADO, false);
			agregar(noPreparados, status.getMissing(), Estado.ELIMINADO, false);
			agregar(noPreparados, status.getUntracked(), Estado.NO_RASTREADO, false);
			agregar(noPreparados, status.getConflicting(), Estado.CONFLICTO, false);

			List<String> ramas = new ArrayList<String>();
			for (Ref ref : git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call()) {
				String nombre = simplificarRama(ref.getName());
				if (!nombre.isEmpty() && !ramas.contains(nombre)) {
					ramas.add(nombre);
				}
			}
			Collections.sort(ramas, String.CASE_INSENSITIVE_ORDER);

			List<CommitGitAvanzado> historial = new ArrayList<CommitGitAvanzado>();
			try {
				Iterable<RevCommit> commits = git.log().setMaxCount(Math.max(1, maximoHistorial)).call();
				for (RevCommit commit : commits) {
					PersonIdent autor = commit.getAuthorIdent();
					historial.add(new CommitGitAvanzado(commit.getName(), autor == null ? "" : autor.getName(),
							autor == null ? "" : autor.getEmailAddress(), commit.getShortMessage(),
							commit.getCommitTime()));
				}
			} catch (Throwable sinHead) {
				// Un repositorio recién creado todavía no tiene HEAD ni historial.
			}

			return new EstadoGitAvanzado(repo.getBranch(), obtenerRemote(repo), ramas, ordenar(preparados),
					ordenar(noPreparados), historial);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return null;
		}
	}

	public static boolean preparar(File carpeta, List<String> rutas) {
		if (rutas == null || rutas.isEmpty()) {
			return false;
		}
		try (Git git = Git.open(carpeta)) {
			for (String ruta : rutas) {
				if (ruta == null || ruta.trim().isEmpty()) {
					continue;
				}
				git.add().addFilepattern(ruta).call();
				git.add().addFilepattern(ruta).setUpdate(true).call();
			}
			return true;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return false;
		}
	}

	public static boolean prepararTodo(File carpeta) {
		try (Git git = Git.open(carpeta)) {
			git.add().addFilepattern(".").call();
			git.add().addFilepattern(".").setUpdate(true).call();
			return true;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return false;
		}
	}

	public static boolean quitarPreparacion(File carpeta, List<String> rutas) {
		if (rutas == null || rutas.isEmpty()) {
			return false;
		}
		try (Git git = Git.open(carpeta)) {
			for (String ruta : rutas) {
				if (ruta != null && !ruta.trim().isEmpty()) {
					git.reset().addPath(ruta).call();
				}
			}
			return true;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return false;
		}
	}

	public static boolean quitarTodaPreparacion(File carpeta) {
		try (Git git = Git.open(carpeta)) {
			git.reset().setMode(ResetType.MIXED).call();
			return true;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return false;
		}
	}

	public static boolean commit(File carpeta, String mensaje) {
		if (mensaje == null || mensaje.trim().isEmpty()) {
			return false;
		}
		try (Git git = Git.open(carpeta)) {
			Status status = git.status().call();
			if (status.getAdded().isEmpty() && status.getChanged().isEmpty() && status.getRemoved().isEmpty()) {
				return false;
			}
			git.commit().setMessage(mensaje.trim()).setAuthor(Statics.nombre_cd.obtener(), "crashdetector@local")
					.call();
			return true;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return false;
		}
	}

	public static String diffArchivo(File carpeta, String ruta, boolean preparado) {
		if (ruta == null || ruta.trim().isEmpty()) {
			return "";
		}
		try (Git git = Git.open(carpeta);
				ByteArrayOutputStream salida = new ByteArrayOutputStream();
				DiffFormatter formato = new DiffFormatter(salida)) {
			formato.setRepository(git.getRepository());
			formato.setDiffComparator(RawTextComparator.DEFAULT);
			formato.setDetectRenames(true);
			List<DiffEntry> entradas = git.diff().setCached(preparado).setPathFilter(PathFilter.create(ruta)).call();
			formato.format(entradas);
			formato.flush();
			return new String(salida.toByteArray(), StandardCharsets.UTF_8);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return "";
		}
	}

	public static String diffCommit(File carpeta, String hash) {
		if (hash == null || hash.trim().isEmpty()) {
			return "";
		}
		try (Repository repo = abrirRepo(carpeta);
				RevWalk walk = new RevWalk(repo);
				ByteArrayOutputStream salida = new ByteArrayOutputStream();
				DiffFormatter formato = new DiffFormatter(salida)) {
			ObjectId id = repo.resolve(hash.trim());
			if (id == null) {
				return "";
			}
			RevCommit commit = walk.parseCommit(id);
			AbstractTreeIterator anterior = commit.getParentCount() == 0 ? new EmptyTreeIterator()
					: prepararArbol(repo, walk.parseCommit(commit.getParent(0).getId()).getTree());
			AbstractTreeIterator nuevo = prepararArbol(repo, commit.getTree());

			formato.setRepository(repo);
			formato.setDiffComparator(RawTextComparator.DEFAULT);
			formato.setDetectRenames(true);
			formato.format(formato.scan(anterior, nuevo));
			formato.flush();
			return new String(salida.toByteArray(), StandardCharsets.UTF_8);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return "";
		}
	}

	public static String blame(File carpeta, String ruta) {
		if (ruta == null || ruta.trim().isEmpty()) {
			return "";
		}
		try (Git git = Git.open(carpeta)) {
			BlameResult resultado = git.blame().setFilePath(ruta).call();
			if (resultado == null || resultado.getResultContents() == null) {
				return "";
			}
			StringBuilder texto = new StringBuilder();
			for (int linea = 0; linea < resultado.getResultContents().size(); linea++) {
				RevCommit commit = resultado.getSourceCommit(linea);
				PersonIdent autor = resultado.getSourceAuthor(linea);
				String hash = commit == null ? "--------" : commit.abbreviate(8).name();
				String nombre = autor == null ? "" : autor.getName();
				texto.append(hash).append("  ").append(nombre).append("  ").append(linea + 1).append(" | ")
						.append(resultado.getResultContents().getString(linea)).append('\n');
			}
			return texto.toString();
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return "";
		}
	}

	public static boolean crearRama(File carpeta, String nombre) {
		if (!nombreRamaValido(nombre)) {
			return false;
		}
		try (Git git = Git.open(carpeta)) {
			git.branchCreate().setName(nombre.trim()).call();
			git.checkout().setName(nombre.trim()).call();
			return true;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return false;
		}
	}

	public static boolean cambiarRama(File carpeta, String nombre) {
		if (!nombreRamaValido(nombre)) {
			return false;
		}
		try (Git git = Git.open(carpeta)) {
			String limpia = nombre.trim();
			if (limpia.startsWith("origin/")) {
				String local = limpia.substring("origin/".length());
				git.checkout().setCreateBranch(true).setName(local).setStartPoint(limpia).call();
			} else {
				git.checkout().setName(limpia).call();
			}
			return true;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return false;
		}
	}

	public static boolean fetch(File carpeta) {
		try (Git git = Git.open(carpeta)) {
			git.fetch().setRemote("origin").call();
			return true;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return false;
		}
	}

	public static boolean pull(File carpeta) {
		try (Git git = Git.open(carpeta)) {
			return git.pull().call().isSuccessful();
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return false;
		}
	}

	public static boolean push(File carpeta) {
		try (Git git = Git.open(carpeta)) {
			git.push().setRemote("origin").setPushAll().call();
			return true;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return false;
		}
	}

	private static Repository abrirRepo(File carpeta) throws Exception {
		return new FileRepositoryBuilder().setGitDir(new File(carpeta, ".git")).setWorkTree(carpeta).readEnvironment()
				.findGitDir().build();
	}

	private static AbstractTreeIterator prepararArbol(Repository repo, RevTree arbol) throws Exception {
		CanonicalTreeParser parser = new CanonicalTreeParser();
		parser.reset(repo.newObjectReader(), arbol.getId());
		return parser;
	}

	private static String obtenerRemote(Repository repo) {
		String valor = repo.getConfig().getString("remote", "origin", "url");
		return valor == null ? "" : valor;
	}

	private static void agregar(Map<String, CambioGitAvanzado> destino, Iterable<String> rutas, Estado estado,
			boolean preparado) {
		for (String ruta : rutas) {
			destino.put(ruta, new CambioGitAvanzado(ruta, estado, preparado));
		}
	}

	private static List<CambioGitAvanzado> ordenar(Map<String, CambioGitAvanzado> mapa) {
		List<CambioGitAvanzado> lista = new ArrayList<CambioGitAvanzado>(mapa.values());
		Collections.sort(lista, new Comparator<CambioGitAvanzado>() {
			@Override
			public int compare(CambioGitAvanzado a, CambioGitAvanzado b) {
				return a.ruta().compareToIgnoreCase(b.ruta());
			}
		});
		return lista;
	}

	private static String simplificarRama(String nombre) {
		if (nombre == null) {
			return "";
		}
		if (nombre.startsWith("refs/heads/")) {
			return nombre.substring("refs/heads/".length());
		}
		if (nombre.startsWith("refs/remotes/")) {
			return nombre.substring("refs/remotes/".length());
		}
		return nombre;
	}

	private static boolean nombreRamaValido(String nombre) {
		return nombre != null && !nombre.trim().isEmpty() && !nombre.contains(" ") && !nombre.contains("..")
				&& !nombre.endsWith("/") && !nombre.startsWith("/");
	}
}

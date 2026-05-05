package com.asbestosstar.crashdetector.gui.tipos.jgit;

/**
 * Representa una dependencia JAR que se puede comprobar y descargar desde Maven
 * Central.
 */
public class DependenciaJGit {

	public final String groupId;
	public final String artifactId;
	public final String version;

	public DependenciaJGit(String groupId, String artifactId, String version) {
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
	}

	public String nombreJar() {
		return artifactId + "-" + version + ".jar";
	}

	public String nombreVisible() {
		return groupId + ":" + artifactId + ":" + version;
	}

	public String urlMavenCentral() {
		return "https://repo1.maven.org/maven2/" + groupId.replace('.', '/') + "/" + artifactId + "/" + version + "/"
				+ nombreJar();
	}

	public boolean coincideConNombreJar(String nombreArchivo) {
		if (nombreArchivo == null) {
			return false;
		}

		String n = nombreArchivo.trim().toLowerCase();

		return n.equals(nombreJar().toLowerCase()) || n.startsWith((artifactId + "-").toLowerCase());
	}
}
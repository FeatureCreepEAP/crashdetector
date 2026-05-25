package com.asbestosstar.crashdetector.gui.tipos.jgit;

/**
 * Representa una dependencia Maven que se puede comprobar y descargar.
 *
 * Ya no conoce Maven Central directamente. La descarga real la hace
 * DescargadorDependenciasMaven, que puede usar varios repositorios y resolver
 * dependencias transitivas.
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

	public String nombrePom() {
		return artifactId + "-" + version + ".pom";
	}

	public String nombreVisible() {
		return groupId + ":" + artifactId + ":" + version;
	}

	public boolean coincideConNombreJar(String nombreArchivo) {
		if (nombreArchivo == null) {
			return false;
		}

		String n = nombreArchivo.trim().toLowerCase();

		return n.equals(nombreJar().toLowerCase()) || n.startsWith((artifactId + "-").toLowerCase());
	}

	public boolean coordenadaValida() {
		return groupId != null && !groupId.trim().isEmpty() && artifactId != null && !artifactId.trim().isEmpty()
				&& version != null && !version.trim().isEmpty();
	}
}
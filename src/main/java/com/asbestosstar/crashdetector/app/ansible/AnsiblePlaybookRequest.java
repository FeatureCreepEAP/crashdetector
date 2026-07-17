package com.asbestosstar.crashdetector.app.ansible;

import java.io.File;

/**
 * Solicitud explícita para ejecutar un playbook proporcionado por el usuario.
 */
public final class AnsiblePlaybookRequest {

	private final AnsibleCommonOptions options;
	private final File playbook;
	private final File projectDirectory;
	private final boolean externalPlaybookAllowed;
	private final boolean check;
	private final boolean syntaxCheck;

	public AnsiblePlaybookRequest(AnsibleCommonOptions options, File playbook, File projectDirectory,
			boolean externalPlaybookAllowed, boolean check, boolean syntaxCheck) {
		this.options = options;
		this.playbook = playbook;
		this.projectDirectory = projectDirectory;
		this.externalPlaybookAllowed = externalPlaybookAllowed;
		this.check = check;
		this.syntaxCheck = syntaxCheck;
	}

	public AnsibleCommonOptions getOptions() {
		return options;
	}

	public File getPlaybook() {
		return playbook;
	}

	public File getProjectDirectory() {
		return projectDirectory;
	}

	public boolean isExternalPlaybookAllowed() {
		return externalPlaybookAllowed;
	}

	public boolean isCheck() {
		return check;
	}

	public boolean isSyntaxCheck() {
		return syntaxCheck;
	}
}

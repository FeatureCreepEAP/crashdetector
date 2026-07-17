package com.asbestosstar.crashdetector.app.ansible;

/**
 * Solicitud de recopilación de un log remoto de uno o varios hosts.
 */
public final class AnsibleLogRequest {

	private final AnsibleCommonOptions options;
	private final String remotePath;
	private final String tail;

	public AnsibleLogRequest(AnsibleCommonOptions options, String remotePath, String tail) {
		this.options = options;
		this.remotePath = remotePath;
		this.tail = tail;
	}

	public AnsibleCommonOptions getOptions() {
		return options;
	}

	public String getRemotePath() {
		return remotePath;
	}

	public String getTail() {
		return tail;
	}
}

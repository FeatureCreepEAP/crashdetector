package com.asbestosstar.crashdetector.app.ansible;

import java.io.File;

/**
 * Opciones seguras compartidas por los comandos Ansible.
 */
public final class AnsibleCommonOptions {

	private File inventory;
	private String limit = "all";
	private String remoteUser;
	private File privateKey;
	private boolean become;
	private String becomeUser;
	private int forks = 5;
	private long timeoutSeconds = 300L;
	private long maxBytes = 64L * 1024L * 1024L;
	private String runnerBinary;
	private String playbookBinary;
	private AnsibleEngine engine = AnsibleEngine.AUTO;
	private boolean keepArtifacts;
	private File extraVarsFile;
	private File vaultPasswordFile;

	public File getInventory() {
		return inventory;
	}

	public void setInventory(File inventory) {
		this.inventory = inventory;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getRemoteUser() {
		return remoteUser;
	}

	public void setRemoteUser(String remoteUser) {
		this.remoteUser = remoteUser;
	}

	public File getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(File privateKey) {
		this.privateKey = privateKey;
	}

	public boolean isBecome() {
		return become;
	}

	public void setBecome(boolean become) {
		this.become = become;
	}

	public String getBecomeUser() {
		return becomeUser;
	}

	public void setBecomeUser(String becomeUser) {
		this.becomeUser = becomeUser;
	}

	public int getForks() {
		return forks;
	}

	public void setForks(int forks) {
		this.forks = forks;
	}

	public long getTimeoutSeconds() {
		return timeoutSeconds;
	}

	public void setTimeoutSeconds(long timeoutSeconds) {
		this.timeoutSeconds = timeoutSeconds;
	}

	public long getMaxBytes() {
		return maxBytes;
	}

	public void setMaxBytes(long maxBytes) {
		this.maxBytes = maxBytes;
	}

	public String getRunnerBinary() {
		return runnerBinary;
	}

	public void setRunnerBinary(String runnerBinary) {
		this.runnerBinary = runnerBinary;
	}

	public String getPlaybookBinary() {
		return playbookBinary;
	}

	public void setPlaybookBinary(String playbookBinary) {
		this.playbookBinary = playbookBinary;
	}

	public AnsibleEngine getEngine() {
		return engine;
	}

	public void setEngine(AnsibleEngine engine) {
		this.engine = engine;
	}

	public boolean isKeepArtifacts() {
		return keepArtifacts;
	}

	public void setKeepArtifacts(boolean keepArtifacts) {
		this.keepArtifacts = keepArtifacts;
	}

	public File getExtraVarsFile() {
		return extraVarsFile;
	}

	public void setExtraVarsFile(File extraVarsFile) {
		this.extraVarsFile = extraVarsFile;
	}

	public File getVaultPasswordFile() {
		return vaultPasswordFile;
	}

	public void setVaultPasswordFile(File vaultPasswordFile) {
		this.vaultPasswordFile = vaultPasswordFile;
	}
}

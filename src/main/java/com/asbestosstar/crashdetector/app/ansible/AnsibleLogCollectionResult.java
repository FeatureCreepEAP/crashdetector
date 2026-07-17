package com.asbestosstar.crashdetector.app.ansible;

/**
 * Resumen de una recopilación remota preparada para CrashDetector.
 */
public final class AnsibleLogCollectionResult {

	private final String engine;
	private final String command;
	private final int hostCount;
	private final long bytes;

	public AnsibleLogCollectionResult(String engine, String command, int hostCount, long bytes) {
		this.engine = engine;
		this.command = command;
		this.hostCount = hostCount;
		this.bytes = bytes;
	}

	public String getEngine() {
		return engine;
	}

	public String getCommand() {
		return command;
	}

	public int getHostCount() {
		return hostCount;
	}

	public long getBytes() {
		return bytes;
	}
}

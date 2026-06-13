package com.asbestosstar.crashdetector.bajo.hw.cpu.sparc;

public class UmemInit {

	public static boolean necesitaArgEspecialUmem() {
		return false; // no JVM arg; this is environment-based
	}

	public static boolean disponible() {
		return new java.io.File("/usr/lib/libumem.so.1").isFile()
				|| new java.io.File("/usr/lib/sparcv9/libumem.so.1").isFile();
	}

	public static void aplicarA(ProcessBuilder pb) {
		if (!disponible()) {
			return;
		}

		java.util.Map<String, String> env = pb.environment();
		env.put("LD_PRELOAD", "libumem.so.1");
		env.put("UMEM_DEBUG", "default");
		env.put("UMEM_LOGGING", "transaction");
	}
}
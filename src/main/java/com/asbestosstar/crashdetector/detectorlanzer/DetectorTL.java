package com.asbestosstar.crashdetector.detectorlanzer;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

import com.asbestosstar.crashdetector.App;

public class DetectorTL implements DetectorLanzer {

	@Override
	public String id() {
		return "tlauncher";
	}

	@Override
	public boolean animado() {
		return true;
	}

	@Override
	public boolean desanimado() {
		return false;
	}

	@Override // No es perfecto, pero esta ok
	public boolean detectar(App app, String cmd) {

		if (!app.equals(App.MINECRAFT))
			return false;

		// =========================
		// 1. Detectar en comando
		// =========================

		if (cmd != null) {

			String n = cmd.toLowerCase().replace('\\', '/');

			if (n.contains("/org/tlauncher/patchy/") || n.contains("/org/tlauncher/authlib/")) {

				return true;
			}
		}

		// =========================
		// 2. Detectar en classpath JVM actual
		// =========================

		String classpath = System.getProperty("java.class.path");

		if (classpath != null) {

			String cp = classpath.toLowerCase().replace('\\', '/');

			if (cp.contains("/org/tlauncher/patchy/") || cp.contains("/org/tlauncher/authlib/")) {

				return true;
			}
		}

		// =========================
		// 3. Detectar en argumentos JVM (incluye module-path)
		// =========================

		try {

			RuntimeMXBean mx = ManagementFactory.getRuntimeMXBean();

			List<String> args = mx.getInputArguments();

			for (String arg : args) {

				String a = arg.toLowerCase().replace('\\', '/');

				if (a.contains("/org/tlauncher/patchy/") || a.contains("/org/tlauncher/authlib/")) {

					return true;
				}
			}

		} catch (Throwable ignored) {
		}

		return false;
	}
}

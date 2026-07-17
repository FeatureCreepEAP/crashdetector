package com.asbestosstar.crashdetector.app.ansible;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Construye únicamente argumentos Ansible tipados y validados previamente.
 */
public final class AnsibleArguments {

	private AnsibleArguments() {
	}

	public static void agregarComunesPlaybook(List<String> comando, AnsibleCommonOptions options,
			boolean incluirInventory) {
		if (incluirInventory) {
			comando.add("-i");
			comando.add(options.getInventory().getAbsolutePath());
		}
		if (options.getLimit() != null && !options.getLimit().trim().isEmpty()) {
			comando.add("--limit");
			comando.add(options.getLimit());
		}
		comando.add("--forks");
		comando.add(Integer.toString(options.getForks()));
		if (options.getRemoteUser() != null) {
			comando.add("--user");
			comando.add(options.getRemoteUser());
		}
		if (options.getPrivateKey() != null) {
			comando.add("--private-key");
			comando.add(options.getPrivateKey().getAbsolutePath());
		}
		if (options.isBecome()) {
			comando.add("--become");
		}
		if (options.getBecomeUser() != null) {
			comando.add("--become-user");
			comando.add(options.getBecomeUser());
		}
		if (options.getExtraVarsFile() != null) {
			comando.add("--extra-vars");
			comando.add("@" + options.getExtraVarsFile().getAbsolutePath());
		}
		if (options.getVaultPasswordFile() != null) {
			comando.add("--vault-password-file");
			comando.add(options.getVaultPasswordFile().getAbsolutePath());
		}
	}

	public static void escribirCmdlineRunner(Path destino, AnsibleCommonOptions options) throws IOException {
		List<String> argumentos = new ArrayList<String>();
		agregarComunesPlaybook(argumentos, options, false);

		StringBuilder linea = new StringBuilder();
		for (String argumento : argumentos) {
			if (linea.length() > 0) {
				linea.append(' ');
			}
			linea.append(quoteShlex(argumento));
		}
		Files.write(destino, linea.toString().getBytes(StandardCharsets.UTF_8));
	}

	private static String quoteShlex(String valor) {
		if (valor == null) {
			return "''";
		}
		if (valor.matches("[A-Za-z0-9_./:=@,+-]+")) {
			return valor;
		}
		return "'" + valor.replace("'", "'\"'\"'") + "'";
	}
}

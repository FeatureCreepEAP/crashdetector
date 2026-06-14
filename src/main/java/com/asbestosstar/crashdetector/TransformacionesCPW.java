package com.asbestosstar.crashdetector;

import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;

import com.asbestosstar.crashdetector.lanzer.servicio.CDProfiler;
import com.asbestosstar.crashdetector.lanzer.servicio.CDSampler;
import com.asbestosstar.crashdetector.parches.Parche;

import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TargetType;
import cpw.mods.modlauncher.api.TransformerVoteResult;

public class TransformacionesCPW implements ITransformer<ClassNode> {

	private static final boolean JAVA_8 = esJava8();

	private static final boolean PROFILER_ACTIVO = !JAVA_8 && Boolean.getBoolean("crashdetector.cdprofiler");

	private static final boolean SAMPLER_ACTIVO = !JAVA_8 && Boolean.getBoolean("crashdetector.cdsampler");

	static {

		Transformaciones.init();

		// En Java 8, profiler/sampler se manejan por el agente, no por CPW.
		if (!JAVA_8) {

			// Inicializar GUI profiler
			if (PROFILER_ACTIVO) {
				CDProfiler.activarGUI();
			}

			// Inicializar GUI sampler
			if (SAMPLER_ACTIVO) {
				CDSampler.iniciarGUI();
			}
		}
	}

	@Override
	public ClassNode transform(ClassNode input, ITransformerVotingContext context) {

		// aplicar parches normales
		Parche.applicarParches(input, input.name);

		// profiler
		if (PROFILER_ACTIVO) {
			CDProfiler.instrumentarClassNode(input);
		}

		// sampler
		if (SAMPLER_ACTIVO) {
			CDSampler.instrumentarClassNode(input);
		}

		return input;
	}

	@Override
	public TransformerVoteResult castVote(ITransformerVotingContext context) {
		return TransformerVoteResult.YES;
	}

	@Override
	public Set<Target<ClassNode>> targets() {

		Set<Target<ClassNode>> resultado = obtenerTargetsDeParches();

		/*
		 * Java 8: No buscar todo el classpath. Solo usar los targets declarados por los
		 * parches.
		 *
		 * Esto evita que buscarJar agregue clases internas de Forge/ModLauncher/Log4j
		 * en Java 8, donde eso puede causar IncompatibleClassChangeError.
		 */
		if (JAVA_8) {
			return resultado;
		}

		if (!Statics.app_en_cdlauncher) {
			return resultado;
		}

		// CDLauncher todo el classpath, solo para Java 9+
		try {

			String classpath = System.getProperty("java.class.path");

			String[] entries = classpath.split(java.io.File.pathSeparator);

			for (String entry : entries) {

				java.io.File file = new java.io.File(entry);

				if (!file.exists())
					continue;

				if (file.isDirectory()) {

					buscarDirectory(file, file, resultado);

				} else if (entry.endsWith(".jar")) {

					buscarJar(file, resultado);
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return resultado;
	}

	private static Set<Target<ClassNode>> obtenerTargetsDeParches() {

		Set<Target<ClassNode>> resultado = new HashSet<>();

		for (Parche<?> parche : Parche.parches) {

			for (String clase : parche.clases()) {

				resultado.add(Target.targetClass(clase));
			}
		}

		return resultado;
	}

	private static void buscarDirectory(java.io.File root, java.io.File current, Set<Target<ClassNode>> result) {

		java.io.File[] files = current.listFiles();

		if (files == null)
			return;

		for (java.io.File file : files) {

			if (file.isDirectory()) {

				buscarDirectory(root, file, result);

			} else if (file.getName().endsWith(".class")) {

				String name = file.getAbsolutePath().substring(root.getAbsolutePath().length() + 1)
						.replace(java.io.File.separatorChar, '/').replace(".class", "");

				result.add(Target.targetClass(name));
			}
		}
	}

	private static void buscarJar(java.io.File jarFile, Set<Target<ClassNode>> result) {

		try (java.util.jar.JarFile jar = new java.util.jar.JarFile(jarFile)) {

			java.util.Enumeration<java.util.jar.JarEntry> entries = jar.entries();

			while (entries.hasMoreElements()) {

				java.util.jar.JarEntry entry = entries.nextElement();

				String name = entry.getName();

				if (name.endsWith(".class")) {

					name = name.substring(0, name.length() - 6);

					result.add(Target.targetClass(name));
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public TargetType<ClassNode> getTargetType() {
		return TargetType.CLASS;
	}

	private static boolean esJava8() {

		String version = System.getProperty("java.specification.version");

		if (version == null) {
			return false;
		}

		return version.equals("1.8") || version.startsWith("1.8");
	}
}
package com.asbestosstar.crashdetector;

import java.io.File;
import java.nio.file.Path;

import org.objectweb.asm.tree.ClassNode;

import com.asbestosstar.crashdetector.lanzer.servicio.CDProfiler;
import com.asbestosstar.crashdetector.lanzer.servicio.CDSampler;
import com.asbestosstar.crashdetector.parches.Parche;

import net.neoforged.neoforgespi.transformation.ClassProcessor;
import net.neoforged.neoforgespi.transformation.ProcessorName;

public class CrashDetectorFancyModLoader implements ClassProcessor {

	private static final boolean PROFILER_ACTIVO = Boolean.getBoolean("crashdetector.cdprofiler_wip");

	private static final boolean SAMPLER_ACTIVO = Boolean.getBoolean("crashdetector.cdsampler_wip");

	static {

		CargadoresComun.init(new Path[] { new File("mods/").toPath() }, CargadoresComun.CDOrigin.FANCYMODLOADER);

		Transformaciones.init();

		// Inicializar GUI profiler
		if (PROFILER_ACTIVO) {

			CDProfiler.activarGUI();
		}

		// Inicializar GUI sampler
		if (SAMPLER_ACTIVO) {

			CDSampler.iniciarGUI();
		}
	}

	@Override
	public ProcessorName name() {

		if (Statics.app_en_cdlauncher) {

			return new ProcessorName("cdmod", "fancymodloader");
		}

		return new ProcessorName("crashdetector", "fancymodloader");
	}

	@Override
	public boolean handlesClass(SelectionContext context) {

		// manejar todas las clases
		return true;
	}

	@Override
	public ComputeFlags processClass(TransformationContext context) {

		ClassNode input = context.node();

		// aplicar parches
		Parche.applicarParches(input, input.name);

		// profiler
		if (PROFILER_ACTIVO) {

			CDProfiler.instrumentarClassNode(input);
		}

		// sampler
		if (SAMPLER_ACTIVO) {

			CDSampler.instrumentarClassNode(input);
		}

		// necesario porque modificamos bytecode
		return ComputeFlags.COMPUTE_FRAMES;
	}
}

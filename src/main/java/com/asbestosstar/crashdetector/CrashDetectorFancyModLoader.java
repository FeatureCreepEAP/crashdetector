package com.asbestosstar.crashdetector;

import java.io.File;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.tree.ClassNode;

import com.asbestosstar.crashdetector.divisor.HolaMundoConsolaDivisidor;
import com.asbestosstar.crashdetector.parches.Parche;

import net.neoforged.neoforgespi.transformation.ClassProcessor;
import net.neoforged.neoforgespi.transformation.ProcessorName;

public class CrashDetectorFancyModLoader implements ClassProcessor {

	static {
		LogManager.getLogger(HolaMundoConsolaDivisidor.class).log(Level.ERROR, HolaMundoConsolaDivisidor.HOLA_MUNDO);
		if (!Statics.cargador) {
			Statics.cargador = true;
			Statics.carpetas_de_mods.add(new File("mods/").toPath());
			MonitorDePID.main(new String[] {});
			Transformaciones.init();
		}
	}

	@Override
	public ProcessorName name() {
		// TODO Auto-generated method stub
		return new ProcessorName("crashdetector", "fancymodloader");
	}

	@Override
	public boolean handlesClass(SelectionContext context) {
		// TODO Auto-generated method stub
		return true;// TODO
	}

	@Override
	public ComputeFlags processClass(TransformationContext context) {
		// TODO Auto-generated method stub
		ClassNode input = context.node();
		Parche.applicarParches(input, input.name);

		return ComputeFlags.COMPUTE_FRAMES;// TODO NONE si no editar
	}

}

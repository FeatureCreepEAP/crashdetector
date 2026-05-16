package com.asbestosstar.crashdetector;

import java.io.File;
import java.nio.file.Path;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.plugin.builtin.jvm.Plugin;

@Plugin("crashdetector")
public class CrashDetectorSponge {

	@Listener
	public void onConstruct(final ConstructPluginEvent event) {

		CargadoresComun.init(new Path[] { new File("plugins/").toPath() }, CargadoresComun.CDOrigin.SPONGE);
	}
}
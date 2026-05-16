package com.asbestosstar.crashdetector;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import io.github.codetoil.litlaunch.api.Command;
import io.github.codetoil.litlaunch.api.EnumRequireOrNot;
import io.github.codetoil.litlaunch.api.IMod;
import io.github.codetoil.litlaunch.api.event.ILitEvent;
import io.github.codetoil.litlaunch.api.event.ILitEventHandler;
import io.github.codetoil.litlaunch.api.event.ILitEventHandler.IEventListener;

public class CrashDetectorLitLaunch implements IMod, IEventListener {

	static {
		CargadoresComun.init(new Path[] { new File("mods/").toPath() }, CargadoresComun.CDOrigin.LITLAUNCH);

		Transformaciones.init();
	}

	public CrashDetectorLitLaunch() {
	}

	@Override
	public EnumRequireOrNot onClient() {
		return EnumRequireOrNot.COMPATIBLE;
	}

	@Override
	public EnumRequireOrNot onServer() {
		return EnumRequireOrNot.COMPATIBLE;
	}

	@Override
	public String getModID() {
		return "crashdetector";
	}

	@Override
	public String getVersion() {
		return "0.0.1";
	}

	@Override
	public List<Command> getCommandList() {
		return Collections.emptyList();
	}

	@Override
	public void construction() {

	}

	@Override
	public void preInit() {
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
	}

	@Override
	public void serverLoad() {
	}

	public IEventListener getListener() {
		return this;
	}

	@Override
	public IMod getModINSTANCE() {
		return this;
	}

	@Override
	public void receivedEvent(ILitEvent arg0, ILitEventHandler arg1) {
		// TODO Auto-generated method stub

	}
}
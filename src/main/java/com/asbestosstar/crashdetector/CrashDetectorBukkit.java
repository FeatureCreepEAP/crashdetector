package com.asbestosstar.crashdetector;

import java.io.File;
import java.nio.file.Path;

import org.bukkit.plugin.java.JavaPlugin;

public class CrashDetectorBukkit  extends JavaPlugin{

	static {
	CargadoresComun.init(new Path[] { new File("plugins/").toPath() }, CargadoresComun.CDOrigin.BUKKIT);//Bukkit no suporte coremods mucho
	}
	
	
	
	
}

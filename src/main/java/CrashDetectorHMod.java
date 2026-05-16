public class CrashDetectorHMod extends Plugin {
	static {
		try {
			com.asbestosstar.crashdetector.CargadoresComun.init(
					new java.nio.file.Path[] { new java.io.File("plugins/").toPath() },
					com.asbestosstar.crashdetector.CargadoresComun.CDOrigin.HMOD);
			com.asbestosstar.crashdetector.Transformaciones.init();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Override
	public void initialize() {
	}

	@Override
	public void enable() {
	}

	@Override
	public void disable() {
	}
}
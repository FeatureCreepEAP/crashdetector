public class mod_CrashDetector extends BaseMod {

	static {
		try {
			com.asbestosstar.crashdetector.CargadoresComun.init(
					new java.nio.file.Path[] { new java.io.File("mods/").toPath() },
					com.asbestosstar.crashdetector.CargadoresComun.CDOrigin.RISUGAMI);

			com.asbestosstar.crashdetector.Transformaciones.init();

			registrarTransformadorLaunchWrapperSiExiste();

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Override
	public String getVersion() {
		return "CrashDetector";
	}

	@Override
	public void load() {

	}

	private static void registrarTransformadorLaunchWrapperSiExiste() {
		try {
			Class<?> claseLaunch = Class.forName("net.minecraft.launchwrapper.Launch");
			java.lang.reflect.Field campoClassLoader = claseLaunch.getDeclaredField("classLoader");
			campoClassLoader.setAccessible(true);

			Object classLoader = campoClassLoader.get(null);
			if (classLoader == null) {
				return;
			}

			Class<?> claseLaunchClassLoader = Class.forName("net.minecraft.launchwrapper.LaunchClassLoader");

			if (!claseLaunchClassLoader.isInstance(classLoader)) {
				return;
			}

			java.lang.reflect.Method metodoRegistrar = claseLaunchClassLoader.getMethod("registerTransformer",
					String.class);

			metodoRegistrar.invoke(classLoader, "com.asbestosstar.crashdetector.LaunchWrapperTransformaciones");

		} catch (ClassNotFoundException e) {
			// LaunchWrapper no existe. Ignorar.
		} catch (NoSuchFieldException e) {
			// LaunchWrapper existe, pero no tiene Launch.classLoader. Ignorar.
		} catch (NoSuchMethodException e) {
			// LaunchClassLoader existe, pero no tiene registerTransformer(String). Ignorar.
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
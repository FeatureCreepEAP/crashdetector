package com.asbestosstar.crashdetector.app.lanzermod.tlauncher.mixin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.asbestosstar.crashdetector.app.lanzermod.tlauncher.CrashDetectorTLauncher;

import net.minecraft.launcher.process.JavaProcess;
import net.minecraft.launcher.process.JavaProcessLauncher;
import net.minecraft.launcher.versions.CompleteVersion;

import org.tlauncher.tlauncher.minecraft.launcher.MinecraftLauncher;

/**
 * Conecta el ciclo de vida real de MinecraftLauncher con CrashDetector.
 *
 * La clase objetivo no usa mapeos de Minecraft, por lo que toda la
 * configuración trabaja con remap = false.
 */
@Mixin(value = MinecraftLauncher.class, remap = false)
public abstract class MinecraftLauncherMixin {

	@Shadow
	private JavaProcessLauncher launcher;

	@Shadow
	private JavaProcess process;

	@Shadow
	private long startupTime;

	@Shadow
	private File runningMinecraftDir;

	@Shadow
	private CompleteVersion version;

	/**
	 * Se ejecuta justo después de:
	 *
	 * this.process = this.launcher.start();
	 *
	 * En ese punto el proceso ya existe, el comando completo sigue disponible en el
	 * ProcessBuilder y startupTime ya fue establecido.
	 */
	@Inject(method = "launchMinecraft()V", at = @At(value = "FIELD", target = "Lorg/tlauncher/tlauncher/minecraft/launcher/MinecraftLauncher;"
			+ "process:Lnet/minecraft/launcher/process/JavaProcess;", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER), require = 1)
	private void crashDetector$registrarProcesoMinecraft(CallbackInfo informacion) {

		if (this.process == null || this.launcher == null) {
			return;
		}

		Process procesoReal = this.process.getRawProcess();

		/*
		 * createProcess() devuelve el mismo ProcessBuilder que ya fue utilizado por
		 * start(), porque JavaProcessLauncher lo conserva en su campo "process".
		 */
		List<String> comandoCompleto = new ArrayList<String>(this.launcher.createProcess().command());

		String clasePrincipal = this.version == null ? null : this.version.getMainClass();

		CrashDetectorTLauncher.registrarProcesoMinecraft(procesoReal, comandoCompleto, clasePrincipal,
				this.runningMinecraftDir, this.startupTime);
	}

	/**
	 * TLauncher primero ejecuta notifyClose(), después obtiene jp.getExitCode() y
	 * finalmente lo guarda en this.exitCode. Se captura justo después de esa
	 * asignación, antes de CrashDescriptor.scan().
	 */
	@Inject(method = "onJavaProcessEnded"
			+ "(Lnet/minecraft/launcher/process/JavaProcess;)V", at = @At(value = "FIELD", target = "Lorg/tlauncher/tlauncher/minecraft/launcher/MinecraftLauncher;"
					+ "exitCode:I", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER), require = 1)
	private void crashDetector$registrarCodigoSalida(JavaProcess proceso, CallbackInfo informacion) {

		CrashDetectorTLauncher.registrarSalidaPendiente(proceso.getExitCode());
	}

	/**
	 * Conserva el error si JavaProcess lo comunica fuera del cierre normal.
	 */
	@Inject(method = "onJavaProcessError" + "(Lnet/minecraft/launcher/process/JavaProcess;"
			+ "Ljava/lang/Throwable;)V", at = @At("HEAD"), require = 1)
	private void crashDetector$registrarErrorProceso(JavaProcess proceso, Throwable error, CallbackInfo informacion) {

		CrashDetectorTLauncher.finalizarDesdeError(error);
	}

	/**
	 * Punto preparado para análisis incremental. No duplica la línea en consola.
	 */
	@Inject(method = "onJavaProcessLog" + "(Lnet/minecraft/launcher/process/JavaProcess;"
			+ "Ljava/lang/String;)V", at = @At("HEAD"), require = 1)
	private void crashDetector$recibirLineaMinecraft(JavaProcess proceso, String linea, CallbackInfo informacion) {

		CrashDetectorTLauncher.recibirLineaMinecraft(linea);
	}
}

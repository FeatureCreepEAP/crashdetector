package com.asbestosstar.crashdetector.app.lanzermod.tlauncher.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.asbestosstar.crashdetector.app.lanzermod.tlauncher.CrashDetectorTLauncher;

import org.tlauncher.tlauncher.minecraft.crash.Crash;
import org.tlauncher.tlauncher.minecraft.crash.CrashDescriptor;

/**
 * Recibe la clasificación final de TLauncher.
 *
 * scan() devuelve:
 *
 * - null cuando TLauncher considera que no hubo crash; - Crash cuando detectó
 * una salida anormal, reconocida o no reconocida.
 */
@Mixin(value = CrashDescriptor.class, remap = false)
public abstract class CrashDescriptorMixin {

	@Inject(method = "scan()Lorg/tlauncher/tlauncher/minecraft/crash/Crash;", at = @At("RETURN"), require = 1)
	private void crashDetector$alTerminarEscaneo(CallbackInfoReturnable<Crash> retorno) {

		CrashDetectorTLauncher.finalizarDesdeDescriptor(retorno.getReturnValue());
	}
}

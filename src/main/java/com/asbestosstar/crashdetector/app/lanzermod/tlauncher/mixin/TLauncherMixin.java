package com.asbestosstar.crashdetector.app.lanzermod.tlauncher.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.tlauncher.tlauncher.rmo.TLauncher;

/**
 * Mixin exclusivo para la variante TLauncher.
 *
 * remap = false es obligatorio porque las clases de TLauncher no usan los
 * mapeos de Minecraft/Fabric.
 */
@Pseudo
@Mixin(TLauncher.class)
public abstract class TLauncherMixin {

}

package com.asbestosstar.crashdetector.fabricmctransformaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import com.asbestosstar.crashdetector.Transformaciones;
import com.asbestosstar.crashdetector.cargador.Cargador;
import com.asbestosstar.crashdetector.parches.Parche;

public class CoreModFabricMC implements IMixinConfigPlugin {

	// El interruptor de apagado global (kill-switch) existe porque sponge una la
	// etiqu mismo de ML en Manifest
	private static final boolean MODLAUNCHER_DETECTADO;

	static {
		// Evaluamos el entorno inmediatamente al cargar la clase en memoria
		MODLAUNCHER_DETECTADO = Cargador.claseExiste("cpw.mods.modlauncher.api.TargetType")
				|| Cargador.claseExiste("net.neoforged.neoforgespi.transformation.ClassProcessor")
				|| Cargador.claseExiste("cpw.mods.modlauncher.api.ITransformationService");

		// Límite estricto: ¡Solo inicializar transformaciones si el entorno es seguro!
		if (!MODLAUNCHER_DETECTADO) {
			Transformaciones.init();
		}
	}

	@Override
	public void onLoad(String mixinPackage) {
		// No se requiere inicialización adicional aquí
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		// Le indicamos a Mixin que no procese ningún mixin asociado si estamos en
		// ModLauncher
		if (MODLAUNCHER_DETECTADO) {
			return false;
		}
		return true;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
		// No se requiere procesamiento de objetivos adicionales
	}

	@Override
	public List<String> getMixins() {
		// Devolvemos una lista vacía ya que la configuración se maneja externamente o
		// de forma dinámica
		return new ArrayList<String>();
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
		// No se realizan acciones antes de la aplicación del mixin
	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
		// Red de seguridad absoluta: no permitir que los transformadores de bytecode
		// alteren nada
		if (MODLAUNCHER_DETECTADO) {
			return;
		}

		// Aplicación segura de parches ASM directos sobre el bytecode de la clase
		// objetivo
		Parche.applicarParches(targetClass, targetClassName);
	}
}
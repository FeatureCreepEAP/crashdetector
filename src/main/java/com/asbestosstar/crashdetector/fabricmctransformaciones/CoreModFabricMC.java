package com.asbestosstar.crashdetector.fabricmctransformaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import com.asbestosstar.crashdetector.Transformaciones;

public class CoreModFabricMC implements IMixinConfigPlugin{

	@Override
	public void onLoad(String mixinPackage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRefMapperConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getMixins() {
		// TODO Auto-generated method stub
		return new ArrayList<String>();
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
		// TODO Auto-generated method stub
		Transformaciones.transformarASM(targetClassName.replace("/", "."), targetClass);
	}

}

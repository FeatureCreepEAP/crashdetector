package com.asbestosstar.crashdetector;

import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;

import com.asbestosstar.crashdetector.parches.Parche;

import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TargetType;
import cpw.mods.modlauncher.api.TransformerVoteResult;

public class TransformacionesCPW implements  ITransformer<ClassNode> {

	static{
		Transformaciones.init();
	}
	
	
	// Para CPW Transformer
	@Override
	public ClassNode transform(ClassNode input, ITransformerVotingContext context) {
		// TODO Auto-generated method stub
		Parche.applicarParches(input, input.name);
		return input;
	}

	@Override
	public TransformerVoteResult castVote(ITransformerVotingContext context) {
		// TODO Auto-generated method stub
		return TransformerVoteResult.YES;
	}

	@Override
	public Set targets() {
		// TODO Auto-generated method stub
		Set<Target> resultdo = new HashSet<Target>();
		//resulto.add(Target.targetClass("net.minecraft.server.MinecraftServer"));
		//resulto.add(Target.targetClass("net.minecraft.client.Minecraft"));
		for(Parche<?> parche:Parche.parches) {
			for(String clase:parche.clases()) {
				resultdo.add(Target.targetClass(clase));
			}
			
			
		}
		
		
		return resultdo;
	}

	//@Override
	//public TargetType<ClassNode> getTargetType() {
	public cpw.mods.modlauncher.api.TargetType getTargetType() {
		// TODO Auto-generated method stub
		return TargetType.CLASS;
	}

}

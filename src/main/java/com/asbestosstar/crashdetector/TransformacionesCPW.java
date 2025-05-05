package com.asbestosstar.crashdetector;

import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;

import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TransformerVoteResult;

public class TransformacionesCPW implements  ITransformer<ClassNode> {

	// Para CPW Transformer
	@Override
	public ClassNode transform(ClassNode input, ITransformerVotingContext context) {
		// TODO Auto-generated method stub
		return Transformaciones.transformar(input.name.replace("/", "."), input);
	}

	@Override
	public TransformerVoteResult castVote(ITransformerVotingContext context) {
		// TODO Auto-generated method stub
		return TransformerVoteResult.YES;
	}

	@Override
	public Set<Target> targets() {
		// TODO Auto-generated method stub
		Set<Target> resulto = new HashSet<Target>();
		resulto.add(Target.targetClass("net.minecraft.server.MinecraftServer"));
		resulto.add(Target.targetClass("net.minecraft.client.Minecraft"));

		return resulto;
	}

}

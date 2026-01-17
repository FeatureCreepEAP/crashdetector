package com.asbestosstar.crashdetector.fabricmctransformaciones;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = { "net.minecraft.class_310", "net.minecraft.server.MinecraftServer",
		"org.watermedia.loaders.FavricMCLoader"

//		,"com.mojang.authlib.yggdrasil.YggdrasilUserApiService" No esta reliable

})
//@Mixin()
public class ClasesParaTransformacionFabricMC {

}

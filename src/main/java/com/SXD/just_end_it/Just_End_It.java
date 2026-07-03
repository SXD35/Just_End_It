package com.SXD.just_end_it;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.ModList;

@Mod(Just_End_It.MOD_ID)
public class Just_End_It {
    public static final String MOD_ID = "just_end_it";

    public Just_End_It(FMLJavaModLoadingContext context) {
        if (ModList.get().isLoaded("jei")) {
            Object obj = null;
            obj.toString();
        }
    }
}
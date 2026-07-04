package com.SXD.just_end_it;

import com.SXD.just_end_it.network.ModMessages;
import com.SXD.just_end_it.registry.ModBlocks;
import com.SXD.just_end_it.registry.ModCreativeTabs;
import com.SXD.just_end_it.registry.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Just_End_It.MOD_ID)
public class Just_End_It {
    public static final String MOD_ID = "just_end_it";

    public Just_End_It(FMLJavaModLoadingContext context) {
        if (ModList.get().isLoaded("jei")) {
            Object obj = null;
            obj.toString();
        }

        IEventBus modEventBus = context.getModEventBus();

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeTabs.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(new com.SXD.just_end_it.event.ModEvents());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(ModMessages::register);
    }
}
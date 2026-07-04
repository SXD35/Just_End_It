package com.SXD.just_end_it.network;

import com.SXD.just_end_it.Just_End_It;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Just_End_It.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int id = 0;

    public static void register() {
        INSTANCE.registerMessage(id++, PacketOpenWebsite.class, PacketOpenWebsite::toBytes, PacketOpenWebsite::new, PacketOpenWebsite::handle);
        INSTANCE.registerMessage(id++, PacketTransformBlock.class, PacketTransformBlock::toBytes, PacketTransformBlock::new, PacketTransformBlock::handle);
    }
}
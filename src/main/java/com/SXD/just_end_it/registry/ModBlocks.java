package com.SXD.just_end_it.registry;

import com.SXD.just_end_it.Just_End_It;
import com.SXD.just_end_it.block.GenshinImpactBlock;
import com.SXD.just_end_it.block.GenshinImpactOreBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Just_End_It.MOD_ID);

    public static final RegistryObject<Block> GENSHIN_IMPACT_ORE = BLOCKS.register("genshin_impact_ore",
            () -> new GenshinImpactOreBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(3.0F, 3.0F)
                    .sound(SoundType.STONE)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> GENSHIN_IMPACT_BLOCK = BLOCKS.register("genshin_impact_block",
            () -> new GenshinImpactBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(5.0F, 6.0F)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()));

    public static void register(net.minecraftforge.eventbus.api.IEventBus bus) {
        BLOCKS.register(bus);
    }
}
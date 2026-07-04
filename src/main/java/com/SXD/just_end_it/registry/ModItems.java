package com.SXD.just_end_it.registry;

import com.SXD.just_end_it.Just_End_It;
import com.SXD.just_end_it.item.GenshinImpactIngotItem;
import com.SXD.just_end_it.item.GenshinImpactSwordItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Just_End_It.MOD_ID);

    public static final RegistryObject<Item> GENSHIN_IMPACT_ORE = ITEMS.register("genshin_impact_ore",
            () -> new BlockItem(ModBlocks.GENSHIN_IMPACT_ORE.get(), new Item.Properties()));

    public static final RegistryObject<Item> GENSHIN_IMPACT_BLOCK = ITEMS.register("genshin_impact_block",
            () -> new BlockItem(ModBlocks.GENSHIN_IMPACT_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> GENSHIN_IMPACT_ROUGH = ITEMS.register("genshin_impact_rough",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GENSHIN_IMPACT_INGOT = ITEMS.register("genshin_impact_ingot",
            () -> new GenshinImpactIngotItem(new Item.Properties()));

    public static final RegistryObject<Item> GENSHIN_IMPACT_SWORD = ITEMS.register("genshin_impact_sword",
            () -> new GenshinImpactSwordItem(Tiers.DIAMOND, 200, -2.4F, new Item.Properties().durability(1561)));

    public static void register(net.minecraftforge.eventbus.api.IEventBus bus) {
        ITEMS.register(bus);
    }
}
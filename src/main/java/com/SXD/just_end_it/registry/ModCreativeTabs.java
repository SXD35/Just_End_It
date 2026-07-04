package com.SXD.just_end_it.registry;

import com.SXD.just_end_it.Just_End_It;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Just_End_It.MOD_ID);

    public static final RegistryObject<CreativeModeTab> JUST_END_IT_TAB = CREATIVE_TABS.register("just_end_it_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.just_end_it"))
                    .icon(() -> new ItemStack(ModItems.GENSHIN_IMPACT_BLOCK.get()))
                    .displayItems((params, output) -> {
                        output.accept(ModItems.GENSHIN_IMPACT_ORE.get());
                        output.accept(ModItems.GENSHIN_IMPACT_ROUGH.get());
                        output.accept(ModItems.GENSHIN_IMPACT_INGOT.get());
                        output.accept(ModItems.GENSHIN_IMPACT_BLOCK.get());
                        output.accept(ModItems.GENSHIN_IMPACT_SWORD.get());
                    })
                    .build());

    public static void register(net.minecraftforge.eventbus.api.IEventBus bus) {
        CREATIVE_TABS.register(bus);
    }
}
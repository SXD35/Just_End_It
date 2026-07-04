package com.SXD.just_end_it.event;

import com.SXD.just_end_it.network.ModMessages;
import com.SXD.just_end_it.network.PacketOpenWebsite;
import com.SXD.just_end_it.registry.ModBlocks;
import com.SXD.just_end_it.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkDirection;

public class ModEvents {

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getPlacedBlock().getBlock() == ModBlocks.GENSHIN_IMPACT_BLOCK.get()) {
            if (event.getEntity() instanceof Player player && !event.getLevel().isClientSide()) {
                System.out.println("[Just End It] 放置原神方块，发送打开官网网络包");
                sendOpenWebsite(player);
            }
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        BlockState state = event.getState();
        Level level = (Level) event.getLevel();
        Player player = event.getPlayer();
        BlockPos pos = event.getPos();

        if (state.getBlock() == ModBlocks.GENSHIN_IMPACT_ORE.get()) {
            if (!level.isClientSide) {
                System.out.println("[Just End It] 挖掘原神矿石，发送打开官网网络包");
                sendOpenWebsite(player);
                event.setCanceled(true);
                level.removeBlock(pos, false);
                int fortune = EnchantmentHelper.getItemEnchantmentLevel(
                        BuiltInRegistries.ENCHANTMENT.get(new ResourceLocation("minecraft", "fortune")),
                        player.getMainHandItem()
                );
                int count = 1 + (fortune > 0 ? level.random.nextInt(fortune + 1) : 0);
                ItemStack drop = new ItemStack(ModItems.GENSHIN_IMPACT_ROUGH.get(), count);
                popResource(level, pos, drop);
                player.getMainHandItem().hurt(1, level.random, (ServerPlayer) player);
                ExperienceOrb.award((ServerLevel) level, pos.getCenter(), 1 + level.random.nextInt(3));
            }
        }

        if (state.getBlock() == ModBlocks.GENSHIN_IMPACT_BLOCK.get()) {
            if (!level.isClientSide) {
                System.out.println("[Just End It] 挖掘原神方块，发送打开官网网络包");
                sendOpenWebsite(player);
            }
        }
    }

    private void popResource(Level level, BlockPos pos, ItemStack stack) {
        if (level instanceof ServerLevel serverLevel) {
            net.minecraft.world.entity.item.ItemEntity itemEntity = new net.minecraft.world.entity.item.ItemEntity(
                    serverLevel, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack
            );
            serverLevel.addFreshEntity(itemEntity);
        }
    }

    @SubscribeEvent
    public void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        ItemStack result = event.getCrafting();
        if (result.getItem() == ModItems.GENSHIN_IMPACT_BLOCK.get()) {
            Player player = event.getEntity();
            System.out.println("[Just End It] 工作台合成原神方块，发送打开官网网络包");
            if (!player.level().isClientSide) {
                sendOpenWebsite(player);
            }
        }
    }

    @SubscribeEvent
    public void onItemSmelted(PlayerEvent.ItemSmeltedEvent event) {
        if (event.getSmelting().getItem() == ModItems.GENSHIN_IMPACT_INGOT.get()) {
            Player player = event.getEntity();
            System.out.println("[Just End It] 熔炉取出原神锭，发送打开官网网络包");
            if (!player.level().isClientSide) {
                sendOpenWebsite(player);
            }
        }
    }

    @SubscribeEvent
    public void onContainerOpen(PlayerContainerEvent.Open event) {
        if (event.getEntity().level().isClientSide) return;
        AbstractContainerMenu container = event.getContainer();
        if (container instanceof FurnaceMenu) {
            Player player = event.getEntity();
            container.addSlotListener(new net.minecraft.world.inventory.ContainerListener() {
                private ItemStack lastInput = ItemStack.EMPTY;
                @Override
                public void slotChanged(AbstractContainerMenu containerToSend, int slotInd, ItemStack stack) {
                    if (slotInd == 0) {
                        if (!stack.isEmpty() && stack.getItem() == ModItems.GENSHIN_IMPACT_ROUGH.get()) {
                            if (lastInput.isEmpty() || lastInput.getCount() < stack.getCount()) {
                                System.out.println("[Just End It] 熔炉放入粗原神，发送打开官网网络包");
                                if (player instanceof ServerPlayer serverPlayer) {
                                    ModMessages.INSTANCE.sendTo(
                                            new PacketOpenWebsite(),
                                            serverPlayer.connection.connection,
                                            NetworkDirection.PLAY_TO_CLIENT
                                    );
                                }
                            }
                        }
                        lastInput = stack.copy();
                    }
                }
                @Override
                public void dataChanged(AbstractContainerMenu containerMenu, int dataSlotIndex, int value) {}
            });
        }
    }

    private void sendOpenWebsite(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            ModMessages.INSTANCE.sendTo(
                    new PacketOpenWebsite(),
                    serverPlayer.connection.connection,
                    NetworkDirection.PLAY_TO_CLIENT
            );
        }
    }
}
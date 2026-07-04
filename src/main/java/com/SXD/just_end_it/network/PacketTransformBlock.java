package com.SXD.just_end_it.network;

import com.SXD.just_end_it.registry.ModBlocks;
import com.SXD.just_end_it.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketTransformBlock {
    private final BlockPos pos;

    public PacketTransformBlock(BlockPos pos) {
        this.pos = pos;
    }

    public PacketTransformBlock(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                ServerLevel level = player.serverLevel();
                if (level.getBlockState(pos).getBlock() == ModBlocks.GENSHIN_IMPACT_BLOCK.get()) {
                    ItemStack held = player.getMainHandItem();
                    if (held.getItem() == Items.DIAMOND_SWORD) {
                        ItemStack swordStack = new ItemStack(ModItems.GENSHIN_IMPACT_SWORD.get(), 1);
                        swordStack.setTag(held.getTag());
                        player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, swordStack);
                        level.removeBlock(pos, false);
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
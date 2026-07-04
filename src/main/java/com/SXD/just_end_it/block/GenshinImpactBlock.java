package com.SXD.just_end_it.block;

import com.SXD.just_end_it.network.ModMessages;
import com.SXD.just_end_it.network.PacketTransformBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.PacketDistributor;

import java.awt.*;
import java.net.URI;

public class GenshinImpactBlock extends Block {
    public GenshinImpactBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            ItemStack heldItem = player.getItemInHand(hand);
            if (heldItem.getItem() == Items.DIAMOND_SWORD) {
                System.out.println("[Just End It] 客户端：钻石剑右击原神方块，尝试转化并打开官网");
                level.removeBlock(pos, false);
                tryOpenWebsite();
                ModMessages.INSTANCE.send(PacketDistributor.SERVER.noArg(), new PacketTransformBlock(pos));
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    private void tryOpenWebsite() {
        String url = "https://ys.mihoyo.com";
        System.out.println("[Just End It] 尝试打开: " + url);
        if (!GraphicsEnvironment.isHeadless() && Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(url));
                System.out.println("[Just End It] Desktop 方式打开成功");
                return;
            } catch (Exception e) {
                System.out.println("[Just End It] Desktop 方式失败: " + e);
            }
        }
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            System.out.println("[Just End It] rundll32 方式打开成功");
            return;
        } catch (Exception e) {
            System.out.println("[Just End It] rundll32 方式失败: " + e);
        }
        try {
            net.minecraft.Util.getPlatform().openUri(new URI(url));
            System.out.println("[Just End It] Util.getPlatform 方式打开成功");
            return;
        } catch (Exception e) {
            System.out.println("[Just End It] Util.getPlatform 方式失败: " + e);
        }
        System.out.println("[Just End It] 所有方式均失败");
    }
}
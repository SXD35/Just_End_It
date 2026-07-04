package com.SXD.just_end_it.item;

import com.SXD.just_end_it.network.ModMessages;
import com.SXD.just_end_it.network.PacketOpenWebsite;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraftforge.network.NetworkDirection;

public class GenshinImpactSwordItem extends SwordItem {
    public GenshinImpactSwordItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide && attacker instanceof ServerPlayer serverPlayer) {
            System.out.println("[Just End It] 原神之剑攻击实体，发送打开官网网络包");
            ModMessages.INSTANCE.sendTo(
                    new PacketOpenWebsite(),
                    serverPlayer.connection.connection,
                    NetworkDirection.PLAY_TO_CLIENT
            );
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
package com.SXD.just_end_it.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.awt.*;
import java.net.URI;
import java.util.function.Supplier;

public class PacketOpenWebsite {
    public PacketOpenWebsite() {}

    public PacketOpenWebsite(FriendlyByteBuf buf) {}

    public void toBytes(FriendlyByteBuf buf) {}

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().execute(() -> {
                System.out.println("[Just End It] 收到打开官网网络包");
                tryOpenWebsite();
            });
        });
        ctx.get().setPacketHandled(true);
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
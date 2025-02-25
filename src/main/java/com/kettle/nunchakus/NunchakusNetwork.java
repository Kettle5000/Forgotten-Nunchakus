package com.kettle.nunchakus;

import java.util.Objects;

import com.kettle.nunchakus.render.LightningEffectPacket;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class NunchakusNetwork {
    private static final ResourceLocation NAME = new ResourceLocation("fn", "network");

    public static SimpleChannel channel;

    public static void init() {
        channel = NetworkRegistry.ChannelBuilder.named(NAME)
                .clientAcceptedVersions(s -> Objects.equals(s, "3"))
                .serverAcceptedVersions(s -> Objects.equals(s, "3"))
                .networkProtocolVersion(() -> "3")
                .simpleChannel();
        
        channel.messageBuilder(LeftClickStatePacket.class, 1)
        .decoder(LeftClickStatePacket::decode)
        .encoder(LeftClickStatePacket::encode)
        .consumerMainThread(LeftClickStatePacket::handle)
        .add();
        channel.messageBuilder(LightningEffectPacket.class, 2)
        .decoder(LightningEffectPacket::decode)
        .encoder(LightningEffectPacket::encode)
        .consumerMainThread(LightningEffectPacket::handle)
        .add();
        
    }
}

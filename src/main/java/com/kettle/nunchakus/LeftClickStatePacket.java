package com.kettle.nunchakus;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LeftClickStatePacket {
    private final boolean leftClickHeld;

    public LeftClickStatePacket(boolean leftClickHeld) {
        this.leftClickHeld = leftClickHeld;
    }

    // Encode to the network
    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(leftClickHeld);
    }
    
    public static LeftClickStatePacket decode(FriendlyByteBuf buf) {
        return new LeftClickStatePacket(buf.readBoolean());
    }

    public boolean isLeftClickHeld() {
        return leftClickHeld;
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            LeftClickStateManager.set(ctx.getSender(), leftClickHeld);
        });
        ctx.setPacketHandled(true);
    }
}

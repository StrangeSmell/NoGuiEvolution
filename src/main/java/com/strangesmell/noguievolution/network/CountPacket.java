package com.strangesmell.noguievolution.network;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;
//Not currently used

public class CountPacket {

    private final int breakCount;


    public CountPacket(int breakCount) {
        this.breakCount = breakCount;
    }

    public CountPacket(FriendlyByteBuf buf) {
        breakCount = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(breakCount);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        var ctx = supplier.get();
        ServerPlayer serverPlayer = ctx.getSender();

        serverPlayer.getStats().getValue(Stats.BLOCK_MINED,serverPlayer.getBlockStateOn().getBlock());
        ctx.setPacketHandled(true);


        return true;
    }


}

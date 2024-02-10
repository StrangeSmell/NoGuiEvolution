package com.strangesmell.noguievolution.event;

import com.strangesmell.noguievolution.Config;
import com.strangesmell.noguievolution.NoGuiEvolution;
import com.strangesmell.noguievolution.Util.Utils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)

public class ClimbEvent {
    @SubscribeEvent
    public static void climbEvent(PlayerEvent event){
       Player player = event.getEntity();
       if(player==null) return;
        BlockState blockState = player.getBlockStateOn();
        boolean canLadder;
        canLadder = player.getBlockStateOn().getBlock().isLadder(blockState, player.level(),player.getOnPos(),player);
        if(canLadder){
            int count = 0;
            if(!event.getEntity().level().isClientSide){
                ServerPlayer serverPlayer =(ServerPlayer) event.getEntity();
                count = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.CLIMB_ONE_CM));
                AttributeModifier countModifier = new AttributeModifier(" count ", count, AttributeModifier.Operation.ADDITION);
                serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()).removeModifiers();
                serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()).addPermanentModifier(countModifier);
            }

            if(event.getEntity().level().isClientSide){
                LocalPlayer localPlayer =(LocalPlayer) event.getEntity();
                count =(int) localPlayer.getAttributeValue(NoGuiEvolution.COUNT_ATTRIBUTE.get());
            }

            if(count>= Config.climbNumberLimit) count = Config.climbNumberLimit;

            double time = count*Config.climbNumberCoefficient;
            double x = player.getDeltaMovement().get(Direction.Axis.X);
            double y = player.getDeltaMovement().get(Direction.Axis.Y);
            double z = player.getDeltaMovement().get(Direction.Axis.Z);

            float i = 1;
            if(y<0) i=-1;

            y=i*0.2*(1+time);

            Utils.limit(y, Config.climbSpeedLimit);
            if(y<-Config.climbSpeedLimit) y=-Config.climbSpeedLimit;

            player.setDeltaMovement(x,y,z);

            if(player.isShiftKeyDown()) player.setDeltaMovement(x,0,z);
        }
    }
}

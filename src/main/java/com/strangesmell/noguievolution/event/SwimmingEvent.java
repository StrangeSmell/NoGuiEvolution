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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)

public class SwimmingEvent {
    @SubscribeEvent
    public static void swimmingEvent(MovementInputUpdateEvent event){
        Player player = event.getEntity();
        if(player.isSwimming()){
            int count = 0;
            if(!event.getEntity().level().isClientSide){
                ServerPlayer serverPlayer =(ServerPlayer) event.getEntity();
                count = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.SWIM_ONE_CM));
                AttributeModifier minedCountModifier = new AttributeModifier(" count ", count, AttributeModifier.Operation.ADDITION);
                serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()).removeModifiers();
                serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()).addPermanentModifier(minedCountModifier);
            }

            if(event.getEntity().level().isClientSide){
                LocalPlayer localPlayer =(LocalPlayer) event.getEntity();
                count =(int) localPlayer.getAttributeValue(NoGuiEvolution.COUNT_ATTRIBUTE.get());
            }

            if(count>=Config.swimNumberLimit) count = Config.swimNumberLimit;

            double time = count*Config.swimNumberCoefficient;

            Utils.limit(time,Config.swimSpeedLimit);
            double x = player.getDeltaMovement().get(Direction.Axis.X);
            double y = player.getDeltaMovement().get(Direction.Axis.Y);
            double z = player.getDeltaMovement().get(Direction.Axis.Z);
            double limit = time;
            if(x>-limit&&y>-limit&&z>-limit&&x<limit&&y<limit&&z<limit){
                player.setDeltaMovement(x*(1+time),y*(1+time),z*(1+time));
            }
        }
    }
}

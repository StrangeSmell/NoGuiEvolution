package com.strangesmell.noguievolution.event;


import com.strangesmell.noguievolution.Config;
import com.strangesmell.noguievolution.NoGuiEvolution;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)

public class JumpEvent {
    @SubscribeEvent
    public static void jumpEvent(LivingEvent.LivingJumpEvent event){
        LivingEntity livingEntity = event.getEntity();
        if(livingEntity instanceof Player player){
            int count=0;
            if(!event.getEntity().level().isClientSide){
                ServerPlayer serverPlayer =(ServerPlayer) event.getEntity();
                count = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.JUMP));

                //forget begin
                long nowTime = player.level().getGameTime();
                long lastTime = player.getPersistentData().getLong("jumpLastTime");
                int x = (int) ((nowTime - lastTime)/Config.forgetTime);
                serverPlayer.getStats().setValue(player,Stats.CUSTOM.get(Stats.JUMP),(int)(count * Math.pow(Config.forgetCoefficient,x)));
                player.getPersistentData().putLong("jumpLastTime",nowTime);
                count = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.JUMP));
                //forget end
                AttributeModifier countModifier2 = serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get())
                        .getModifier(NoGuiEvolution.uuid);
                if (countModifier2 != null) {
                    serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()).removeModifier(countModifier2);
                }
                AttributeModifier countModifier = new AttributeModifier(NoGuiEvolution.uuid," count ", count, AttributeModifier.Operation.ADDITION);
                serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()).addPermanentModifier(countModifier);
                AttributeModifier countModifier3 = serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get())
                        .getModifier(NoGuiEvolution.uuid);
                if (countModifier3 != null) {
                    serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()).removeModifier(countModifier2);
                }
            }else{

                count =(int) livingEntity.getAttributeValue(NoGuiEvolution.COUNT_ATTRIBUTE.get());
                AttributeModifier countModifier2 = player.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get())
                        .getModifier(NoGuiEvolution.uuid);
                if (countModifier2 != null) {
                    player.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()).removeModifier(countModifier2);
                }
            }

            if(count >= Config.jumpNumberLimit) count= Config.jumpNumberLimit;
            double jumpCoefficient = count*Config.jumpNumberCoefficient;
            double x = player.getDeltaMovement().get(Direction.Axis.X);
            double y = player.getDeltaMovement().get(Direction.Axis.Y);
            double z = player.getDeltaMovement().get(Direction.Axis.Z);
            boolean isSprinting = player.isSprinting();
                player.setDeltaMovement(x*(1+jumpCoefficient),y*(1+jumpCoefficient),z*(1+jumpCoefficient));
            player.setSprinting(isSprinting);
        }

    }

}

package com.strangesmell.noguievolution.event;

import com.strangesmell.noguievolution.Config;
import com.strangesmell.noguievolution.NoGuiEvolution;
import com.strangesmell.noguievolution.Util.Utils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MoveEvent {
    @SubscribeEvent
    public static void moveEvent(LivingEvent.LivingJumpEvent event){
        LivingEntity livingEntity =  event.getEntity();
        if(livingEntity == null) return;
        if(livingEntity instanceof Player player){
            double walkDistance=0;
            if(!event.getEntity().level().isClientSide){
                ServerPlayer serverPlayer =(ServerPlayer) event.getEntity();
                walkDistance = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.WALK_ONE_CM));

                //forget begin
                Long nowTime = player.level().getGameTime();
                Long lastTime = player.getPersistentData().getLong("moveLastTime");
                int x = (int) ((nowTime - lastTime)/Config.forgetTime);
                serverPlayer.getStats().setValue(player,Stats.CUSTOM.get(Stats.WALK_ONE_CM),(int)(walkDistance * Math.pow(Config.forgetCoefficient,x)));
                player.getPersistentData().putLong("moveLastTime",nowTime);
                walkDistance = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.WALK_ONE_CM));
                //forget end

                AttributeModifier countModifier2 = Objects.requireNonNull(serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()))
                        .getModifier(NoGuiEvolution.uuid);
                if (countModifier2 != null) {
                    Objects.requireNonNull(serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()))
                            .removeModifier(countModifier2);
                }
                AttributeModifier countModifier = new AttributeModifier(NoGuiEvolution.uuid," count ", walkDistance, AttributeModifier.Operation.ADDITION);
                Objects.requireNonNull(serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get())).addPermanentModifier(countModifier);
            }else {
                walkDistance =(int) player.getAttributeValue(NoGuiEvolution.COUNT_ATTRIBUTE.get());
            }
            boolean isSprinting = player.isSprinting();
            Utils.modifier(player,walkDistance, Config.moveNumberCoefficient,Attributes.MOVEMENT_SPEED,Config.moveNumberLimit);
            player.setSprinting(isSprinting);
        }

    }

}

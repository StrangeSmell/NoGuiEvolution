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

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MoveEvent {
    @SubscribeEvent
    public static void moveEvent(LivingEvent.LivingJumpEvent event){
        LivingEntity livingEntity =  event.getEntity();
        if(livingEntity == null) return;
        if(livingEntity instanceof Player){
            Player player = (Player) livingEntity;
            double walkDistance=0;
            if(!event.getEntity().level().isClientSide){
                ServerPlayer serverPlayer =(ServerPlayer) event.getEntity();
                walkDistance = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.WALK_ONE_CM));
                AttributeModifier minedCountModifier = new AttributeModifier(" walkDistance ", walkDistance, AttributeModifier.Operation.ADDITION);
                serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()).removeModifiers();
                serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()).addPermanentModifier(minedCountModifier);

            }

            if(event.getEntity().level().isClientSide){
                LocalPlayer localPlayer =(LocalPlayer) event.getEntity();
                walkDistance =(int) localPlayer.getAttributeValue(NoGuiEvolution.COUNT_ATTRIBUTE.get());
            }
            boolean isSprinting = player.isSprinting();
            Utils.modifier(player,walkDistance, Config.moveNumberCoefficient,Attributes.MOVEMENT_SPEED,Config.moveNumberLimit);
            player.setSprinting(isSprinting);
        }

    }

}

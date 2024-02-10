package com.strangesmell.noguievolution.event;

import com.strangesmell.noguievolution.Config;
import com.strangesmell.noguievolution.NoGuiEvolution;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)

public class SwimmingEvent {
    @SubscribeEvent
    public static void swimmingEvent(LivingEvent.LivingJumpEvent event){
        LivingEntity livingEntity = event.getEntity();
        if(livingEntity instanceof Player){
            Player player = (Player) livingEntity;
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

            double addCount = count*Config.swimNumberCoefficient;


            AttributeModifier countModifier = new AttributeModifier(" countModifier ", addCount, AttributeModifier.Operation.ADDITION);
            player.getAttribute(ForgeMod.SWIM_SPEED.get()).removeModifiers();
            player.getAttribute(ForgeMod.SWIM_SPEED.get()).addPermanentModifier(countModifier);

        }
    }
}

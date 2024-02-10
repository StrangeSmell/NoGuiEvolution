package com.strangesmell.noguievolution.notcurrentlyused;

import com.strangesmell.noguievolution.NoGuiEvolution;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
//Not currently used
public class UpDataByJumpEvent {
    //@SubscribeEvent
    public static void updataByLivingJumpEventEvent(LivingEvent.LivingJumpEvent event){

    }

    //@SubscribeEvent
    public static void updataByRightClickBlockEvent(PlayerInteractEvent.RightClickBlock event){
        int count=0;
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
            localPlayer.getStats().setValue(event.getEntity(), Stats.CUSTOM.get(Stats.SWIM_ONE_CM),count);
        }
    }
}

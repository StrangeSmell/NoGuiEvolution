package com.strangesmell.noguievolution.event;


import com.strangesmell.noguievolution.Config;
import com.strangesmell.noguievolution.NoGuiEvolution;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)

public class JumpEvent {
    @SubscribeEvent
    public static void JumpEvent(LivingEvent.LivingJumpEvent event){
        LivingEntity livingEntity = event.getEntity();
        if(livingEntity instanceof Player ){
            int jumpCount=0;
            if(!event.getEntity().level().isClientSide){
                ServerPlayer serverPlayer =(ServerPlayer) event.getEntity();
                jumpCount = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.JUMP));
                AttributeModifier minedCountModifier = new AttributeModifier(" jumpCount ", jumpCount, AttributeModifier.Operation.ADDITION);
                serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()).removeModifiers();
                serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()).addPermanentModifier(minedCountModifier);
            }else{
                jumpCount =(int) livingEntity.getAttributeValue(NoGuiEvolution.COUNT_ATTRIBUTE.get());
            }

            Player player = (Player) event.getEntity();

            if(jumpCount >= Config.jumpNumberLimit) jumpCount= Config.jumpNumberLimit;
            double jumpCoefficient = jumpCount*Config.jumpNumberCoefficient;
            double x = player.getDeltaMovement().get(Direction.Axis.X);
            double y = player.getDeltaMovement().get(Direction.Axis.Y);
            double z = player.getDeltaMovement().get(Direction.Axis.Z);
            boolean isSprinting = player.isSprinting();
            player.setDeltaMovement(x*(1+jumpCoefficient),y*(1+jumpCoefficient),z*(1+jumpCoefficient));
            player.setSprinting(isSprinting);
        }

    }

}

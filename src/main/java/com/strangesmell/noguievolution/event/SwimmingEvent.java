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

import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)

public class SwimmingEvent {
    @SubscribeEvent
    public static void swimmingEvent(LivingEvent.LivingJumpEvent event){
        LivingEntity livingEntity = event.getEntityLiving();
        if(livingEntity instanceof Player player){
            int count = 0;
            if(!event.getEntity().level.isClientSide){
                ServerPlayer serverPlayer =(ServerPlayer) event.getEntity();
                count = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.SWIM_ONE_CM));

                //forget begin
                long nowTime = player.level.getGameTime();
                long lastTime = player.getPersistentData().getLong("swimLastTime");
                int x = (int) ((nowTime - lastTime)/Config.forgetTime);
                serverPlayer.getStats().setValue(player,Stats.CUSTOM.get(Stats.SWIM_ONE_CM),(int)(count * Math.pow(Config.forgetCoefficient,x)));
                player.getPersistentData().putLong("swimLastTime",nowTime);
                count = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.SWIM_ONE_CM));
                //forget end
                AttributeModifier countModifier2 = Objects.requireNonNull(serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()))
                        .getModifier(NoGuiEvolution.uuid);
                if (countModifier2 != null) {
                    Objects.requireNonNull(serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()))
                            .removeModifier(countModifier2);
                }
                AttributeModifier countModifier = new AttributeModifier(NoGuiEvolution.uuid," count ", count, AttributeModifier.Operation.ADDITION);
                Objects.requireNonNull(serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get())).addPermanentModifier(countModifier);
            }else {
                LocalPlayer localPlayer =(LocalPlayer) event.getEntity();
                count =(int) localPlayer.getAttributeValue(NoGuiEvolution.COUNT_ATTRIBUTE.get());
            }

            if(count>=Config.swimNumberLimit) count = Config.swimNumberLimit;

            double addCount = count*Config.swimNumberCoefficient;


            AttributeModifier countModifier = new AttributeModifier(" countModifier ", addCount, AttributeModifier.Operation.ADDITION);
            Objects.requireNonNull(player.getAttribute(ForgeMod.SWIM_SPEED.get())).removeModifiers();
            Objects.requireNonNull(player.getAttribute(ForgeMod.SWIM_SPEED.get())).addPermanentModifier(countModifier);

        }
    }
}

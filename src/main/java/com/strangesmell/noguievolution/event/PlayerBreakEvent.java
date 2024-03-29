package com.strangesmell.noguievolution.event;

import com.strangesmell.noguievolution.Config;
import com.strangesmell.noguievolution.NoGuiEvolution;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerBreakEvent {
    @SubscribeEvent
    public static void playerBreakEvent(PlayerEvent.BreakSpeed event) {
        int count=0;
        if(!event.getEntity().level().isClientSide){
            ServerPlayer serverPlayer =(ServerPlayer) event.getEntity();
            count = serverPlayer.getStats().getValue(Stats.BLOCK_MINED,event.getState().getBlock());

            //forget begin
            Player player = event.getEntity();
            Long nowTime = player.level().getGameTime();
            Long lastTime = player.getPersistentData().getLong("breakLastTime");
            int x = (int) ((nowTime - lastTime)/Config.forgetTime);
            serverPlayer.getStats().setValue(player,Stats.BLOCK_MINED.get(event.getState().getBlock()),(int)(count * Math.pow(Config.forgetCoefficient,x)));
            player.getPersistentData().putLong("breakLastTime",nowTime);
            count = serverPlayer.getStats().getValue(Stats.BLOCK_MINED.get(event.getState().getBlock()));
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
            count =(int) event.getEntity().getAttributeValue(NoGuiEvolution.COUNT_ATTRIBUTE.get());
        }

        float speed = event.getOriginalSpeed();
        count = Math.min(count,Config.minedNumberLimit);
        event.setNewSpeed((float) (speed*(1+count*Config.minedNumberCoefficient)));
    }

}

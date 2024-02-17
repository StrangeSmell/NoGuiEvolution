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

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerBreakEvent {
    @SubscribeEvent
    public static void playerBreakEvent(PlayerEvent.BreakSpeed event) {

        int minedCount=0;


        if(!event.getEntity().level().isClientSide){
            ServerPlayer serverPlayer =(ServerPlayer) event.getEntity();
            minedCount = serverPlayer.getStats().getValue(Stats.BLOCK_MINED,event.getState().getBlock());

            //forget begin
            Player player = event.getEntity();
            Long nowTime = player.level().getGameTime();
            Long lastTime = player.getPersistentData().getLong("breakLastTime");
            int x = (int) ((nowTime - lastTime)/Config.forgetTime);
            serverPlayer.getStats().setValue(player,Stats.BLOCK_MINED.get(event.getState().getBlock()),(int)(minedCount * Math.pow(Config.forgetCoefficient,x)));
            player.getPersistentData().putLong("breakLastTime",nowTime);
            minedCount = serverPlayer.getStats().getValue(Stats.BLOCK_MINED.get(event.getState().getBlock()));
            //forget end

            AttributeModifier minedCountModifier = new AttributeModifier(" minedCount ", minedCount, AttributeModifier.Operation.ADDITION);
            serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()).removeModifiers();
            serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()).addPermanentModifier(minedCountModifier);
        }else {
            minedCount =(int) event.getEntity().getAttributeValue(NoGuiEvolution.COUNT_ATTRIBUTE.get());
        }

        float speed = event.getOriginalSpeed();
        minedCount = Math.min(minedCount,Config.minedNumberLimit);
        event.setNewSpeed((float) (speed*(1+minedCount*Config.minedNumberCoefficient)));
    }

}

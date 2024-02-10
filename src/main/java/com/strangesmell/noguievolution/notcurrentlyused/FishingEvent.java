package com.strangesmell.noguievolution.notcurrentlyused;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
//Not currently used
public class FishingEvent {
    public static void fishing(ItemFishedEvent event ){
        Player player = event.getEntity();
        if (player == null || !player.isAlive())
            return;
        Level level = player.level();
    }

}

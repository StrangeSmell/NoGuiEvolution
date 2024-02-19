package com.strangesmell.noguievolution.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;

import com.strangesmell.noguievolution.NoGuiEvolution;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerConstructEvent {

    @SubscribeEvent
    public void playerConstructEvent(EntityEvent.EntityConstructing event) {
        Entity player = event.entity;
        if (player instanceof EntityPlayer) {
            ((EntityPlayer) player).getAttributeMap()
                .registerAttribute(NoGuiEvolution.COUNT_ATTRIBUTE);
        }
    }
}

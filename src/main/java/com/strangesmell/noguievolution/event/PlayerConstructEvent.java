package com.strangesmell.noguievolution.event;

import com.strangesmell.noguievolution.NoGuiEvolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid =NoGuiEvolution.MODID)
public class PlayerConstructEvent {
    @SubscribeEvent
    public static void playerConstructEvent(EntityEvent.EntityConstructing event){
        Entity player = event.getEntity();
        if(player instanceof EntityPlayer){
            ((EntityPlayer) player).getAttributeMap().registerAttribute(NoGuiEvolution.COUNT_ATTRIBUTE);
        }
    }
}

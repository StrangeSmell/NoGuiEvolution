package com.strangesmell.noguievolution.event;

import com.strangesmell.noguievolution.NoGuiEvolution;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AddAttributeEvent {
    @SubscribeEvent
    public static void entityInity(EntityAttributeModificationEvent event){
        event.add(EntityType.PLAYER,NoGuiEvolution.COUNT_ATTRIBUTE.get(),0);
    }

}

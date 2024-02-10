package com.strangesmell.noguievolution.notcurrentlyused;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)

public class LeveLoadingEvent {
    @SubscribeEvent
    public static void  leveLoadingEvent(LevelEvent.Load event){

    }
}

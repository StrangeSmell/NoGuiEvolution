package com.strangesmell.noguievolution.event;

import com.strangesmell.noguievolution.Config;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class PreInitializationEvent {

    public void preInit(FMLPreInitializationEvent event)
    {
        new Config(event);

    }
}

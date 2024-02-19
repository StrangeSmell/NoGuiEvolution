package com.strangesmell.noguievolution;

import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.common.MinecraftForge;

import com.strangesmell.noguievolution.event.AttackEvent;
import com.strangesmell.noguievolution.event.ClimbEvent;
import com.strangesmell.noguievolution.event.DamageAbsorbEvent;
import com.strangesmell.noguievolution.event.JumpEvent;
import com.strangesmell.noguievolution.event.MoveEvent;
import com.strangesmell.noguievolution.event.PlayerBreakEvent;
import com.strangesmell.noguievolution.event.PlayerConstructEvent;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = NoGuiEvolution.MODID, name = NoGuiEvolution.NAME, version = NoGuiEvolution.VERSION)
public class NoGuiEvolution {

    public static final String MODID = "noguievolution";
    public static final String NAME = "NGE";
    public static final String VERSION = "1.1.0";

    public static final RangedAttribute COUNT_ATTRIBUTE = new RangedAttribute(
        "count_attribute",
        0,
        0,
        Double.MAX_VALUE);

    public NoGuiEvolution() {
        MinecraftForge.EVENT_BUS.register(new AttackEvent());
        MinecraftForge.EVENT_BUS.register(new ClimbEvent());
        MinecraftForge.EVENT_BUS.register(new DamageAbsorbEvent());
        MinecraftForge.EVENT_BUS.register(new JumpEvent());
        MinecraftForge.EVENT_BUS.register(new MoveEvent());
        MinecraftForge.EVENT_BUS.register(new PlayerBreakEvent());
        MinecraftForge.EVENT_BUS.register(new PlayerConstructEvent());

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        new Config(event);
    }
}

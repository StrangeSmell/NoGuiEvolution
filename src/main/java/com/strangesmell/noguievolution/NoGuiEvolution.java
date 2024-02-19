package com.strangesmell.noguievolution;

import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.UUID;

@Mod(modid = NoGuiEvolution.MODID, name = NoGuiEvolution.NAME, version = NoGuiEvolution.VERSION)
public class NoGuiEvolution
{
    public static final String MODID = "noguievolution";
    public static final String NAME = "NGE";
    public static final String VERSION = "1.1.0";

    static String name = "noguievolution";
    UUID namespaceUUID = new UUID(0L, 0L); // 使用默认的名称空间 UUID
    static byte[] nameBytes = name.getBytes();
    public static final UUID uuid = UUID.nameUUIDFromBytes(nameBytes);

    static String namemove = "noguievolutionmove";
    UUID namespacemoveUUID = new UUID(0L, 0L); // 使用默认的名称空间 UUID
    static byte[] namemoveBytes = namemove.getBytes();
    public static final UUID uuidmove = UUID.nameUUIDFromBytes(namemoveBytes);

    public static final RangedAttribute COUNT_ATTRIBUTE  = new RangedAttribute(null  ,"count_attribute",0,0,Double.MAX_VALUE);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        new Config(event);
    }
}

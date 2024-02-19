package com.strangesmell.noguievolution;


import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.UUID;

@Mod(NoGuiEvolution.MODID)
public class NoGuiEvolution
{
    public static final String MODID = "noguievolution";
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MODID);
    public static RegistryObject<Attribute> COUNT_ATTRIBUTE = ATTRIBUTES.register("count_attribute",
            ()-> new RangedAttribute("count_attribute",0,0,Integer.MAX_VALUE).setSyncable(true));

    UUID nameSpaceUUID = new UUID(0L, 0L); // 使用默认的名称空间 UUID
    static byte[] nameBytes = MODID.getBytes();
    public static final UUID uuid = UUID.nameUUIDFromBytes(nameBytes);

    static String nameMove = "noguievolutionmove";
    UUID nameSpaceMoveUUID = new UUID(0L, 0L); // 使用默认的名称空间 UUID
    static byte[] nameMoveBytes = nameMove.getBytes();
    public static final UUID uuidMove = UUID.nameUUIDFromBytes(nameMoveBytes);

    public NoGuiEvolution()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ATTRIBUTES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}

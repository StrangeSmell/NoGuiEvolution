package com.strangesmell.noguievolution;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.UUID;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
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

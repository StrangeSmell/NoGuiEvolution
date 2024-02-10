package com.strangesmell.noguievolution.network;

import com.strangesmell.noguievolution.NoGuiEvolution;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
//Not currently used

public class SimpleImpl {
    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }


}

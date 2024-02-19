package com.strangesmell.noguievolution;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;




@Mod.EventBusSubscriber(modid = NoGuiEvolution.MODID)
public class Config
{
    private static Configuration config;

    public static double killNumberLimitCoefficient;
    public static double killNumberAttackCoefficient;
    public static double killNumberCoefficient;
    public static int useNumberLimit;
    public static double useNumberCoefficient;
    public static int minedNumberLimit;
    public static double minedNumberCoefficient;
    public static int jumpNumberLimit;
    public static double jumpNumberCoefficient;
    public static int moveNumberLimit;
    public static double moveNumberCoefficient;
    public static double swimNumberCoefficient;
    public static int swimNumberLimit;
    public static double climbSpeedLimit;
    public static double climbNumberCoefficient;
    public static int climbNumberLimit;
    public static boolean isPercentage;
    public static double absorbNumberLimit;
    public static double absorbNumberCoefficient;
    public static double forgetCoefficient;
    public static int forgetTime;

    public Config(FMLPreInitializationEvent event)
    {

        config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();
        load();
    }

    public static void load()
    {
        killNumberLimitCoefficient = config.get(Configuration.CATEGORY_GENERAL, "killNumberLimitCoefficient",1000 , "").getDouble();
        killNumberAttackCoefficient = config.get(Configuration.CATEGORY_GENERAL, "killNumberAttackCoefficient", 0.001, "").getDouble();
        killNumberCoefficient = config.get(Configuration.CATEGORY_GENERAL, "killNumberCoefficient",0.01 , "").getDouble();
        useNumberLimit = config.get(Configuration.CATEGORY_GENERAL, "useNumberLimit",5000 , "").getInt();
        useNumberCoefficient = config.get(Configuration.CATEGORY_GENERAL, "useNumberCoefficient", 0.0001, "").getDouble();
        minedNumberLimit = config.get(Configuration.CATEGORY_GENERAL, "minedNumberLimit", 50000, "").getInt();
        minedNumberCoefficient = config.get(Configuration.CATEGORY_GENERAL, "minedNumberCoefficient",0.0001 , "").getDouble();
        jumpNumberLimit = config.get(Configuration.CATEGORY_GENERAL, "jumpNumberLimit", 100000, "").getInt();
        jumpNumberCoefficient = config.get(Configuration.CATEGORY_GENERAL, "jumpNumberCoefficient",0.0000414 , "").getDouble();
        moveNumberLimit = config.get(Configuration.CATEGORY_GENERAL, "moveNumberLimit", 10000000, "").getInt();
        moveNumberCoefficient = config.get(Configuration.CATEGORY_GENERAL, "moveNumberCoefficient",0.0000001 , "").getDouble();
        swimNumberCoefficient = config.get(Configuration.CATEGORY_GENERAL, "swimNumberCoefficient", 0.000001, "").getDouble();
        swimNumberLimit = config.get(Configuration.CATEGORY_GENERAL, "swimNumberLimit",2000000 , "").getInt();
        climbSpeedLimit = config.get(Configuration.CATEGORY_GENERAL, "climbSpeedLimit", 0.2, "").getDouble();
        climbNumberCoefficient = config.get(Configuration.CATEGORY_GENERAL, "climbNumberCoefficient", 0.0000001, "").getDouble();
        climbNumberLimit = config.get(Configuration.CATEGORY_GENERAL, "climbNumberLimit",1000000 , "").getInt();
        isPercentage = config.get(Configuration.CATEGORY_GENERAL, "isPercentage",true , "").getBoolean();
        absorbNumberLimit = config.get(Configuration.CATEGORY_GENERAL, "absorbNumberLimit",10000 , "").getDouble();
        absorbNumberCoefficient = config.get(Configuration.CATEGORY_GENERAL, "absorbNumberCoefficient", 1, "").getDouble();
        forgetCoefficient = config.get(Configuration.CATEGORY_GENERAL, "forgetCoefficient", 0.95, "").getDouble();
        forgetTime = config.get(Configuration.CATEGORY_GENERAL, "forgetTime", 1728000, "").getInt();
        config.save();
    }
}

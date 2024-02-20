package com.strangesmell.noguievolution;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;


@Mod.EventBusSubscriber(modid = NoGuiEvolution.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.DoubleValue KILL_NUMBER_LIMIT_COEFFICIENT = BUILDER
            .comment("杀敌最大有效数计算：killNumberLimitCoefficient*maxHealth*10")
            .comment("伤害计算公式：基础数值+killCount*killNumberCoefficient*maxHealth*10*killNumberAttackCoefficient+toolUseCount*useNumberCoefficient")
            .comment("挖掘速度计算公式：基础速度*(1+minedCount*minedNumberCoefficient)")
            .comment("跳跃速度计算公式：x，y，z轴的初速度为分别为 各轴基础速度*(1+jumpCount*jumpNumberCoefficient)")
            .comment("移动速度计算公式：基础数值+moveCount*moveNumberCoefficient")
            .comment("游泳速度计算公式：基础数值+swimCount*swimNumberCoefficient")
            .comment("攀爬速度计算公式：y轴的初速度为为 基础速度*(1+climbNumber*climbNumberCoefficient)")
            .comment("敲钟音量计算公式：基础数值+count*volumeCoefficient")
            .comment("敲钟音调计算公式：基础数值+count*pitchCoefficient")
            .comment("kill number limit")
            .defineInRange("killNumberLimitCoefficient", 1.0, 0, Double.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue KILL_NUMBER_ATTACK_COEFFICIENT = BUILDER
            .comment("kill number attack coefficient")
            .defineInRange("killNumberAttackCoefficient", 1000, 0, Double.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue KILL_NUMBER_COEFFICIENT = BUILDER
            .comment("Kill number coefficient")
            .defineInRange("killNumberCoefficient", 0.01, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue USE_NUMBER_LIMIT = BUILDER
            .comment("use number limit")
            .defineInRange("useNumberLimit", 5000, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue USE_NUMBER_COEFFICIENT = BUILDER
            .comment("use number coefficient")
            .defineInRange("useNumberCoefficient", 0.0001, 0, Double.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue MINED_NUMBER_LIMIT = BUILDER
            .comment("mined number limit")
            .defineInRange("minedNumberLimit", 50000, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue MINED_NUMBER_COEFFICIENT = BUILDER
            .comment("mined number coefficient")
            .defineInRange("minedNumberCoefficient", 0.0001, 0, Double.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue JUMP_NUMBER_LIMIT = BUILDER
            .comment("jump number limit")
            .defineInRange("jumpNumberLimit", 100000, 1, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue JUMP_NUMBER_COEFFICIENT = BUILDER
            .comment("jump number coefficient")
            .defineInRange("jumpNumberCoefficient", 0.0000414, 0, Double.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue MOVE_NUMBER_LIMIT = BUILDER
            .comment("move distance limit")
            .defineInRange("moveNumberLimit", 10000000, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue MOVE_NUMBER_COEFFICIENT = BUILDER
            .comment("move distance coefficient")
            .defineInRange("moveNumberCoefficient", 0.0000001, 0, Double.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue SWIM_NUMBER_LIMIT = BUILDER
            .comment("swim distance limit")
            .defineInRange("swimNumberLimit", 2000000, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue SWIM_NUMBER_COEFFICIENT = BUILDER
            .comment("swim number coefficient")
            .defineInRange("swimNumberCoefficient", 0.000001, 0, Double.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue CLIMB_SPEED_LIMIT = BUILDER
            .comment("climb speed limit")
            .defineInRange("climbSpeedLimit", 0.2, 0, Double.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue CLIMB_NUMBER_LIMIT = BUILDER
            .comment("climb distance limit")
            .defineInRange("climbNumberLimit", 1000000, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue CLIMB_NUMBER_COEFFICIENT = BUILDER
            .comment("climb number coefficient")
            .defineInRange("climbNumberCoefficient", 0.0000001, 0, Double.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue BELL_NUMBER_LIMIT = BUILDER
            .comment("ring number limit")
            .defineInRange("ringNumberLimit", 10000, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue VOLUME_COEFFICIENT = BUILDER
            .comment("volume coefficient")
            .defineInRange("volumeCoefficient", 0.001, 0, Double.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue PITCH_COEFFICIENT = BUILDER
            .comment("pitch coefficient")
            .defineInRange("pitchCoefficient", 0.001, 0, Double.MAX_VALUE);

    private static final ForgeConfigSpec.BooleanValue IS_PERCENTAGE = BUILDER
            .comment("Percentage damage reduction")
            .define("isPercentage", true);

    private static final ForgeConfigSpec.DoubleValue ABSORB_NUMBER_LIMIT = BUILDER
            .comment("absorb number limit")
            .defineInRange("absorbNumberLimit", 10000, 0, Double.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue ABSORB_NUMBER_COEFFICIENT = BUILDER
            .comment("absorb number coefficient")
            .defineInRange("absorbNumberCoefficient", 1, 0, Double.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue FORGET_COEFFICIENT = BUILDER
            .comment("forget coefficient")
            .defineInRange("forgetCoefficient", 0.95, 0, Double.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue FORGET_TIME = BUILDER
            .comment("forget time")
            .defineInRange("forgetTime", 1728000, 1, Integer.MAX_VALUE);

    static final ForgeConfigSpec SPEC = BUILDER.build();

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
    public static int ringNumberLimit;
    public static double volumeCoefficient;
    public static double pitchCoefficient;
    public static boolean isPercentage;
    public static double absorbNumberLimit;
    public static double absorbNumberCoefficient;
    public static double forgetCoefficient;
    public static int forgetTime;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        killNumberLimitCoefficient = KILL_NUMBER_LIMIT_COEFFICIENT.get();
        killNumberAttackCoefficient = KILL_NUMBER_ATTACK_COEFFICIENT.get();
        killNumberCoefficient = KILL_NUMBER_COEFFICIENT.get();
        useNumberLimit = USE_NUMBER_LIMIT.get();
        useNumberCoefficient = USE_NUMBER_COEFFICIENT.get();
        minedNumberLimit = MINED_NUMBER_LIMIT.get();
        minedNumberCoefficient = MINED_NUMBER_COEFFICIENT.get();
        jumpNumberLimit = JUMP_NUMBER_LIMIT.get();
        jumpNumberCoefficient = JUMP_NUMBER_COEFFICIENT.get();
        moveNumberLimit = MOVE_NUMBER_LIMIT.get();
        moveNumberCoefficient = MOVE_NUMBER_COEFFICIENT.get();
        swimNumberLimit = SWIM_NUMBER_LIMIT.get();
        swimNumberCoefficient = SWIM_NUMBER_COEFFICIENT.get();
        climbSpeedLimit = CLIMB_SPEED_LIMIT.get();
        climbNumberLimit = CLIMB_NUMBER_LIMIT.get();
        climbNumberCoefficient = CLIMB_NUMBER_COEFFICIENT.get();
        ringNumberLimit = BELL_NUMBER_LIMIT.get();
        volumeCoefficient = VOLUME_COEFFICIENT.get();
        pitchCoefficient = PITCH_COEFFICIENT.get();
        isPercentage = IS_PERCENTAGE.get();
        absorbNumberLimit = ABSORB_NUMBER_LIMIT.get();
        absorbNumberCoefficient = ABSORB_NUMBER_COEFFICIENT.get();
        forgetCoefficient = FORGET_COEFFICIENT.get();
        forgetTime = FORGET_TIME.get();

    }
}

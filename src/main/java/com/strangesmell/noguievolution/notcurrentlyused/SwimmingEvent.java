package com.strangesmell.noguievolution.notcurrentlyused;

import com.strangesmell.noguievolution.Config;
import com.strangesmell.noguievolution.NoGuiEvolution;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = NoGuiEvolution.MODID,value = Side.CLIENT)

public class SwimmingEvent {
    //@SubscribeEvent
    public static void swimmingEvent(LivingEvent.LivingJumpEvent event){
        EntityLivingBase entityLivingBase = event.getEntityLiving();
        if(entityLivingBase instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) entityLivingBase;
            int count = 0;
            if(!event.getEntity().world.isRemote){
                EntityPlayerMP serverPlayer =(EntityPlayerMP) event.getEntity();
                count = serverPlayer.getStatFile().readStat(StatList.WALK_ONE_CM);
                AttributeModifier countModifier = new AttributeModifier(" count ", count,2);
                Objects.requireNonNull(serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)).removeAllModifiers();
                Objects.requireNonNull(serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)).applyModifier(countModifier);
            }else {
                EntityPlayerSP localPlayer =(EntityPlayerSP) event.getEntity();
                count =(int) localPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE).getAttributeValue();
            }

            if(count>=Config.swimNumberLimit) count = Config.swimNumberLimit;

            double addCount = count*Config.swimNumberCoefficient;


            AttributeModifier countModifier = new AttributeModifier(" countModifier ", addCount,2);
            //Objects.requireNonNull(player.getEntityAttribute(ForgeMod.SWIM_SPEED.get())).removeModifiers();
            //Objects.requireNonNull(player.getEntityAttribute(ForgeMod.SWIM_SPEED.get())).addPermanentModifier(countModifier);

        }
    }
}

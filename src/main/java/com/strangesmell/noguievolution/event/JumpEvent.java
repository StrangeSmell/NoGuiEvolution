package com.strangesmell.noguievolution.event;


import com.strangesmell.noguievolution.Config;
import com.strangesmell.noguievolution.NoGuiEvolution;
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
public class JumpEvent {
    @SubscribeEvent
    public static void JumpEvent(LivingEvent.LivingJumpEvent event){
        EntityLivingBase entityLivingBase = event.getEntityLiving();
        if(entityLivingBase instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) entityLivingBase;
            int jumpCount=0;
            if(!event.getEntity().world.isRemote){
                EntityPlayerMP serverPlayer =(EntityPlayerMP) event.getEntity();
                jumpCount = serverPlayer.getStatFile().readStat(StatList.JUMP);

                // forget begin
                long nowTime = player.world.getTotalWorldTime();
                long lastTime = player.getEntityData()
                        .getLong("jumpLastTime");
                int x = (int) ((nowTime - lastTime) / Config.forgetTime);
                if (x > 0) serverPlayer.getStatFile()
                        .increaseStat(
                                player,
                                StatList.JUMP,
                                (int) (jumpCount * Math.pow(Config.forgetCoefficient, x))-jumpCount);
                player.getEntityData()
                        .setLong("jumpLastTime", nowTime);
                jumpCount = serverPlayer.getStatFile()
                        .readStat(StatList.JUMP);
                // forget end
                AttributeModifier countModifier2 = serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)
                        .getModifier(NoGuiEvolution.uuid);
                if (countModifier2 != null) {
                        serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)
                            .removeModifier(countModifier2);
                }
                AttributeModifier countModifier = new AttributeModifier(NoGuiEvolution.uuid," count ", jumpCount,2);
                    serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE).applyModifier(countModifier);
            }else{
                jumpCount =(int) entityLivingBase.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE).getAttributeValue();
            }

            if(jumpCount >= Config.jumpNumberLimit) jumpCount= Config.jumpNumberLimit;
            double jumpCoefficient = jumpCount*Config.jumpNumberCoefficient;
            double x = player.motionX;
            double y = player.motionY;
            double z = player.motionZ;
            boolean isSprinting = player.isSprinting();
            player.addVelocity(x*jumpCoefficient,y*jumpCoefficient,z*jumpCoefficient);
            player.setSprinting(isSprinting);
        }

    }

}

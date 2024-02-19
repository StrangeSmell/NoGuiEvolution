package com.strangesmell.noguievolution.event;

import java.util.Objects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;
import net.minecraftforge.event.entity.living.LivingEvent;

import com.strangesmell.noguievolution.Config;
import com.strangesmell.noguievolution.NoGuiEvolution;
import com.strangesmell.noguievolution.Util.Utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class JumpEvent {

    @SubscribeEvent
    public void JumpEvent(LivingEvent.LivingJumpEvent event) {
        EntityLivingBase entityLivingBase = event.entityLiving;
        if (entityLivingBase instanceof EntityPlayer player) {
            int jumpCount;
            if (!((EntityPlayer) entityLivingBase).getEntityWorld().isRemote) {
                EntityPlayerMP serverPlayer = (EntityPlayerMP) entityLivingBase;
                jumpCount = serverPlayer.func_147099_x()
                    .writeStat(StatList.jumpStat);

                // forget begin
                long nowTime = player.worldObj.getTotalWorldTime();
                long lastTime = player.getEntityData()
                    .getLong("jumpLastTime");
                int x = (int) ((nowTime - lastTime) / Config.forgetTime);
                if (x > 0) serverPlayer.func_147099_x()
                    .func_150873_a(
                        player,
                        StatList.jumpStat,
                        (int) (jumpCount * Math.pow(Config.forgetCoefficient, x)));
                player.getEntityData()
                    .setLong("jumpLastTime", nowTime);
                jumpCount = serverPlayer.func_147099_x()
                    .writeStat(StatList.jumpStat);
                // forget end

                AttributeModifier countModifier2 = serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)
                    .getModifier(Utils.uuid);
                if (countModifier2 != null) {
                    Objects.requireNonNull(serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE))
                        .removeModifier(countModifier2);
                }
                AttributeModifier countModifier = new AttributeModifier(Utils.uuid, " count ", jumpCount, 2);

                Objects.requireNonNull(serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE))
                    .applyModifier(countModifier);
            } else {
                jumpCount = (int) entityLivingBase.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)
                    .getAttributeValue();
            }

            if (jumpCount >= Config.jumpNumberLimit) jumpCount = Config.jumpNumberLimit;
            double jumpCoefficient = jumpCount * Config.jumpNumberCoefficient;
            double x = player.motionX;
            double y = player.motionY;
            double z = player.motionZ;
            boolean isSprinting = player.isSprinting();
            player.addVelocity(x * jumpCoefficient, y * jumpCoefficient, z * jumpCoefficient);
            player.setSprinting(isSprinting);
        }

    }

}

package com.strangesmell.noguievolution.event;

import java.util.Objects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;
import net.minecraftforge.event.entity.living.LivingEvent;

import com.strangesmell.noguievolution.Config;
import com.strangesmell.noguievolution.NoGuiEvolution;
import com.strangesmell.noguievolution.Util.Utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MoveEvent {

    @SubscribeEvent
    public void moveEvent(LivingEvent.LivingJumpEvent event) {
        EntityLivingBase entityLivingBase = event.entityLiving;
        if (entityLivingBase == null) return;
        if (entityLivingBase instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLivingBase;
            double walkDistance;
            if (!((EntityPlayer) entityLivingBase).getEntityWorld().isRemote) {
                EntityPlayerMP serverPlayer = (EntityPlayerMP) event.entityLiving;
                walkDistance = serverPlayer.func_147099_x()
                    .writeStat(StatList.distanceWalkedStat);

                // forget begin
                long nowTime = player.worldObj.getTotalWorldTime();
                long lastTime = player.getEntityData()
                    .getLong("moveLastTime");
                int x = (int) ((nowTime - lastTime) / Config.forgetTime);
                if (x > 0) serverPlayer.func_147099_x()
                    .func_150873_a(
                        player,
                        StatList.distanceWalkedStat,
                        (int) (walkDistance * Math.pow(Config.forgetCoefficient, x)));
                player.getEntityData()
                    .setLong("moveLastTime", nowTime);
                walkDistance = serverPlayer.func_147099_x()
                    .writeStat(StatList.distanceWalkedStat);
                // forget end

                AttributeModifier countModifier2 = serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)
                    .getModifier(Utils.uuid);
                if (countModifier2 != null) {
                    Objects.requireNonNull(serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE))
                        .removeModifier(countModifier2);
                }
                AttributeModifier countModifier = new AttributeModifier(" count ", walkDistance, 2);
                Objects.requireNonNull(serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE))
                    .applyModifier(countModifier);
            } else {
                walkDistance = (int) entityLivingBase.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)
                    .getAttributeValue();
            }
            boolean isSprinting = player.isSprinting();

            Utils.modifier(
                player,
                walkDistance,
                Config.moveNumberCoefficient,
                SharedMonsterAttributes.movementSpeed,
                Config.moveNumberLimit);
            player.setSprinting(isSprinting);
        }

    }

}

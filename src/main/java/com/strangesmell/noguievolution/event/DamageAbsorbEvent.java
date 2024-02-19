package com.strangesmell.noguievolution.event;

import java.util.Objects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.strangesmell.noguievolution.Config;
import com.strangesmell.noguievolution.NoGuiEvolution;
import com.strangesmell.noguievolution.Util.Utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

// @Mod.EventBusSubscriber(modid = NoGuiEvolution.MODID)
public class DamageAbsorbEvent {

    @SubscribeEvent
    public void damageAbsorbEvent(LivingHurtEvent event) {
        EntityLivingBase entityLivingBase = event.entityLiving;
        if (entityLivingBase instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLivingBase;
            double count;
            if (!player.getEntityWorld().isRemote) {
                EntityPlayerMP serverPlayer = (EntityPlayerMP) player;
                count = serverPlayer.func_147099_x()
                    .writeStat(StatList.damageTakenStat);

                // forget begin
                long nowTime = player.worldObj.getTotalWorldTime();
                long lastTime = player.getEntityData()
                    .getLong("damageAbsorbLastTime");
                int x = (int) ((nowTime - lastTime) / Config.forgetTime);
                if (x > 0) serverPlayer.func_147099_x()
                    .func_150873_a(
                        player,
                        StatList.damageTakenStat,
                        (int) (count * Math.pow(Config.forgetCoefficient, x)));
                player.getEntityData()
                    .setLong("damageAbsorbLastTime", nowTime);
                count = serverPlayer.func_147099_x()
                    .writeStat(StatList.damageTakenStat);
                // forget end

                AttributeModifier countModifier2 = serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)
                    .getModifier(Utils.uuid);
                if (countModifier2 != null) {
                    Objects.requireNonNull(serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE))
                        .removeModifier(countModifier2);
                }
                AttributeModifier countModifier = new AttributeModifier(Utils.uuid, " count ", count, 2);
                Objects.requireNonNull(serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE))
                    .applyModifier(countModifier);
            } else {
                count = (int) player.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)
                    .getAttributeValue();
            }
            double damage = event.ammount;
            count = Math.min(count, Config.absorbNumberLimit);
            if (Config.isPercentage) {
                event.ammount = (float) (damage
                    * ((Config.absorbNumberLimit - count * Config.absorbNumberCoefficient) / Config.absorbNumberLimit));
            } else {
                float newDamge = (float) (damage - count * Config.absorbNumberCoefficient);
                if (newDamge <= 0) newDamge = 0;
                event.ammount = newDamge;
            }

        }

    }
}

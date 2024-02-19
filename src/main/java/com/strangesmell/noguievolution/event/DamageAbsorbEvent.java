package com.strangesmell.noguievolution.event;

import com.strangesmell.noguievolution.Config;
import com.strangesmell.noguievolution.NoGuiEvolution;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = NoGuiEvolution.MODID)
public class DamageAbsorbEvent {
    @SubscribeEvent
    public static void damageAbsorbEvent(LivingHurtEvent event){
        EntityLivingBase entityLivingBase = event.getEntityLiving();
        if(entityLivingBase instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) entityLivingBase;
            double count;
            if(!player.world.isRemote){
                EntityPlayerMP serverPlayer = (EntityPlayerMP) player;
                count = serverPlayer.getStatFile().readStat(StatList.DAMAGE_TAKEN);
                // forget begin
                long nowTime = player.world.getTotalWorldTime();
                long lastTime = player.getEntityData()
                        .getLong("damageAbsorbLastTime");
                int x = (int) ((nowTime - lastTime) / Config.forgetTime);
                if (x > 0) serverPlayer.getStatFile()
                        .increaseStat(
                                player,
                                StatList.DAMAGE_TAKEN,
                                (int) (count * Math.pow(Config.forgetCoefficient, x))-(int)count);
                player.getEntityData()
                        .setLong("damageAbsorbLastTime", nowTime);
                count = serverPlayer.getStatFile()
                        .readStat(StatList.DAMAGE_TAKEN);
                // forget end
                AttributeModifier countModifier2 = serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)
                        .getModifier(NoGuiEvolution.uuid);
                if (countModifier2 != null) {
                     serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)
                            .removeModifier(countModifier2);
                }
                AttributeModifier countModifier = new AttributeModifier(NoGuiEvolution.uuid," count ", count,2);
                 serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE).applyModifier(countModifier);
            }else{
                count =(int) player.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE).getAttributeValue();
            }
            double damage = event.getAmount();
            count = Math.min(count, Config.absorbNumberLimit);
            if(Config.isPercentage){
                event.setAmount((float) (damage*((Config.absorbNumberLimit-count*Config.absorbNumberCoefficient)/Config.absorbNumberLimit)));
            }else {
                float newDamge =(float) (damage - count * Config.absorbNumberCoefficient);
                if(newDamge<=0) newDamge =0;
                event.setAmount(newDamge);
            }


        }

    }
}

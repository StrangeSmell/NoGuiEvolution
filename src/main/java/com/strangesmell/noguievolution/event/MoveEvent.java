package com.strangesmell.noguievolution.event;

import com.strangesmell.noguievolution.Config;
import com.strangesmell.noguievolution.NoGuiEvolution;
import com.strangesmell.noguievolution.Util.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = NoGuiEvolution.MODID)

public class MoveEvent {
    @SubscribeEvent
    public static void moveEvent(LivingEvent.LivingJumpEvent event){
        EntityLivingBase entityLivingBase =  event.getEntityLiving();
        if(entityLivingBase == null) return;
        if(entityLivingBase instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) entityLivingBase;
            int walkDistance;
            if(!event.getEntity().world.isRemote){
                EntityPlayerMP serverPlayer =(EntityPlayerMP) event.getEntity();
                walkDistance = serverPlayer.getStatFile().readStat(StatList.WALK_ONE_CM);

                // forget begin
                long nowTime = player.world.getTotalWorldTime();
                long lastTime = player.getEntityData()
                        .getLong("moveLastTime");
                int x = (int) ((nowTime - lastTime) / Config.forgetTime);
                if (x > 0) serverPlayer.getStatFile()
                        .increaseStat(
                                player,
                                StatList.WALK_ONE_CM,
                                (int) (walkDistance * Math.pow(Config.forgetCoefficient, x))-walkDistance);
                player.getEntityData()
                        .setLong("moveLastTime", nowTime);
                walkDistance = serverPlayer.getStatFile()
                        .readStat(StatList.WALK_ONE_CM);
                // forget end
                AttributeModifier countModifier2 = serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)
                        .getModifier(NoGuiEvolution.uuid);
                if (countModifier2 != null) {
                     serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)
                            .removeModifier(countModifier2);
                }
                AttributeModifier countModifier = new AttributeModifier(NoGuiEvolution.uuid," count ", walkDistance,2);
                 serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE).applyModifier(countModifier);
            }else{
                walkDistance =(int) entityLivingBase.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE).getAttributeValue();
            }
            boolean isSprinting = player.isSprinting();
            Utils.modifier(player,walkDistance, Config.moveNumberCoefficient, SharedMonsterAttributes.MOVEMENT_SPEED,Config.moveNumberLimit);
            player.setSprinting(isSprinting);
        }

    }

}

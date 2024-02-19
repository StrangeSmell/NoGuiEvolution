package com.strangesmell.noguievolution.event;

import java.util.Objects;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;
import net.minecraftforge.event.entity.player.PlayerEvent;

import com.strangesmell.noguievolution.Config;
import com.strangesmell.noguievolution.NoGuiEvolution;
import com.strangesmell.noguievolution.Util.Utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

// @Mod.EventBusSubscriber(modid = NoGuiEvolution.MODID)
public class ClimbEvent {

    @SubscribeEvent

    public void climbEvent(PlayerEvent event) {
        EntityPlayer player = event.entityPlayer;
        if (player == null) return;

        boolean canLadder;
        canLadder = player.isOnLadder();
        if (canLadder) {
            int count;
            if (!player.getEntityWorld().isRemote) {
                EntityPlayerMP serverPlayer = (EntityPlayerMP) player;
                count = serverPlayer.func_147099_x()
                    .writeStat(StatList.distanceClimbedStat);

                // forget begin
                long nowTime = player.worldObj.getTotalWorldTime();
                long lastTime = player.getEntityData()
                    .getLong("climbLastTime");
                int x = (int) ((nowTime - lastTime) / Config.forgetTime);
                if (x > 0) serverPlayer.func_147099_x()
                    .func_150873_a(
                        player,
                        StatList.distanceClimbedStat,
                        (int) (count * Math.pow(Config.forgetCoefficient, x)));
                player.getEntityData()
                    .setLong("climbLastTime", nowTime);
                count = serverPlayer.func_147099_x()
                    .writeStat(StatList.distanceClimbedStat);
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

            if (count >= Config.climbNumberLimit) count = Config.climbNumberLimit;

            double time = count * Config.climbNumberCoefficient;

            double y = player.motionY;

            float i = 1;
            if (y < 0) i = -1;

            y = i * 0.2 * time;

            y = Math.min(y, Config.climbSpeedLimit);
            if (y < -Config.climbSpeedLimit) y = -Config.climbSpeedLimit;

            player.addVelocity(0, y, 0);

            y = player.motionY;
            if (player.isSneaking()) player.addVelocity(0, -y, 0);
        }
    }

}

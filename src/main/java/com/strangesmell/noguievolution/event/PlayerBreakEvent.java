package com.strangesmell.noguievolution.event;

import java.util.Objects;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;
import net.minecraftforge.event.entity.player.PlayerEvent;

import com.strangesmell.noguievolution.Config;
import com.strangesmell.noguievolution.NoGuiEvolution;
import com.strangesmell.noguievolution.Util.Utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerBreakEvent {

    @SubscribeEvent
    public void playerBreakEvent(PlayerEvent.BreakSpeed event) {

        int minedCount;

        if (!event.entityLiving.worldObj.isRemote) {
            EntityPlayerMP serverPlayer = (EntityPlayerMP) event.entityPlayer;
            try {
                minedCount = serverPlayer.func_147099_x()
                    .writeStat(StatList.mineBlockStatArray[Block.getIdFromBlock(event.block)]);
            } catch (NullPointerException e) {
                minedCount = 0;
            }

            // forget begin
            EntityPlayer player = event.entityPlayer;
            long nowTime = player.worldObj.getTotalWorldTime();
            long lastTime = player.getEntityData()
                .getLong("breakLastTime");
            int x = (int) ((nowTime - lastTime) / Config.forgetTime);
            if (x > 0) serverPlayer.func_147099_x()
                .func_150873_a(
                    player,
                    StatList.mineBlockStatArray[Block.getIdFromBlock(event.block)],
                    (int) (minedCount * Math.pow(Config.forgetCoefficient, x)));
            player.getEntityData()
                .setLong("breakLastTime", nowTime);
            try {
                minedCount = serverPlayer.func_147099_x()
                    .writeStat(StatList.mineBlockStatArray[Block.getIdFromBlock(event.block)]);
            } catch (NullPointerException e) {
                minedCount = 0;
            }
            // forget end

            AttributeModifier countModifier2 = serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)
                .getModifier(Utils.uuid);
            if (countModifier2 != null) {
                Objects.requireNonNull(serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE))
                    .removeModifier(countModifier2);
            }
            AttributeModifier countModifier = new AttributeModifier(" count ", minedCount, 2);
            Objects.requireNonNull(serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE))
                .applyModifier(countModifier);
        } else {
            minedCount = (int) event.entityPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)
                .getAttributeValue();
        }

        float speed = event.originalSpeed;
        minedCount = Math.min(minedCount, Config.minedNumberLimit);
        event.newSpeed = ((float) (speed * (1 + minedCount * Config.minedNumberCoefficient)));
    }
}

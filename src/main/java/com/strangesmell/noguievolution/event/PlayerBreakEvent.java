package com.strangesmell.noguievolution.event;

import com.strangesmell.noguievolution.Config;
import com.strangesmell.noguievolution.NoGuiEvolution;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = NoGuiEvolution.MODID,value = Side.CLIENT)

public class PlayerBreakEvent {
    @SubscribeEvent
    public static void playerBreakEvent(PlayerEvent.BreakSpeed event) {

        int minedCount;

        if(!event.getEntity().world.isRemote){
            EntityPlayerMP serverPlayer =(EntityPlayerMP) event.getEntity();
            minedCount = serverPlayer.getStatFile().readStat(StatList.MINE_BLOCK_STATS.get(Block.getIdFromBlock(event.getState().getBlock())));

            // forget begin
            EntityPlayer player = event.getEntityPlayer();
            long nowTime = player.world.getTotalWorldTime();
            long lastTime = player.getEntityData()
                    .getLong("breakLastTime");
            int x = (int) ((nowTime - lastTime) / Config.forgetTime);
            if (x > 0) serverPlayer.getStatFile()
                    .increaseStat(
                            player,
                            StatList.MINE_BLOCK_STATS.get(Block.getIdFromBlock(event.getState().getBlock())),
                            (int) (minedCount * Math.pow(Config.forgetCoefficient, x))-minedCount);
            player.getEntityData()
                    .setLong("breakLastTime", nowTime);
            minedCount = serverPlayer.getStatFile()
                    .readStat(StatList.MINE_BLOCK_STATS.get(Block.getIdFromBlock(event.getState().getBlock())));
            // forget end
            AttributeModifier countModifier2 = serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)
                    .getModifier(NoGuiEvolution.uuid);
            if (countModifier2 != null) {
                 serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)
                        .removeModifier(countModifier2);
            }
            AttributeModifier countModifier = new AttributeModifier(NoGuiEvolution.uuid," count ", minedCount,2);
             serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE).applyModifier(countModifier);
        }else{
            minedCount =(int)event.getEntityPlayer().getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE).getAttributeValue();
        }

        float speed = event.getOriginalSpeed();
        minedCount = Math.min(minedCount,Config.minedNumberLimit);
        event.setNewSpeed((float) (speed*(1+minedCount*Config.minedNumberCoefficient)));
    }
}

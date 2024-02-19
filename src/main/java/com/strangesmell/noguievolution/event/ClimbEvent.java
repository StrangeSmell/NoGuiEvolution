package com.strangesmell.noguievolution.event;

import com.strangesmell.noguievolution.Config;
import com.strangesmell.noguievolution.NoGuiEvolution;
import com.strangesmell.noguievolution.Util.Utils;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = NoGuiEvolution.MODID)
public class ClimbEvent {
    @SubscribeEvent

    public static void climbEvent(PlayerEvent event){
        EntityPlayer player = event.getEntityPlayer();
        if(player==null) return;

        boolean canLadder;
        canLadder = player.isOnLadder();
        if(canLadder){
            int count;
            if(!event.getEntity().world.isRemote){
                EntityPlayerMP serverPlayer =(EntityPlayerMP) event.getEntity();
                count = serverPlayer.getStatFile().readStat(StatList.CLIMB_ONE_CM);

                // forget begin
                long nowTime = player.world.getTotalWorldTime();
                long lastTime = player.getEntityData()
                        .getLong("climbLastTime");
                int x = (int) ((nowTime - lastTime) / Config.forgetTime);
                if(x>0) serverPlayer.getStatFile()
                        .increaseStat(
                                player,
                                StatList.CLIMB_ONE_CM,
                                (int) (count * Math.pow(Config.forgetCoefficient, x))-count);
                player.getEntityData()
                        .setLong("climbLastTime", nowTime);
                count = serverPlayer.getStatFile()
                        .readStat(StatList.CLIMB_ONE_CM);
                // forget end
                AttributeModifier countModifier2 = serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)
                        .getModifier(NoGuiEvolution.uuid);
                if (countModifier2 != null) {
                      serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE)
                            .removeModifier(countModifier2);
                }
                AttributeModifier countModifier = new AttributeModifier(NoGuiEvolution.uuid," count ", count,2);
                  serverPlayer.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE).applyModifier(countModifier);
            }else {
                count =(int) player.getEntityAttribute(NoGuiEvolution.COUNT_ATTRIBUTE).getAttributeValue();
            }

            if(count>= Config.climbNumberLimit) count = Config.climbNumberLimit;

            double time = count*Config.climbNumberCoefficient;

            double y = player.motionY;

            float i = 1;
            if(y<0) i=-1;

            y=i*0.2*time;

            y = Math.min(y, Config.climbSpeedLimit);
            if(y<-Config.climbSpeedLimit) y=-Config.climbSpeedLimit;

            player.addVelocity(0,y,0);

            y= player.motionY;
            if(player.isSneaking()) player.addVelocity(0,-y,0);
        }
    }

}

package com.strangesmell.noguievolution.event;

import com.strangesmell.noguievolution.Config;
import com.strangesmell.noguievolution.NoGuiEvolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = NoGuiEvolution.MODID)

public class AttackEvent {
    @SubscribeEvent
    public static void LivingAttack(LivingHurtEvent event){
        Entity player = event.getSource().getTrueSource();

        if(player instanceof EntityPlayerMP){
            int killCount=0;
            EntityPlayerMP serverPlayerEntity = (EntityPlayerMP) player;
            EntityLivingBase entityLivingBase = event.getEntityLiving();
            ItemStack itemStack = serverPlayerEntity.getHeldItemMainhand();
            Item item = itemStack.getItem();

            boolean isInTools = item.isRepairable();

            int toolUseCount = 0;
            try {
                toolUseCount = serverPlayerEntity.getStatFile()
                        .readStat(StatList.getObjectUseStats(item));
            } catch (NullPointerException e) {

            }
            if(!isInTools) toolUseCount = 0;

            try {
                killCount = serverPlayerEntity.getStatFile()
                        .readStat(EntityList.ENTITY_EGGS.get(EntityList.getKey(entityLivingBase)).killEntityStat);
            } catch (NullPointerException e) {
            }

            // forget begin
            Long AttackNowTime = player.world.getTotalWorldTime();
            Long AttackLastTime = player.getEntityData()
                    .getLong("killLastTime");
            if (isInTools) {
                Long UseLastTime = player.getEntityData().getLong("useLastTime" + item);
                int y = (int) ((AttackNowTime - UseLastTime) / Config.forgetTime);
                if (y > 0) serverPlayerEntity.getStatFile().increaseStat((EntityPlayer) player,
                           StatList.getObjectUseStats(item), (int) (toolUseCount * Math.pow(Config.forgetCoefficient, y))-toolUseCount);
                player.getEntityData().setLong("useLastTime" + item, player.world.getTotalWorldTime());
            }
            int x = (int) ((AttackNowTime - AttackLastTime) / Config.forgetTime);
            if (x > 0) serverPlayerEntity.getStatFile()
                    .increaseStat(
                            (EntityPlayer) player,
                            EntityList.ENTITY_EGGS.get(EntityList.getKey(entityLivingBase)).killEntityStat,
                            (int) (killCount * Math.pow(Config.forgetCoefficient, x))-killCount);
            player.getEntityData()
                    .setLong("killLastTime", player.world.getTotalWorldTime());
            killCount = serverPlayerEntity.getStatFile()
                    .readStat(EntityList.ENTITY_EGGS.get(EntityList.getKey(entityLivingBase)).killEntityStat);
            toolUseCount = serverPlayerEntity.getStatFile()
                    .readStat(StatList.getObjectUseStats(item));
            // forget end

            if(killCount >= Config.killNumberLimitCoefficient*entityLivingBase.getMaxHealth()*10 ) killCount = (int)(Config.killNumberLimitCoefficient*  entityLivingBase.getMaxHealth()*10);
            if(toolUseCount>= Config.useNumberLimit) toolUseCount = Config.useNumberLimit;
            event.setAmount( (float) (event.getAmount() + killCount * Config.killNumberCoefficient * entityLivingBase.getMaxHealth()*Config.killNumberAttackCoefficient + toolUseCount * Config.useNumberCoefficient ));
        }
    }
}

package com.strangesmell.noguievolution.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.strangesmell.noguievolution.Config;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AttackEvent {

    @SubscribeEvent
    public void LivingAttack(LivingHurtEvent event) {
        Entity player = event.source.getEntity();

        if (player instanceof EntityPlayerMP serverPlayerEntity) {
            EntityLivingBase entityLivingBase = event.entityLiving;
            ItemStack itemStack = serverPlayerEntity.getHeldItem();
            if (itemStack == null) return;
            Item item = itemStack.getItem();
            boolean isInTools = item.isRepairable();

            int toolUseCount = 0;
            int damageCount = 0;

            if (StatList.objectUseStats[Item.getIdFromItem(item)] != null) {
                toolUseCount = serverPlayerEntity.func_147099_x()
                    .writeStat(StatList.objectUseStats[Item.getIdFromItem(item)]);;
            }
            if (!isInTools) toolUseCount = 0;

            damageCount = serverPlayerEntity.func_147099_x()
                .writeStat(StatList.damageTakenStat);

            // forget begin
            Long AttackNowTime = player.worldObj.getTotalWorldTime();
            Long AttackLastTime = player.getEntityData()
                .getLong("damageLastTime");
            if (isInTools) {
                Long UseLastTime = player.getEntityData()
                    .getLong("useLastTime" + item);
                int y = (int) ((AttackNowTime - UseLastTime) / Config.forgetTime);
                if (y > 0) serverPlayerEntity.func_147099_x()
                    .func_150873_a(
                        (EntityPlayer) player,
                        StatList.objectUseStats[Item.getIdFromItem(item)],
                        (int) (toolUseCount * Math.pow(Config.forgetCoefficient, y)));
                player.getEntityData()
                    .setLong("useLastTime" + item, player.worldObj.getTotalWorldTime());
            }
            int x = (int) ((AttackNowTime - AttackLastTime) / Config.forgetTime);
            if (x > 0) serverPlayerEntity.func_147099_x()
                .func_150873_a(
                    (EntityPlayer) player,
                    StatList.damageTakenStat,
                    (int) (damageCount * Math.pow(Config.forgetCoefficient, x)));
            player.getEntityData()
                .setLong("damageLastTime", player.worldObj.getTotalWorldTime());
            try {
                damageCount = serverPlayerEntity.func_147099_x()
                    .writeStat(StatList.damageTakenStat);
            } catch (NullPointerException e) {
                damageCount = 0;
            }
            if (StatList.objectUseStats[Item.getIdFromItem(item)] != null) {
                toolUseCount = serverPlayerEntity.func_147099_x()
                    .writeStat(StatList.objectUseStats[Item.getIdFromItem(item)]);;
            }
            // forget end

            if (damageCount >= Config.damageNumberLimitCoefficient * entityLivingBase.getMaxHealth() * 10)
                damageCount = (int) (Config.damageNumberLimitCoefficient * entityLivingBase.getMaxHealth() * 10);
            if (toolUseCount >= Config.useNumberLimit) toolUseCount = Config.useNumberLimit;
            event.ammount = (event.ammount
                + damageCount * (float) Config.damageNumberCoefficient
                    * entityLivingBase.getMaxHealth()
                    * (float) Config.damageNumberAttackCoefficient
                + toolUseCount * (float) Config.useNumberCoefficient);
        }
    }
}

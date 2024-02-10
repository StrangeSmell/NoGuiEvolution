package com.strangesmell.noguievolution.event;

import com.strangesmell.noguievolution.Config;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AttackEvent {
    @SubscribeEvent
    public static void LivingAttack(LivingHurtEvent event){
        Entity player = event.getSource().getEntity();
        Entity entity = event.getEntity();
        if(player instanceof Player && entity instanceof LivingEntity){
            player = (Player) player;
            ServerPlayer serverPlayer = (ServerPlayer) player;
            entity = (LivingEntity) entity;

            ItemStack itemStack = ((ServerPlayer) player).getItemInHand(((ServerPlayer) player).getUsedItemHand());

            boolean isInTools =  itemStack.is(Tags.Items.TOOLS)||itemStack.is(Tags.Items.TOOLS_TRIDENTS)||itemStack.is(Tags.Items.TOOLS_SHIELDS)||itemStack.is(Tags.Items.TOOLS_BOWS)||itemStack.is(Tags.Items.TOOLS_CROSSBOWS);
            int toolUseCount = serverPlayer.getStats().getValue(Stats.ITEM_USED, itemStack.getItem());
            if(!isInTools) toolUseCount = 0;

            int killCount = serverPlayer.getStats().getValue(Stats.ENTITY_KILLED,entity.getType());

            if(killCount >= Config.killNumberLimitCoefficient*((LivingEntity) entity).getMaxHealth()*10 ) killCount = (int)(Config.killNumberLimitCoefficient* ((LivingEntity) entity).getMaxHealth()*10);
            if(toolUseCount>= Config.useNumberLimit) toolUseCount = Config.useNumberLimit;
            event.setAmount( event.getAmount() + killCount * Config.killNumberCoefficient * ((LivingEntity) entity).getMaxHealth()*(float)Config.killNumberAttackCoefficient + toolUseCount * (float)Config.useNumberCoefficient );
        }
    }
}
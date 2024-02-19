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

import net.minecraft.world.item.Item;
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

        if(player instanceof ServerPlayer serverPlayer){
            LivingEntity livingEntity = event.getEntity();
            ItemStack itemStack = serverPlayer.getItemInHand(((ServerPlayer) player).getUsedItemHand());
            Item item = itemStack.getItem();
            int killCount = serverPlayer.getStats().getValue(Stats.ENTITY_KILLED,livingEntity.getType());
            int toolUseCount = serverPlayer.getStats().getValue(Stats.ITEM_USED, item);
            boolean isInTools =  itemStack.is(Tags.Items.TOOLS)||itemStack.is(Tags.Items.TOOLS_TRIDENTS)||itemStack.is(Tags.Items.TOOLS_SHIELDS)||itemStack.is(Tags.Items.TOOLS_BOWS)||itemStack.is(Tags.Items.TOOLS_CROSSBOWS);

            //forget begin
            Long AttackNowTime = player.level().getGameTime();
            Long AttackLastTime = player.getPersistentData().getLong("killLastTime");
            if(isInTools){
                Long UseLastTime = player.getPersistentData().getLong("useLastTime"+item.toString());
                int y = (int) ((AttackNowTime - UseLastTime)/Config.forgetTime);
                if (y>0) serverPlayer.getStats().setValue((Player) player,Stats.ITEM_USED.get(item), (int) (toolUseCount*Math.pow(Config.forgetCoefficient,y))  );
                player.getPersistentData().putLong("useLastTime"+item.toString(),player.level().getGameTime());
            }
            int x = (int) ((AttackNowTime - AttackLastTime)/Config.forgetTime);
            if(x>0) serverPlayer.getStats().setValue((Player) player,Stats.ENTITY_KILLED.get(livingEntity.getType()),(int) (killCount*Math.pow(Config.forgetCoefficient,x)));
            player.getPersistentData().putLong("killLastTime"+livingEntity.toString(),player.level().getGameTime());
            killCount = serverPlayer.getStats().getValue(Stats.ENTITY_KILLED,livingEntity.getType());
            toolUseCount = serverPlayer.getStats().getValue(Stats.ITEM_USED, item);
            //forget end

            if(!isInTools) toolUseCount = 0;

            if(killCount >= Config.killNumberLimitCoefficient*livingEntity.getMaxHealth()*10 ) killCount = (int)(Config.killNumberLimitCoefficient* livingEntity.getMaxHealth()*10);
            if(toolUseCount>= Config.useNumberLimit) toolUseCount = Config.useNumberLimit;
            event.setAmount( event.getAmount() + killCount *(float) Config.killNumberCoefficient * livingEntity.getMaxHealth()*(float)Config.killNumberAttackCoefficient + toolUseCount * (float)Config.useNumberCoefficient );

        }
    }
}

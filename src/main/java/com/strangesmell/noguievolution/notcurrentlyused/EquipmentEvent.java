package com.strangesmell.noguievolution.notcurrentlyused;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
//Not currently used
public class EquipmentEvent {
/*

    @SubscribeEvent
    public static void useTool(PlayerInteractEvent.LeftClickBlock event){
        ItemStack itemStack = event.getItemStack();

        boolean isTool = itemStack.is(Tags.Items.TOOLS);//剑+四
        boolean isBow = itemStack.is(Tags.Items.TOOLS_BOWS);//弓
        boolean isCrossbow = itemStack.is(Tags.Items.TOOLS_CROSSBOWS);//弩
        boolean isShield = itemStack.is(Tags.Items.TOOLS_SHIELDS);//盾
        boolean isFishing= itemStack.is(Tags.Items.TOOLS_FISHING_RODS);//鱼竿
        boolean isTrident = itemStack.is(Tags.Items.TOOLS_TRIDENTS);//三叉戟
        boolean isArmor = itemStack.is(Tags.Items.ARMORS);//防具
        boolean isDamaged = itemStack.isDamaged();//损坏
        CompoundTag data = event.getEntity().getPersistentData();
        if(isTool) data.putInt("NGETools",data.getInt("NGETools")+1);
        else if (isCrossbow)data.putInt("NGECrossbow",data.getInt("NGETools")+1);
        else if (isBow)data.putInt("NGEBow",data.getInt("NGETools")+1);
        else if (isShield) data.putInt("NGEShield",data.getInt("NGETools")+1);
        else if(isFishing) data.putInt("NGEFishing",data.getInt("NGETools")+1);
        else if(isTrident) data.putInt("NGETrident",data.getInt("NGETools")+1);
        else return;



        }
*/
}

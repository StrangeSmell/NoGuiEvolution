package com.strangesmell.noguievolution.notcurrentlyused;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
//Not currently used
public class PlayerTags {
    /*
    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        CompoundTag data = player.getPersistentData();
        // 创建自定义标签并设置初始值
        data.put("NGETools", new CompoundTag());
        data.getCompound("NGETools").putInt("ToolUses", 0);
        data.put("NGEBow", new CompoundTag());
        data.getCompound("NGEBow").putInt("BowUses", 0);
        data.put("NGECrossbow", new CompoundTag());
        data.getCompound("NGECrossbow").putInt("CrossbowUses", 0);
        data.put("NGEShield", new CompoundTag());
        data.getCompound("NGEShield").putInt("ShieldUses", 0);
        data.put("NGEFishing", new CompoundTag());
        data.getCompound("NGEFishing").putInt("FishingUses", 0);
        data.put("NGETrident", new CompoundTag());
        data.getCompound("NGETrident").putInt("TridentUses", 0);
        data.put("NGEArmor", new CompoundTag());
        data.getCompound("NGEArmor").putInt("ArmorUses", 0);
    }
    */

}

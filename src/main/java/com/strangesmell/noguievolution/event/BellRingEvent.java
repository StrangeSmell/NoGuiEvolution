package com.strangesmell.noguievolution.event;

import com.strangesmell.noguievolution.Config;
import com.strangesmell.noguievolution.NoGuiEvolution;
import com.strangesmell.noguievolution.Util.Utils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BellBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.event.sound.PlaySoundSourceEvent;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)

public class BellRingEvent {
    @SubscribeEvent
    public static void soundEvent(PlayerInteractEvent.RightClickBlock event) {
        BlockEntity blockEntity = event.getLevel().getBlockEntity(event.getPos());
        if(blockEntity != null && blockEntity.getType().equals(BlockEntityType.BELL)){
            Player player = event.getEntity();
            int count=0;
            if(!player.level().isClientSide){
                ServerPlayer serverPlayer = (ServerPlayer) player;
                count = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.BELL_RING));
                AttributeModifier minedCountModifier = new AttributeModifier(" count ", count, AttributeModifier.Operation.ADDITION);
                serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()).removeModifiers();
                serverPlayer.getAttribute(NoGuiEvolution.COUNT_ATTRIBUTE.get()).addPermanentModifier(minedCountModifier);
            }
            if(player.level().isClientSide()){
                LocalPlayer localPlayer = (LocalPlayer) player;
                count =(int) localPlayer.getAttributeValue(NoGuiEvolution.COUNT_ATTRIBUTE.get());
            }
            Utils.limit(count,Config.ringNumberLimit);
            player.level().playSound(event.getEntity(),event.getPos(),SoundEvents.BELL_BLOCK,SoundSource.BLOCKS,
                    (float) (24+count* Config.volumeCoefficient),(float)(0.93+count*Config.pitchCoefficient));
            }
    }
}

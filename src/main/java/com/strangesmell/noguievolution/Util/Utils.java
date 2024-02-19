package com.strangesmell.noguievolution.Util;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

public class Utils {

    public static void modifier(Player player, double count, double coefficient, Attribute attribute,int limit){
        if(count>=limit) count=limit;
        AttributeModifier modifier = new AttributeModifier(" modifier ", coefficient*count, AttributeModifier.Operation.ADDITION);
        player.getAttribute(attribute).removeModifiers();
        player.getAttribute(attribute).addPermanentModifier(modifier);
    }


    public static void massage(Player player){


    }
    public static void limit(int count,int limit){
        if(count>=limit) count = limit;
    }
    public static void limit(double count,double limit){
        if(count>=limit) count = limit;
    }

}

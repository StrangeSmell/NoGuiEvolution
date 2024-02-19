package com.strangesmell.noguievolution.Util;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;

public class Utils {

    public static void modifier(EntityPlayer playerEntity, double count, double coefficient, IAttribute attribute, int limit){
        if(count>=limit) count=limit;
        AttributeModifier modifier = new AttributeModifier(" modifier ", coefficient*count, 2);
        playerEntity.getEntityAttribute(attribute).removeAllModifiers();
        playerEntity.getEntityAttribute(attribute).applyModifier(modifier);
    }

}



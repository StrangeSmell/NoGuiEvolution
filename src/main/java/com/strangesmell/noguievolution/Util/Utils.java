package com.strangesmell.noguievolution.Util;

import java.util.Objects;
import java.util.UUID;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;

public class Utils {

    public static void modifier(EntityPlayer playerEntity, double count, double coefficient, IAttribute attribute,
        int limit) {
        if (count >= limit) count = limit;
        AttributeModifier modifier2 = playerEntity.getEntityAttribute(attribute)
            .getModifier(moveuuid);
        if (modifier2 != null) {
            Objects.requireNonNull(playerEntity.getEntityAttribute(attribute))
                .removeModifier(modifier2);
        }
        AttributeModifier modifier = new AttributeModifier(moveuuid, " modifier ", coefficient * count, 2);
        playerEntity.getEntityAttribute(attribute)
            .applyModifier(modifier);
    }

    static String name = "noguievolution";
    UUID namespaceUUID = new UUID(0L, 0L);
    static byte[] nameBytes = name.getBytes();

    public static final UUID uuid = UUID.nameUUIDFromBytes(nameBytes);

    static String namemove = "noguievolutionmove";
    UUID namespacemoveUUID = new UUID(0L, 0L);
    static byte[] namemoveBytes = name.getBytes();

    public static final UUID moveuuid = UUID.nameUUIDFromBytes(namemoveBytes);

}

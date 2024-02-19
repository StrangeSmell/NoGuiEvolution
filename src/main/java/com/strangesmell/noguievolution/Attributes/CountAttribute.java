package com.strangesmell.noguievolution.Attributes;

import net.minecraft.world.entity.ai.attributes.Attribute;

public class CountAttribute extends Attribute {
    private boolean syncable = true;

    public CountAttribute(String pDescriptionId, double pDefaultValue) {
        super(pDescriptionId, pDefaultValue);
    }
    public Attribute setSyncable(boolean pWatch) {
        this.syncable = pWatch;
        return this;
    }
}

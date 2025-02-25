package com.kettle.nunchakus.render;

import net.minecraft.world.phys.Vec3;

public class LightningEffectData {
    private final Vec3 start;
    private final Vec3 end;
    private final float energyScale;
    private final long timestamp;
    
    public LightningEffectData(Vec3 start, Vec3 end, float energyScale, long timestamp) {
        this.start = start;
        this.end = end;
        this.energyScale = energyScale;
        this.timestamp = timestamp;
    }
    
    public Vec3 getStart() { return start; }
    public Vec3 getEnd() { return end; }
    public float getEnergyScale() { return energyScale; }
    public long getTimestamp() { return timestamp; }
}


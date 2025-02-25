package com.kettle.nunchakus;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ForgottenNunchakusMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NunchakusConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    // Resource location for the lightning damage type; default is "minecraft:lightning"
    public static final ForgeConfigSpec.ConfigValue<String> LIGHTNING_DAMAGE = BUILDER
            .comment("A resource location for the lightning damage type. Default: minecraft:lightning")
            .define("lightning_damage", "minecraft:lightning_bolt");

    // Resource location for the lightning mob effect; default is "minecraft:slowness"
    public static final ForgeConfigSpec.ConfigValue<String> LIGHTNING_MOBEFFECT = BUILDER
            .comment("A resource location for the lightning mob effect. Default: minecraft:slowness")
            .define("lightning_mobeffect", "minecraft:slowness");

    // Combo duration in ticks (0 to 255)
    public static final ForgeConfigSpec.IntValue COMBO_DURATION = BUILDER
            .comment("Combo duration in ticks (from 0 to 255)")
            .defineInRange("combo_duration", 30, 0, 255);

    // Build the final spec
    public static final ForgeConfigSpec SPEC = BUILDER.build();

    // Cached config values for easy access
    public static String lightningDamage;
    public static MobEffect lightningMobEffect;
    public static int comboDuration;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent.Loading event) {
        lightningDamage = LIGHTNING_DAMAGE.get();
        lightningMobEffect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(LIGHTNING_MOBEFFECT.get()));
        if (lightningMobEffect == null) {
        	lightningMobEffect = MobEffects.MOVEMENT_SLOWDOWN;
        }
        
        comboDuration = COMBO_DURATION.get();
    }
}

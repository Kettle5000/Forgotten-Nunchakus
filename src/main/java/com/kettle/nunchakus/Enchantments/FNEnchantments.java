package com.kettle.nunchakus.Enchantments;

import com.kettle.nunchakus.ForgottenNunchakusMod;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FNEnchantments {
	public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ForgottenNunchakusMod.MODID);
	public static final RegistryObject<Enchantment> COMBO = REGISTRY.register("combo", () -> new ComboEnchantment());
}


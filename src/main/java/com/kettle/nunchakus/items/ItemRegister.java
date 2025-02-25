package com.kettle.nunchakus.items;

import com.kettle.nunchakus.ForgottenNunchakusMod;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegister {
	// Create a Deferred Register for items using our mod ID
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			ForgottenNunchakusMod.MODID);

	// Register the Nunchakus item. Adjust the properties as needed.
	public static final RegistryObject<Item> WOOD_NUNCHAKUS = ITEMS.register("wood_nunchakus",
			() -> new BaseNunchakuItem(Tiers.WOOD,
					new Item.Properties().defaultDurability(Tiers.WOOD.getUses()).durability(Tiers.WOOD.getUses()),
					1.5f, -0.86f));
	public static final RegistryObject<Item> GOLD_NUNCHAKUS = ITEMS.register("gold_nunchakus",
			() -> new BaseNunchakuItem(Tiers.GOLD,
					new Item.Properties().defaultDurability(Tiers.GOLD.getUses()).durability(Tiers.GOLD.getUses()),
					1.5f, -0.86f));
	public static final RegistryObject<Item> STONE_NUNCHAKUS = ITEMS.register("stone_nunchakus",
			() -> new BaseNunchakuItem(Tiers.STONE,
					new Item.Properties().defaultDurability(Tiers.STONE.getUses()).durability(Tiers.STONE.getUses()),
					1.0f, -0.86f));
	public static final RegistryObject<Item> COPPER_NUNCHAKUS = ITEMS.register("copper_nunchakus",
			() -> new BaseNunchakuItem(CustomTiers.COPPER,
					new Item.Properties().defaultDurability(105).durability(105),
					1.25f, -0.86f));
	public static final RegistryObject<Item> IRON_NUNCHAKUS = ITEMS.register("iron_nunchakus",
			() -> new BaseNunchakuItem(Tiers.IRON,
					new Item.Properties().defaultDurability(Tiers.IRON.getUses()).durability(Tiers.IRON.getUses()),
					0.5f, -0.86f));
	public static final RegistryObject<Item> DIAMOND_NUNCHAKUS = ITEMS.register("diamond_nunchakus",
			() -> new BaseNunchakuItem(Tiers.DIAMOND,
					new Item.Properties().defaultDurability(Tiers.DIAMOND.getUses()).durability(Tiers.DIAMOND.getUses()),
					0.0f, -0.86f));
	public static final RegistryObject<Item> NETHERITE_NUNCHAKUS = ITEMS.register("netherite_nunchakus",
			() -> new BaseNunchakuItem(Tiers.NETHERITE,
					new Item.Properties().defaultDurability(Tiers.NETHERITE.getUses()).durability(Tiers.NETHERITE.getUses()),
					-0.5f, -0.86f));
	public static final RegistryObject<Item> SILVER_NUNCHAKUS = ITEMS.register("silver_nunchakus",
			() -> new SilverNunchakuItem(CustomTiers.SILVER,
					new Item.Properties().defaultDurability(CustomTiers.SILVER.getUses()).durability(CustomTiers.SILVER.getUses()),
					1.0f, -0.86f));
	public static final RegistryObject<Item> DESERTCHITIN_NUNCHAKUS = ITEMS.register("desertchitin_nunchakus",
			() -> new BaseNunchakuItem(CustomTiers.DESERT_CHITIN,
					new Item.Properties().defaultDurability(CustomTiers.DESERT_CHITIN.getUses()).durability(CustomTiers.DESERT_CHITIN.getUses()),
					1.0f, -0.86f));
	public static final RegistryObject<Item> JUNGLECHITIN_NUNCHAKUS = ITEMS.register("junglechitin_nunchakus",
			() -> new BaseNunchakuItem(CustomTiers.JUNGLE_CHITIN,
					new Item.Properties().defaultDurability(CustomTiers.JUNGLE_CHITIN.getUses()).durability(CustomTiers.JUNGLE_CHITIN.getUses()),
					1.0f, -0.86f));
	public static final RegistryObject<Item> DRAGONBONE_NUNCHAKUS = ITEMS.register("dragonbone_nunchakus",
			() -> new BaseNunchakuItem(CustomTiers.DRAGON_BONE,
					new Item.Properties().defaultDurability(CustomTiers.DRAGON_BONE.getUses()).durability(CustomTiers.DRAGON_BONE.getUses()),
					-0.5f, -0.86f));
	public static final RegistryObject<Item> FIREDRAGONBONE_NUNCHAKUS = ITEMS.register("firedragonbone_nunchakus",
			() -> new FireDragonBoneNunchaku(CustomTiers.FIRE_DRAGON_BONE,
					new Item.Properties().defaultDurability(CustomTiers.FIRE_DRAGON_BONE.getUses()).durability(CustomTiers.FIRE_DRAGON_BONE.getUses()),
					0.0f, -0.86f));
	public static final RegistryObject<Item> ICEDRAGONBONE_NUNCHAKUS = ITEMS.register("icedragonbone_nunchakus",
			() -> new IceDragonBoneNunchaku(CustomTiers.ICE_DRAGON_BONE,
					new Item.Properties().defaultDurability(CustomTiers.ICE_DRAGON_BONE.getUses()).durability(CustomTiers.ICE_DRAGON_BONE.getUses()),
					0.0f, -0.86f));
	public static final RegistryObject<Item> LIGHTNINGDRAGONBONE_NUNCHAKUS = ITEMS.register("lightningdragonbone_nunchakus",
			() -> new LightningDragonBoneNunchaku(CustomTiers.SHOCKED_DRAGON_BONE,
					new Item.Properties().defaultDurability(CustomTiers.SHOCKED_DRAGON_BONE.getUses()).durability(CustomTiers.SHOCKED_DRAGON_BONE.getUses()),
					0.0f, -0.86f));
}

package com.kettle.nunchakus.items;

import java.util.function.Supplier;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fml.ModList;

@SuppressWarnings("deprecation")
public enum CustomTiers implements Tier {
	COPPER(1, 105, 4.0F, 1.0F, 5, () -> {
		return Ingredient.of(Items.COPPER_INGOT);
	}), JUNGLE_CHITIN(2, 600, 4.0F, 0.0F, 5, () -> {
        if (ModList.get().isLoaded("iceandfire")) {
            // Get the item from the mod. Change "fire_dragon_bone" to the actual item ID.
            Item modItem = IafItemRegistry.MYRMEX_JUNGLE_CHITIN.get();
            if (modItem != null) {
                return Ingredient.of(modItem);
            }
        }
        return Ingredient.of(Items.IRON_INGOT);
	}), DESERT_CHITIN(2, 600, 4.0F, 0.0F, 5, () -> {
        if (ModList.get().isLoaded("iceandfire")) {
            // Get the item from the mod. Change "fire_dragon_bone" to the actual item ID.
            Item modItem = IafItemRegistry.MYRMEX_DESERT_CHITIN.get();
            if (modItem != null) {
                return Ingredient.of(modItem);
            }
        }
        return Ingredient.of(Items.IRON_INGOT);
	}), SILVER(1, 460, 4.0F, 1.0F, 18, () -> {
        // Use mod "iceandfire" if loaded, otherwise fallback to DIAMOND.
        if (ModList.get().isLoaded("iceandfire")) {
            // Get the item from the mod. Change "fire_dragon_bone" to the actual item ID.
            Item modItem = IafItemRegistry.SILVER_INGOT.get();
            if (modItem != null) {
                return Ingredient.of(modItem);
            }
        }
        return Ingredient.of(Items.IRON_INGOT);
	}), DRAGON_BONE(3, 1660, 6.0F, 4.0F, 15, () -> {
        if (ModList.get().isLoaded("iceandfire")) {
            // Get the item from the mod. Change "fire_dragon_bone" to the actual item ID.
            Item modItem = IafItemRegistry.DRAGON_BONE.get();
            if (modItem != null) {
                return Ingredient.of(modItem);
            }
        }
        return Ingredient.of(Items.DIAMOND);
	}), FIRE_DRAGON_BONE(4, 2000, 8.0F, 4.25F, 15, () -> {
        if (ModList.get().isLoaded("iceandfire")) {
            // Get the item from the mod. Change "fire_dragon_bone" to the actual item ID.
            Item modItem = IafItemRegistry.DRAGON_BONE.get();
            if (modItem != null) {
                return Ingredient.of(modItem);
            }
        }
        return Ingredient.of(Items.DIAMOND);
	}), ICE_DRAGON_BONE(4, 2000, 12.0F, 4.25F, 15, () -> {
        if (ModList.get().isLoaded("iceandfire")) {
            // Get the item from the mod. Change "fire_dragon_bone" to the actual item ID.
            Item modItem = IafItemRegistry.DRAGON_BONE.get();
            if (modItem != null) {
                return Ingredient.of(modItem);
            }
        }
        return Ingredient.of(Items.DIAMOND);
	}), SHOCKED_DRAGON_BONE(4, 2000, 12.0F, 4.25F, 15, () -> {
        if (ModList.get().isLoaded("iceandfire")) {
            // Get the item from the mod. Change "fire_dragon_bone" to the actual item ID.
            Item modItem = IafItemRegistry.DRAGON_BONE.get();
            if (modItem != null) {
                return Ingredient.of(modItem);
            }
        }
        return Ingredient.of(Items.DIAMOND);
	});

	private final int level;
	private final int uses;
	private final float speed;
	private final float damage;
	private final int enchantmentValue;
	private final LazyLoadedValue<Ingredient> repairIngredient;

	private CustomTiers(int p_43332_, int p_43333_, float p_43334_, float p_43335_, int p_43336_,
			Supplier<Ingredient> p_43337_) {
		this.level = p_43332_;
		this.uses = p_43333_;
		this.speed = p_43334_;
		this.damage = p_43335_;
		this.enchantmentValue = p_43336_;
		this.repairIngredient = new LazyLoadedValue<>(p_43337_);
	}

	public int getUses() {
		return this.uses;
	}

	public float getSpeed() {
		return this.speed;
	}

	public float getAttackDamageBonus() {
		return this.damage;
	}

	public int getLevel() {
		return this.level;
	}

	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	public Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}
}

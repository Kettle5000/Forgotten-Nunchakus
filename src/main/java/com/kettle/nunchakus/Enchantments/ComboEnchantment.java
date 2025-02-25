package com.kettle.nunchakus.Enchantments;

import com.kettle.nunchakus.items.BaseNunchakuItem;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ComboEnchantment extends Enchantment {

	public ComboEnchantment(EquipmentSlot... slots) {
		super(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, slots);
	}

	@Override
    public boolean canEnchant(ItemStack p_44642_) {
        return (p_44642_.getItem() instanceof BaseNunchakuItem );
    }
	
	@Override
	public int getMaxLevel() {
		return (int)3;
	}
}

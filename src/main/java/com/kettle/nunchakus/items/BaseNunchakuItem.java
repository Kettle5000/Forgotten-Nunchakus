package com.kettle.nunchakus.items;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.kettle.nunchakus.LeftClickStateManager;
import com.kettle.nunchakus.events.ClientSideEvents;
import com.kettle.nunchakus.events.CommonSidedEvents;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.SweepingEdgeEnchantment;
import net.minecraft.world.level.Level;
import com.kettle.nunchakus.NunchakusConfig;
import com.kettle.nunchakus.Enchantments.FNEnchantments;

public class BaseNunchakuItem extends TieredItem implements Vanishable{
	private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    // The desired damage and speed values.
    private final float attackDamage;

    public BaseNunchakuItem(Tier tier, Properties properties, float attackDamage, float attackSpeed) {
        super(tier, properties);
        this.attackDamage = (float)attackDamage + tier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)attackSpeed, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }
	
	
	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
		if (entity instanceof Player player) {
			float strserver = player.getAttackStrengthScale(0.5f);
			if (selected && level.isClientSide() && LeftClickStateManager.isLeftClickHeld(player)) {
				if (strserver >= 1.0f) {
					ClientSideEvents.onNunchakuUse(player, stack);
				}
			}
		}
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
		player.getMainHandItem().hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
		return false;
	}
	
	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entityLiving)
	{
		return true;
	}
	
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity source) {
		if (source instanceof Player player) {
			float bonus = 0.05f * stack.getEnchantmentLevel(FNEnchantments.COMBO.get());
			CommonSidedEvents.ComboValue.put(player.getUUID(), CommonSidedEvents.ComboValue.getOrDefault(player.getUUID(), 0.1f) + 0.1f + bonus);
			CommonSidedEvents.ComboTimer.put(player.getUUID(), (int) NunchakusConfig.comboDuration);
		}
		return false;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		if (!(enchantment instanceof SweepingEdgeEnchantment) && (enchantment.category == EnchantmentCategory.WEAPON)) {
			return true;
		} else {
			return super.canApplyAtEnchantingTable(stack, enchantment);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot p_43274_) {
	      return p_43274_ == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(p_43274_);
	   }
	
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
	    super.appendHoverText(stack, world, tooltip, flag);
	    tooltip.add(Component.translatable("tooltip.fn.nunchaku.basic_tooltip")
	                     .withStyle(ChatFormatting.AQUA));
	}
}

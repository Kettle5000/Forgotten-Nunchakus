package com.kettle.nunchakus.items;

import java.util.List;

import javax.annotation.Nullable;

import com.kettle.nunchakus.events.CommonSidedEvents;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class SilverNunchakuItem extends BaseNunchakuItem {

	public SilverNunchakuItem(Tier tier, Properties properties, float attackDamage, float attackSpeed) {
		super(tier, properties, attackDamage, attackSpeed);
	}
	
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity source) {
		if (source instanceof Player player) {
			CommonSidedEvents.ComboValue.put(player.getUUID(), CommonSidedEvents.ComboValue.getOrDefault(player.getUUID(), 0.1f) + 0.1f);
			CommonSidedEvents.ComboTimer.put(player.getUUID(), (int)30);
			if (target instanceof Mob mob && mob.getMobType() == MobType.UNDEAD) {
				DamageSource damageSource = player.level().damageSources().indirectMagic(player, player);
				target.hurtTime = 0;
				target.invulnerableTime = 0;
				target.hurt(damageSource, 2);
			}
		}
		return false;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
	    super.appendHoverText(stack, world, tooltip, flag);
	    tooltip.add(Component.translatable("tooltip.fn.silver_nunchaku.undead_damage")
	                     .withStyle(ChatFormatting.GREEN));
	}

}

package com.kettle.nunchakus.items;

import java.util.List;

import javax.annotation.Nullable;

import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import com.kettle.nunchakus.events.CommonSidedEvents;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;

public class FireDragonBoneNunchaku extends BaseNunchakuItem {

	public FireDragonBoneNunchaku(Tier tier, Properties properties, float attackDamage, float attackSpeed) {
		super(tier, properties, attackDamage, attackSpeed);
	}
	
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity source) {
		if (source instanceof Player player) {
			CommonSidedEvents.ComboValue.put(player.getUUID(), CommonSidedEvents.ComboValue.getOrDefault(player.getUUID(), 0.1f) + 0.1f);
			CommonSidedEvents.ComboTimer.put(player.getUUID(), (int)30);
			if (ModList.get().isLoaded("iceandfire")) {
				if (target instanceof EntityIceDragon) {
					DamageSource damageSource = player.level().damageSources().onFire();
					target.hurtTime = 0;
					target.invulnerableTime = 0;
					target.hurt(damageSource, 8);
				}
			}
			target.setSecondsOnFire(15);
			target.knockback(1F, source.getX() - target.getX(), source.getZ() - target.getZ());
		}
		return false;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
	    tooltip.add(Component.translatable("tooltip.fn.firedragonbone_nunchaku.damage_bonus")
	                     .withStyle(ChatFormatting.GREEN));
	    super.appendHoverText(stack, world, tooltip, flag);
	}

}

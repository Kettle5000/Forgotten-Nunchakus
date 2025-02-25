package com.kettle.nunchakus.items;

import java.util.List;

import javax.annotation.Nullable;

import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.kettle.nunchakus.events.CommonSidedEvents;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import com.kettle.nunchakus.NunchakusConfig;
import com.kettle.nunchakus.Enchantments.FNEnchantments;

public class IceDragonBoneNunchaku extends BaseNunchakuItem {

	public IceDragonBoneNunchaku(Tier tier, Properties properties, float attackDamage, float attackSpeed) {
		super(tier, properties, attackDamage, attackSpeed);
	}
	
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity source) {
		boolean modloaded = ModList.get().isLoaded("iceandfire");
		if (source instanceof Player player) {
			float bonus = 0.05f * stack.getEnchantmentLevel(FNEnchantments.COMBO.get());
			CommonSidedEvents.ComboValue.put(player.getUUID(), CommonSidedEvents.ComboValue.getOrDefault(player.getUUID(), 0.1f) + 0.1f + bonus);
			CommonSidedEvents.ComboTimer.put(player.getUUID(), (int) NunchakusConfig.comboDuration);
			if (modloaded) {
				EntityDataProvider.getCapability(target).ifPresent(data -> data.frozenData.setFrozen(target, 300));
				if (target instanceof EntityFireDragon) {
					DamageSource damageSource = player.level().damageSources().drown();
					target.hurtTime = 0;
					target.invulnerableTime = 0;
					target.hurt(damageSource, 8);
				}
			}
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 300, 2, false, false));
			target.knockback(1F, source.getX() - target.getX(), source.getZ() - target.getZ());
		}
		return false;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
	    tooltip.add(Component.translatable("tooltip.fn.icedragonbone_nunchaku.damage_bonus")
	                     .withStyle(ChatFormatting.GREEN));
	    super.appendHoverText(stack, world, tooltip, flag);
	}
}

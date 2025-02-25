package com.kettle.nunchakus.items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import com.kettle.nunchakus.events.CommonSidedEvents;
import com.kettle.nunchakus.render.LightningEffectData;
import com.kettle.nunchakus.render.LightningEffectManager;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import com.kettle.nunchakus.NunchakusConfig;
import com.kettle.nunchakus.Enchantments.FNEnchantments;

public class LightningDragonBoneNunchaku extends BaseNunchakuItem {

	public LightningDragonBoneNunchaku(Tier tier, Properties properties, float attackDamage, float attackSpeed) {
		super(tier, properties, attackDamage, attackSpeed);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity source) {
		boolean modloaded = ModList.get().isLoaded("iceandfire");
		if (source instanceof Player player) {
			float bonus = 0.05f * stack.getEnchantmentLevel(FNEnchantments.COMBO.get());
			CommonSidedEvents.ComboValue.put(player.getUUID(), CommonSidedEvents.ComboValue.getOrDefault(player.getUUID(), 0.1f) + 0.1f + bonus);
			CommonSidedEvents.ComboTimer.put(player.getUUID(), (int) NunchakusConfig.comboDuration);
			// Since i dont know how to apply InF lightning effects i will just apply the
			// default minecraft one
			LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(player.level());
			if (lightningBolt != null) {
				lightningBolt.moveTo(target.getX(), target.getY(), target.getZ());
				lightningBolt.setVisualOnly(true); // This makes it not cause damage/fire.
				player.level().addFreshEntity(lightningBolt);
			}
			applyChainLightning(target, player, modloaded);
			target.knockback(1F, source.getX() - target.getX(), source.getZ() - target.getZ());
		}
		return false;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
	    tooltip.add(Component.translatable("tooltip.fn.lightningdragonbone_nunchaku.damage_bonus")
	                     .withStyle(ChatFormatting.GREEN));
	    super.appendHoverText(stack, world, tooltip, flag);
	}
	
	public void applyChainLightning(LivingEntity target, Player player, boolean modloaded) {
	    // Maximum number of additional targets in the chain (main target + 4 additional = 5 total)
	    final int maxChainCount = 4;
	    // Maximum chain range (in blocks)
	    final double chainRange = 5.0D;
	    List<LivingEntity> chainTargets = new ArrayList<>();
	    chainTargets.add(target);
	    LivingEntity current = target;
	    for (int i = 0; i < maxChainCount; i++) {
	        // Define a search area centered at the current target
	        AABB searchBox = new AABB(
	            current.getX() - chainRange, current.getY() - chainRange, current.getZ() - chainRange,
	            current.getX() + chainRange, current.getY() + chainRange, current.getZ() + chainRange
	        );
	        List<LivingEntity> candidates = player.level().getEntitiesOfClass(LivingEntity.class,
	                searchBox,
	                e -> e != player && e.isAlive() && !chainTargets.contains(e));
	        if (candidates.isEmpty()) {
	            break;
	        }
	        LivingEntity nextTarget = candidates.get(player.getRandom().nextInt(candidates.size()));
	        chainTargets.add(nextTarget);
	        current = nextTarget;
	    }
	    float bonus = 0;
	    DamageSource lightningDamage = player.level().damageSources().lightningBolt();
		int[] hpLosses = {5, 4, 3, 2, 1 };
		int[] slowDurations = {10, 8, 6, 4, 2 };
		// Loop over each extra target and apply the damage and effect
		LivingEntity last = target;
		for (int i = 0; i < chainTargets.size(); i++) {
			LivingEntity extra = chainTargets.get(i);
			extra.hurtTime = 0;
			extra.invulnerableTime = 0;
			if (extra instanceof EntityFireDragon || extra instanceof EntityIceDragon) {
				bonus = 4;
			} else {
				bonus = 0;
			}
			extra.hurt(lightningDamage, hpLosses[i] + bonus);
			extra.addEffect(
					new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, slowDurations[i], 4, false, false));
			//this is not working. sadly i dont understand how to summon those effects since those are chained ones
			if (modloaded && player.level().isClientSide()) {
				Vec3 startPos = last == extra ? player.getEyePosition(1.0F) : last.position();
	            Vec3 targetPos = target.position().add(0, target.getBbHeight() * 0.5, 0);
	            float energyScale = 0.4F; // Compute based on your logic
	            LightningEffectManager.addEffect(extra.getUUID(), new LightningEffectData(startPos, targetPos, energyScale, (int)Minecraft.getInstance().level.getGameTime()));
			}
			last = extra;
		}
	}

}

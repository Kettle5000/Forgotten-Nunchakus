package com.kettle.nunchakus.events;

import java.util.UUID;
import java.util.WeakHashMap;

import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import com.kettle.nunchakus.LeftClickStateManager;
import com.kettle.nunchakus.NunchakusConfig;
import com.kettle.nunchakus.items.BaseNunchakuItem;
import com.kettle.nunchakus.items.FireDragonBoneNunchaku;
import com.kettle.nunchakus.items.IceDragonBoneNunchaku;
import com.kettle.nunchakus.items.SilverNunchakuItem;
import com.kettle.pml.core.DamageControl;
import com.kettle.pml.core.PMLDamageTypes;
import com.kettle.pml.events.DamageHandler;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommonSidedEvents {
	
	public static WeakHashMap<UUID, Float> ComboValue = new WeakHashMap<UUID, Float>();
	public static WeakHashMap<UUID, Integer> ComboTimer = new WeakHashMap<UUID, Integer>();
    
	@SubscribeEvent
	public static void PlayerTickServer(TickEvent.PlayerTickEvent event) {
		if (event.player != null && event.phase == TickEvent.Phase.END) {
			UUID id = event.player.getUUID();
			if (ComboTimer.get(id) != null) {
				int timer = ComboTimer.get(id);
				if (timer == 0) {
					ComboValue.put(id, NunchakusConfig.combo_base);
				} else if (timer > 0){
					ComboTimer.put(id, timer - 1);
				}
			}
		}
	}
    
    @SubscribeEvent
    public static void NunchakusScaleDamage(LivingHurtEvent event) {
    	if (!event.isCanceled() && event.getSource().getEntity() != null && event.getSource().getEntity() instanceof Player player) {
    		Item item = player.getMainHandItem().getItem();
    		if (LeftClickStateManager.isLeftClickHeld(player) && item instanceof BaseNunchakuItem) {
    			UUID id = player.getUUID();
    			DamageSource source = event.getSource();
    			DamageControl control = DamageHandler.getDamageControl(source);
    			if (control != null && PMLDamageTypes.isMeleeAttack(source)) {
    				LivingEntity target = event.getEntity();
        			if (item instanceof SilverNunchakuItem && target instanceof Mob mob && mob.getMobType() == MobType.UNDEAD) {
        				control.addBaseFlatBonus(2f);
        			}
        			if (ModList.get().isLoaded("iceandfire")) {
        				if (target instanceof EntityIceDragon && item instanceof FireDragonBoneNunchaku) {
        					control.addBaseFlatBonus(8f);
        				}
        				if (target instanceof EntityFireDragon && item instanceof IceDragonBoneNunchaku) {
        					control.addBaseFlatBonus(8f);
        				}
        			}
        			float combo = Math.min(NunchakusConfig.combo_cap, ComboValue.getOrDefault(id, NunchakusConfig.combo_base));
        			control.SetWeaponDamage(combo);
    			}
    		}
    	}
    }
    
    
    
}

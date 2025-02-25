package com.kettle.nunchakus.events;

import java.util.UUID;
import java.util.WeakHashMap;

import com.kettle.nunchakus.ForgottenNunchakusMod;
import com.kettle.nunchakus.LeftClickStateManager;
import com.kettle.nunchakus.items.BaseNunchakuItem;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
					ComboValue.put(id, 0.1f);
				} else if (timer > 0){
					ComboTimer.put(id, timer - 1);
				}
			}
		}
	}
	
    @SubscribeEvent
    public static void detectingsides(AttackEntityEvent event) {
    	Player player = event.getEntity();
    	if (!player.level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
    		if (player.getMainHandItem().getItem() instanceof BaseNunchakuItem && LeftClickStateManager.isLeftClickHeld(player)) {
    			//We are not suppose to be doing this but since there seems to be desynchronizations more often in this version
    			// this will fix the issue temporarely
    			player.attackStrengthTicker = 100;
    		}
    	}
    }
    
    @SubscribeEvent
    public static void NunchakusScaleDamage(LivingHurtEvent event) {
    	if (!event.isCanceled() && event.getSource().getEntity() != null && event.getSource().getEntity() instanceof Player player) {
    		if (LeftClickStateManager.isLeftClickHeld(player)) {
    			UUID id = player.getUUID();
    			float combo = Math.min(1f, ComboValue.getOrDefault(id, 0.1f));
    			event.setAmount(event.getAmount() * (combo));
    		}
    	}
    }
    
}
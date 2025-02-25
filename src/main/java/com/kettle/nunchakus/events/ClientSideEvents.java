package com.kettle.nunchakus.events;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;

import com.github.alexthe666.iceandfire.client.particle.LightningBoltData;
import com.github.alexthe666.iceandfire.client.particle.LightningRender;
import com.kettle.nunchakus.LeftClickStatePacket;
import com.kettle.nunchakus.NunchakusNetwork;
import com.kettle.nunchakus.items.BaseNunchakuItem;
import com.kettle.nunchakus.render.LightningEffectData;
import com.kettle.nunchakus.render.LightningEffectManager;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientSideEvents {

	private static boolean lastAttackState = false;
	private static final LightningRender lightningRender = new LightningRender();

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (event.phase == TickEvent.Phase.END) {
        	if (player == null) return;
            // Check if the attack key (left click) is held down.
            boolean attackHeld = mc.options.keyAttack.isDown() && player.getMainHandItem().getItem() instanceof BaseNunchakuItem;

            // Only send a network update if the state has changed
            if (attackHeld != lastAttackState) {
                lastAttackState = attackHeld;
                // Send a custom packet to the server with the new state.
                NunchakusNetwork.channel.sendToServer(new LeftClickStatePacket(attackHeld));
            }
        }
    }
	
	public static void onNunchakuUse(Player player, ItemStack stack) {
		Minecraft instance = Minecraft.getInstance();
		if (player != instance.player) return;
		var mode = instance.gameMode;
		if (mode == null) return;
		var cd = player.getAttackStrengthScale(1);
		if (cd < 1) return;
		var hit = instance.hitResult;
		if (hit instanceof EntityHitResult entityResult) {
			var entity = entityResult.getEntity();
			if (!entity.isAlive()) return;
			if (!(entity instanceof ItemEntity) && !(entity instanceof ExperienceOrb) && !(entity instanceof AbstractArrow)) {
				mode.attack(player, entity);
			}
		}
	}
	
	//This is not working sadly.
	@SubscribeEvent
    public static void onRenderLiving(RenderLivingEvent.Post<LivingEntity, ? extends EntityModel<? extends LivingEntity>> event) {
        LivingEntity entity = event.getEntity();
        LightningEffectData data = LightningEffectManager.getEffect(entity.getUUID());
        if (data != null) {
            // Prepare your PoseStack and MultiBufferSource from the event context.
            PoseStack poseStack = event.getPoseStack();
            MultiBufferSource buffer = event.getMultiBufferSource();
            float partialTicks = event.getPartialTick();
            
            // Create a LightningBoltData similar to your RenderLightningDragon code.
            LightningBoltData bolt = new LightningBoltData(LightningBoltData.BoltRenderInfo.ELECTRICITY,
                    data.getStart(), data.getEnd(), 15)
                .size(0.05F * getBoundedScale(0.4F * data.getEnergyScale(), 0.5F, 2))
                .lifespan(4)
                .spawn(LightningBoltData.SpawnFunction.NO_DELAY);
            
            lightningRender.update(null, bolt, partialTicks);
            lightningRender.render(partialTicks, poseStack, buffer);
            
            // Optionally remove the effect after rendering if it's one-off.
            //LightningEffectManager.removeEffect(entity.getUUID());
            poseStack.popPose();
        }
        
        // Optionally, clean up expired effects periodically.
        LightningEffectManager.cleanup();
    }
    
    private static float getBoundedScale(float scale, float min, float max) {
        return min + scale * (max - min);
    }
	
	

}

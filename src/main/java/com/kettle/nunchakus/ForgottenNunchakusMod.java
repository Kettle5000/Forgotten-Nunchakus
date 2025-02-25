package com.kettle.nunchakus;

import com.kettle.nunchakus.items.ItemRegister;
import com.mojang.logging.LogUtils;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ForgottenNunchakusMod.MODID)
public class ForgottenNunchakusMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "fn";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public ForgottenNunchakusMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the Deferred Register to the mod event bus so blocks get registered
        modEventBus.addListener(this::RegisterSpin);
        ItemRegister.ITEMS.register(modEventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        
        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        NunchakusNetwork.init();
        
    }
    
    private void RegisterSpin(final FMLClientSetupEvent event) {
		 List<RegistryObject<Item>> items = List.of(ItemRegister.WOOD_NUNCHAKUS, ItemRegister.STONE_NUNCHAKUS, ItemRegister.COPPER_NUNCHAKUS,
				 ItemRegister.IRON_NUNCHAKUS, ItemRegister.GOLD_NUNCHAKUS, ItemRegister.DIAMOND_NUNCHAKUS, ItemRegister.NETHERITE_NUNCHAKUS,
				 ItemRegister.SILVER_NUNCHAKUS, ItemRegister.DRAGONBONE_NUNCHAKUS, ItemRegister.DESERTCHITIN_NUNCHAKUS, ItemRegister.FIREDRAGONBONE_NUNCHAKUS,
				 ItemRegister.JUNGLECHITIN_NUNCHAKUS, ItemRegister.ICEDRAGONBONE_NUNCHAKUS, ItemRegister.LIGHTNINGDRAGONBONE_NUNCHAKUS);
    	 event.enqueueWork(() -> {
    	        // Assuming you have something like this in your ItemRegister:
    	        // public static final RegistryObject<Item> WOOD_NUNCHAKUS = ...;
    	        // public static final RegistryObject<Item> STONE_NUNCHAKUS = ...;
    	        for (RegistryObject<Item> nunchaku : items) {
    	            ItemProperties.register(nunchaku.get(), new ResourceLocation("spin"), (stack, level, living, j) -> {
    	                if (living instanceof Player player && LeftClickStateManager.isLeftClickHeld(player) && stack == player.getMainHandItem()) {
    	                    return 0.0f;
    	                }
    	                return 0.5f;
    	            });
    	        }
    	    });
    }

}

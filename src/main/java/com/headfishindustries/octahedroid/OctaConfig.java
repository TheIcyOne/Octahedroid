package com.headfishindustries.octahedroid;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid=Octahedroid.MODID)
public class OctaConfig {
	
	@Config.Comment("Client configuration options.")
	public static final Client client = new Client();

	public static class Client{
		@Config.Comment("Set to false to use a static block model for the Octahedroid")
		@Config.RequiresMcRestart
		public boolean TESRact = true;
	}
	
	
	@Mod.EventBusSubscriber(modid=Octahedroid.MODID)
	protected static class EventHandler{
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent e) {
			if (e.getModID().equals(Octahedroid.MODID)) {
				ConfigManager.sync(Octahedroid.MODID, Config.Type.INSTANCE);
			}
		}
	}

}

package com.headfishindustries.octahedroid;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid=Octahedroid.MODID)
public class OctaConfig {
	
	@Config.LangKey("config.client.title")
	public static final Client client = new Client();
	
	@Config.LangKey("config.balance.title")
	public static final Balance balance = new Balance();

	public static class Client{
		@Config.Comment("Use TESR models. Set this to false to disable my fancy animations.")
		@Config.RequiresMcRestart
		public boolean TESRact = true;
	}
	
	public static class Balance{
		@Config.Comment("Max energy throughput of the Octahedroid in FE.")
		@Config.RequiresWorldRestart
		public int maxThroughputEnergy = 100000;
		
		@Config.Comment("Max fluid throughput of the Octahedroid in mB. 1000 is 1 bucket.")
		public int maxThroughputFluid = 10000;
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

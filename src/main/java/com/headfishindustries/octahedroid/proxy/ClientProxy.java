package com.headfishindustries.octahedroid.proxy;

import com.headfishindustries.octahedroid.OctaConfig;
import com.headfishindustries.octahedroid.Octahedroid;
import com.headfishindustries.octahedroid.client.render.RenderOctahedroid;
import com.headfishindustries.octahedroid.tile.TileOctahedroid;

import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
	
	@SuppressWarnings("deprecation")
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		//stfu forge this is fine
		if (OctaConfig.client.TESRact) {
			ClientRegistry.bindTileEntitySpecialRenderer(TileOctahedroid.class, new RenderOctahedroid());
		}
	}

}

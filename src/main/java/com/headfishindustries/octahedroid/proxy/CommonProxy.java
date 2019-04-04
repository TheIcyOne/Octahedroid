package com.headfishindustries.octahedroid.proxy;

import com.headfishindustries.octahedroid.Octahedroid;
import com.headfishindustries.octahedroid.client.gui.GuiProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
		
	}
	
	public void init(FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(Octahedroid.INSTANCE, new GuiProxy());
	}

}

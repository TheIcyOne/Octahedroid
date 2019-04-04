package com.headfishindustries.octahedroid.client.gui;

import com.headfishindustries.octahedroid.tile.TileOctahedroid;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class OctaContainer extends Container{

	private TileOctahedroid tile;
	
	public OctaContainer(TileOctahedroid te) {
		this.tile = te;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}

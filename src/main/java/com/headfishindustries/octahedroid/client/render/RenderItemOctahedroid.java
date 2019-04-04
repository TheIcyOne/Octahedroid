package com.headfishindustries.octahedroid.client.render;

import org.lwjgl.opengl.GL11;

import com.headfishindustries.octahedroid.client.render.model.ModelOctahedroid;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderItemOctahedroid extends TileEntityItemStackRenderer{

	private static TextureManager renderEngine ;
	private static final ResourceLocation casingTexture = new ResourceLocation("minecraft", "textures/blocks/obsidian.png");
	private static final ResourceLocation coreTexture = new ResourceLocation("minecraft", "textures/environment/end_sky.png");
	
	private ModelOctahedroid model = new ModelOctahedroid();
	
	@Override
	public void renderByItem(ItemStack stack) {
		if (renderEngine == null) renderEngine = Minecraft.getMinecraft().renderEngine;
		super.renderByItem(stack);
		float scaling = 1;
		
		GlStateManager.pushMatrix();
		{
			GlStateManager.pushMatrix();
			{
				renderEngine.bindTexture(casingTexture);
				GlStateManager.translate(0.5, 0.5, 0.5);
				GlStateManager.pushMatrix();
				{
					double f = Math.sin(scaling * 0.5);
					GlStateManager.scale(f, f,  f);
					GlStateManager.rotate((float) Math.cos(scaling) * 360 ,1, 1, 1);
					model.renderCasing();
				}
				GlStateManager.popMatrix();
				
				GlStateManager.pushMatrix();
				{
					double f = Math.cos(scaling * 0.5);
					GlStateManager.scale(f, f,  f);
					GlStateManager.rotate((float) Math.cos(scaling) * 360 ,1, 1, 1);
					model.renderCasing();
				}
				GlStateManager.popMatrix();
			}
			GlStateManager.popMatrix();
			
			GlStateManager.pushMatrix();
			{
				renderEngine.bindTexture(coreTexture);
				GlStateManager.translate(0.475, 0.475, 0.475);
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GlStateManager.color(0.5f, 0.2f, 0.5f, 0.85f);
				double f = 0.05 * Math.sin(scaling) + 0.5;
				GlStateManager.rotate((float) Math.sin(scaling) * 360 ,(float) f,(float) 2f,(float) f);
				GlStateManager.scale(f, f, f);
				model.renderCore();
			}
			GlStateManager.popMatrix();
		}
		GlStateManager.popMatrix();
	
		
	}

}

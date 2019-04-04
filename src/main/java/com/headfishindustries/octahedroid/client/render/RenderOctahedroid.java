package com.headfishindustries.octahedroid.client.render;

import org.lwjgl.opengl.GL11;

import com.headfishindustries.octahedroid.client.render.model.ModelOctahedroid;
import com.headfishindustries.octahedroid.tile.TileOctahedroid;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderOctahedroid extends TileEntitySpecialRenderer<TileOctahedroid>{
	
	private static TextureManager renderEngine;
	private static final ResourceLocation casingTexture = new ResourceLocation("minecraft", "textures/blocks/obsidian.png");
	private static final ResourceLocation coreTexture = new ResourceLocation("minecraft", "textures/environment/end_sky.png");
	
	private ModelOctahedroid model = new ModelOctahedroid();

	@Override
	public void render(TileOctahedroid te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
		if (renderEngine == null) renderEngine = Minecraft.getMinecraft().renderEngine;
		int off = te == null ? 0 : te.getPos().hashCode();
		renderModel(te, x, y, z, off + (Minecraft.getMinecraft().world.getTotalWorldTime() + partialTicks) * 0.05f );
    }
	
	private void renderModel(TileOctahedroid tile, double x, double y, double z, float scaling) {
		GlStateManager.pushMatrix();
		{
			GlStateManager.translate(x, y, z);
			GlStateManager.pushMatrix();
			{
				renderEngine.bindTexture(casingTexture);
				GlStateManager.translate(0.5, 0.5, 0.5);
				GlStateManager.scale(0.8, 0.8, 0.8);
				
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
				GlStateManager.translate(0.5, 0.5, 0.5);
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GlStateManager.color(0.5f, 0.2f, 0.5f, 0.85f);
				double f = 0.05 * Math.sin(scaling) + 0.25;
				GlStateManager.rotate((float) Math.sin(scaling) * 360 ,(float) f,(float) 2f,(float) f);
				GlStateManager.scale(f, f, f);
				model.renderCore();
			}
			GlStateManager.popMatrix();
		}
		GlStateManager.popMatrix();
	}

}

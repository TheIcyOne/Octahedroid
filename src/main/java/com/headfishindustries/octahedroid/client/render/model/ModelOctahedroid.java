package com.headfishindustries.octahedroid.client.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelOctahedroid extends ModelBase{
		
		private final ModelRenderer casing;
		private final ModelRenderer core;

		public ModelOctahedroid() {
			textureWidth = 16;
			textureHeight = 16;
			casing = new ModelRenderer(this, 0, 0);
			casing.addBox(-8, -8, -8, 2, 16, 2);
			casing.addBox(6, -8, 6, 2, 16, 2);
			casing.addBox(-8, -8, 6, 2, 16, 2);
			casing.addBox(6, -8, -8, 2, 16, 2);
			
			casing.addBox(-6, -8, -8, 12, 2, 2);
			casing.addBox(-6, -8, 6, 12, 2, 2);
			casing.addBox(-6, 6, -8, 12, 2, 2);
			casing.addBox(-6, 6, 6, 12, 2, 2);
			
			casing.addBox(-8, -8, -6, 2, 2, 12);
			casing.addBox(-8, 6, -6, 2, 2, 12);
			casing.addBox(6, -8, -6, 2, 2, 12);
			casing.addBox(6, 6, -6, 2, 2, 12);
			
			casing.setRotationPoint(0, 0, 0);
			
			core = new ModelRenderer(this, 0, 0);
			core.addBox(-6, -6, -6, 14, 14, 14);
			core.setRotationPoint(0, 0, 0);
		}
		
		public void renderCasing() {
			casing.render(1/16f);
		}
		
		public void renderCore() {
			core.render(1/16f);
		}

}

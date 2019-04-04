package com.headfishindustries.octahedroid.client.gui;

import java.io.IOException;
import com.google.common.base.Predicate;

import org.lwjgl.input.Keyboard;

import com.headfishindustries.octahedroid.Octahedroid;
import com.headfishindustries.octahedroid.net.MessageUpdateChannel;
import com.headfishindustries.octahedroid.tile.TileOctahedroid;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class OctaGui extends GuiScreen{
	
	private TileOctahedroid octa;
	
	public static final int WIDTH = 180;
    public static final int HEIGHT = 152;
	
	private GuiButton updateChannelButton;
	
	private GuiTextField channelField;
	private GuiLabel channelLabel;
	
	private Predicate<String> validator = new Predicate<String>(){
		@Override
		public boolean apply(String input) {
			return input.matches("^[0-9\\-]*$");
		}
	};

	public OctaGui(TileOctahedroid tile) {
		this.octa = tile;
		width = WIDTH;
		height = HEIGHT;
	}
	
	public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
		
		int curID = 0;
		int x = this.width / 2 - 80;
		int y =  this.height / 2 - 40;
		
		this.channelLabel = new GuiLabel(this.fontRenderer, curID++, x, y, 40, 20, 0xFFFFFF);
		this.channelLabel.addLine("Channel: ");
		
		this.channelField = new GuiTextField(curID++, this.fontRenderer, x + 50, y, 60, 20);
		this.channelField.setText("" + this.octa.getChannelID());
		this.channelField.setVisible(true);
		this.channelField.setFocused(true);
		this.channelField.setValidator(this.validator);
		
		this.updateChannelButton = this.addButton(new GuiButton(curID++, x + 125, y, 20, 20, "OK"));
		
		
    }
	
	protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
		this.channelField.textboxKeyTyped(typedChar, keyCode);

        if (keyCode != 28 && keyCode != 156)
        {
        	if (keyCode == 1) {
        		this.mc.displayGuiScreen((GuiScreen)null);
        	}
        }
        else
        {     
        	this.actionPerformed(this.updateChannelButton);
        }
    }
	
	public void updateScreen()
    {
		this.channelField.updateCursorCounter();
    }
	
	public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	protected void actionPerformed(GuiButton button) throws IOException
    {
		if (button.enabled) {
			if (button.equals(this.updateChannelButton)) {
				Octahedroid.WRAPPER.sendToServer(new MessageUpdateChannel(octa.getPos(), Integer.parseInt(this.channelField.getText())));
				this.mc.displayGuiScreen((GuiScreen)null);
			}
		}
    }
	
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
			super.mouseClicked(mouseX, mouseY, mouseButton);
			this.channelField.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
		this.drawDefaultBackground();
		this.channelField.drawTextBox();
		this.channelLabel.drawLabel(mc, mouseX, mouseY);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
    }

}

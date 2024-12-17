package vip.gothaj.client.ui.clickguis.Panel.screens.ads;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import vip.gothaj.client.Client;
import vip.gothaj.client.modules.Category;
import vip.gothaj.client.ui.clickguis.Panel.ClickGui;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.ModuleButton;
import vip.gothaj.client.ui.clickguis.Panel.screens.Screen;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.render.RenderUtils;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.ScrollBar;

public class ModuleScreen extends Screen{

	private Category category;
	
	public ModuleScreen(ClickGui screen, Category category) {
		super(screen);
		this.category = category;
		this.contentPosition.setHeight(290);
		this.scroll = new ScrollBar(contentPosition.getHeight(), 0, 4, contentPosition.getHeight());
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {
		
		RoundedUtils.drawRoundedRect(contentPosition, Client.INSTANCE.getThemeManager().get("Panel Screen Backround Color"), 0,0,3,3);
		RoundedUtils.drawRoundedRect(contentPosition.getX(), contentPosition.getY() - 18, contentPosition.getWidth(),
				18, 0xff121921, 3, 3, 0, 0);
		
		Fonts.drawString(this.screen.textBox.getText().equals("") ? category.name().toUpperCase().substring(0,1)+category.name().toLowerCase().substring(1) : "Modules", contentPosition.getX()+5, contentPosition.getY() - 15, -1, "tahoma", 1.3);
	
		this.scroll.track.setX(contentPosition.getX2()-4);
		this.scroll.track.setY(contentPosition.getY());
		
		double height = 0;
		RenderUtils.enableScisor();
		RenderUtils.scissor(new ScaledResolution(Minecraft.getMinecraft()), contentPosition.getX(), contentPosition.getY(), contentPosition.getWidth(), contentPosition.getHeight());
		for(ModuleButton m : screen.modules) {
			
			if(m.getModule().getCategory() != category && this.screen.textBox.getText().equals("")) continue;
			else if(!this.screen.textBox.getText().equals("") && !m.getModule().getName().toLowerCase().contains(screen.textBox.getText().toLowerCase())) continue;
			
			m.setPosition(contentPosition.getX()+2, contentPosition.getY()+2+height-this.scroll.scroll);
			m.drawScreen(mouseX, mouseY);
			height+=m.getPosition().getHeight()+2;
		}
		
		RenderUtils.disableScisor();
		this.scroll.update(mouseY);
		RoundedUtils.drawRoundedRect(this.scroll.thumb, Client.INSTANCE.getThemeManager().get("Panel Screen Scroll Thumb Color"), 0.5f);
		
		this.scroll.max = height+2;
		if(contentPosition.isInside(mouseX, mouseY))this.scroll.scrollMouseInput();
		
	}

	@Override
	public void onClick(int mouseX, int mouseY, int button) {
		if(!contentPosition.isInside(mouseX, mouseY)) return;
		for(ModuleButton m : screen.modules) {
			
			
			if(m.getModule().getCategory() != category && this.screen.textBox.getText().equals("")) continue;
			
			m.onClick(mouseX, mouseY, button);
		}
		this.scroll.onClick(mouseX, mouseY, button);
	}

	@Override
	public void onRelease(int mouseX, int mouseY, int button) {
		
		for(ModuleButton m : screen.modules) {
			
			if(m.getModule().getCategory() != category && this.screen.textBox.getText().equals("")) continue;
			
			m.onRelease(mouseX, mouseY, button);
		}
		this.scroll.onRelease(mouseX, mouseY, button);
	}

	@Override
	public void onKey(int key, char ch) {

	}

}

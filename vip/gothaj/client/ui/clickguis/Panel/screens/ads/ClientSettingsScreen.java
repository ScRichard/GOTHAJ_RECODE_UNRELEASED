package vip.gothaj.client.ui.clickguis.Panel.screens.ads;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import vip.gothaj.client.Client;
import vip.gothaj.client.ui.clickguis.Panel.ClickGui;
import vip.gothaj.client.ui.clickguis.Panel.extensions.clientsettings.ClientSettingsObject;
import vip.gothaj.client.ui.clickguis.Panel.screens.Screen;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.render.RenderUtils;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.ScrollBar;
import vip.gothaj.client.utils.ui.ScrollBar.ScrollType;
import vip.gothaj.client.values.settings.CategoryValue;

public class ClientSettingsScreen extends Screen{
	
	private ArrayList<ClientSettingsObject> objects = new ArrayList();
	
	public ClientSettingsScreen(ClickGui screen) {
		super(screen);
		this.contentPosition.setHeight(290);
		this.scroll = new ScrollBar(contentPosition.getWidth(), 0, 4, contentPosition.getWidth());
		
		for(CategoryValue v : Client.INSTANCE.getClientSettings().getSettings()) {
			objects.add(new ClientSettingsObject(v, this));
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY) {
		
		RoundedUtils.drawRoundedRect(contentPosition, Client.INSTANCE.getThemeManager().get("Panel Screen Backround Color"), 0,0,3,3);
		RoundedUtils.drawRoundedRect(contentPosition.getX(), contentPosition.getY() - 18, contentPosition.getWidth(),
				18, 0xff121921, 3, 3, 0, 0);
		
		Fonts.drawString("Client Settings", contentPosition.getX()+5, contentPosition.getY() - 15, -1, "tahoma", 1.3);
	
		this.scroll.track.setX(contentPosition.getX());
		this.scroll.track.setY(contentPosition.getY2()-4);
		
		double width = 0;
		RenderUtils.enableScisor();
		RenderUtils.scissor(new ScaledResolution(Minecraft.getMinecraft()), contentPosition.getX(), contentPosition.getY(), contentPosition.getWidth(), contentPosition.getHeight());
		
		for(ClientSettingsObject o : objects) {
			o.getPosition().setX(contentPosition.getX()+2+width-this.scroll.scroll);
			o.getPosition().setY(contentPosition.getY()+2);
			o.drawScreen(mouseX, mouseY);
			width+= 2 + o.getPosition().getWidth();
		}
		
		RenderUtils.disableScisor();
		this.scroll.update(mouseY);
		RoundedUtils.drawRoundedRect(this.scroll.thumb, Client.INSTANCE.getThemeManager().get("Panel Screen Scroll Thumb Color"), 0.5f);
		
		this.scroll.max = width;
		
		if(contentPosition.isInside(mouseX, mouseY))this.scroll.scrollMouseInput();
	}
	
}

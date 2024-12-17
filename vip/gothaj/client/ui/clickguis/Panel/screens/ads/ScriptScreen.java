package vip.gothaj.client.ui.clickguis.Panel.screens.ads;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import vip.gothaj.client.Client;
import vip.gothaj.client.scripts.Script;
import vip.gothaj.client.ui.clickguis.Panel.ClickGui;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.ModuleButton;
import vip.gothaj.client.ui.clickguis.Panel.extensions.script.ScriptComponent;
import vip.gothaj.client.ui.clickguis.Panel.screens.Screen;
import vip.gothaj.client.utils.animations.Animation;
import vip.gothaj.client.utils.animations.AnimationType;
import vip.gothaj.client.utils.client.ChatUtils;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.font.icons.IconUtils;
import vip.gothaj.client.utils.render.ColorUtils;
import vip.gothaj.client.utils.render.RenderUtils;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.PositionUtils;
import vip.gothaj.client.utils.ui.ScrollBar;

public class ScriptScreen extends Screen{
	
	public ArrayList<ScriptComponent> scripts = new ArrayList();
	
	private PositionUtils reload = new PositionUtils(0, 0, 13, 13, 1);
	
	private Animation animation = new Animation(500, AnimationType.EaseInOutBack);

	public ScriptScreen(ClickGui screen) {
		super(screen);
		
		this.contentPosition.setHeight(295);
		this.scroll = new ScrollBar(contentPosition.getHeight(), 0, 4, contentPosition.getHeight());
		
		reloadScripts();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY) {
		
		RoundedUtils.drawRoundedRect(contentPosition, Client.INSTANCE.getThemeManager().get("Panel Screen Backround Color"), 0,0,3,3);
		RoundedUtils.drawRoundedRect(contentPosition.getX(), contentPosition.getY() - 18, contentPosition.getWidth(),
				18, 0xff121921, 3, 3, 0, 0);
		
		Fonts.drawString("Scripts", contentPosition.getX()+5, contentPosition.getY() - 15, -1, "tahoma", 1.3);
	
		this.scroll.track.setX(contentPosition.getX2()-4);
		this.scroll.track.setY(contentPosition.getY());
		
		reload.setX(this.contentPosition.getX2()-15);
		reload.setY(this.contentPosition.getY()-18+2.5);
		
		
		Fonts.drawString("Reload all",reload.getX() - Fonts.getWidth("Reload all", "roboto-medium", 0.95) - 5,
				reload.getY() + 3,
				ColorUtils.mix(Client.INSTANCE.getThemeManager().get("Panel Descriptions Color"),
						Client.INSTANCE.getThemeManager().get("Panel Text Color"),
						animation.calculateAnimation()),
				"roboto-medium", 0.95);
		
		animation.reversed = !reload.isInside(mouseX, mouseY);
		
		GL11.glPushMatrix();
		GL11.glTranslated(this.reload.getX()+this.reload.getWidth()/2, this.reload.getY()+this.reload.getHeight()/2, 0.);
		
		GL11.glRotated(-animation.calculateAnimation() * 360, 0, 0, 1);
		IconUtils.drawIcon("reload", -this.reload.getWidth()/2+1, -this.reload.getHeight()/2+1, Client.INSTANCE.getThemeManager().get("Panel Text Color"), 0.9);
		GL11.glPopMatrix();
		
		double height = 0;
		RenderUtils.enableScisor();
		RenderUtils.scissor(new ScaledResolution(Minecraft.getMinecraft()), contentPosition.getX(), contentPosition.getY(), contentPosition.getWidth(), contentPosition.getHeight());

		for(ScriptComponent m : scripts) {
			
		
			if(!this.screen.textBox.getText().equals("") && !m.getScript().getName().toLowerCase().contains(screen.textBox.getText().toLowerCase())) continue;
			
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

		if(reload.isInside(mouseX, mouseY) && button == 0) {
			Client.INSTANCE.getScriptManager().loadAll();
			reloadScripts();
			ChatUtils.debug("Reloaded Scripts");
		}
		
		this.scroll.onClick(mouseX, mouseY, button);
	}

	@Override
	public void onRelease(int mouseX, int mouseY, int button) {
		
		this.scroll.onRelease(mouseX, mouseY, button);
	}

	@Override
	public void onKey(int key, char ch) {
		
	}
	
	public void reloadScripts() {
		scripts.clear();
		for(Script script : Client.INSTANCE.getScriptManager().getScripts()) {
			scripts.add(new ScriptComponent(script, this));
		}
		scripts.sort((m1, m2) -> m1.getScript().getName().compareTo(m2.getScript().getName()));
	}

}

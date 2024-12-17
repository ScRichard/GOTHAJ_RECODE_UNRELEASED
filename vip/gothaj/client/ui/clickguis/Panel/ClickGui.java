package vip.gothaj.client.ui.clickguis.Panel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiScreen;
import vip.gothaj.client.Client;
import vip.gothaj.client.modules.Bind;
import vip.gothaj.client.modules.Category;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.modules.Bind.Ac;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.ContentButton;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.ModuleButton;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings.PanelKeybindSettings;
import vip.gothaj.client.ui.clickguis.Panel.screens.Screen;
import vip.gothaj.client.ui.clickguis.Panel.screens.ads.ClientSettingsScreen;
import vip.gothaj.client.ui.clickguis.Panel.screens.ads.ConfigScreen;
import vip.gothaj.client.ui.clickguis.Panel.screens.ads.ModuleScreen;
import vip.gothaj.client.ui.clickguis.Panel.screens.ads.ScriptScreen;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.render.text.ext.SearchTextBox;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.DragUtils;
import vip.gothaj.client.utils.ui.PositionUtils;

public class ClickGui extends GuiScreen {
	
	
	private PositionUtils gui = new PositionUtils(0,0,400,350,1);
	private PositionUtils bar = new PositionUtils(0,0,400,8,1);
	
	private DragUtils dragging = new DragUtils(0, bar);
	
	private LinkedHashMap<String, ArrayList<ContentButton>> buttons = new LinkedHashMap<String, ArrayList<ContentButton>>();	
	
	public ContentButton active;
	
	public ArrayList<ModuleButton> modules = new ArrayList();
	
	public SearchTextBox textBox = new SearchTextBox("roboto-bold", new PositionUtils(10,10,100,17,1));
	
	public PanelKeybindSettings binding;
	
	public ClickGui() {
		
		
		ArrayList<ContentButton> buttons = new ArrayList();
		
		buttons.add(active = new ContentButton("Combat", "combat", new ModuleScreen(this, Category.COMBAT), this));
		buttons.add(new ContentButton("Movement", "movement", new ModuleScreen(this, Category.MOVEMENT), this));
		buttons.add(new ContentButton("Visuals", "visuals",new ModuleScreen(this, Category.VISUALS), this));
		buttons.add(new ContentButton("Player", "player", new ModuleScreen(this, Category.PLAYER), this));
		buttons.add(new ContentButton("Client", "client",new ModuleScreen(this, Category.CLIENT), this));
		
		this.buttons.put("Modules", buttons);
		ArrayList<ContentButton> button1 = new ArrayList();

		button1.add(new ContentButton("Client Settings", "pencil", new ClientSettingsScreen(this), this));
		button1.add(new ContentButton("Configs", "folder", new ConfigScreen(this), this));
		button1.add(new ContentButton("Scripts", "script",new ScriptScreen(this), this));
		
		this.buttons.put("Client", button1);
		
		
		for(Mod m : Client.INSTANCE.getModuleManager().getModules()) {
			modules.add(new ModuleButton(m, this));
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		gui.setX(bar.getX());
		gui.setY(bar.getY());
		RoundedUtils.drawRoundedRect(gui, Client.INSTANCE.getThemeManager().get("Panel Background Color"), 3);
		
		RoundedUtils.drawRoundedRect(bar, 0xff141E27, 3, 3, 0,0);
		
	
		
		dragging.update(mouseX, mouseY);
		
		this.active.getContentScreent().contentPosition.setX(bar.getX()+100);
		this.active.getContentScreent().contentPosition.setY(gui.getY2()-this.active.getContentScreent().contentPosition.getHeight()-2);
		
		this.textBox.getPosition().setX(this.active.getContentScreent().contentPosition.getX()+this.active.getContentScreent().contentPosition.getWidth()/2-this.textBox.getPosition().getWidth()/2);
		this.textBox.getPosition().setY(gui.getY()+14);
		textBox.drawScreen(mouseX, mouseY);
		
		double i = 0;
		for(Entry<String, ArrayList<ContentButton>> content : buttons.entrySet()) {
			Fonts.drawString(content.getKey(), gui.getX()+50-Fonts.getWidth(content.getKey(), "roboto-bold", 0.9)/2, gui.getY()+40+i, Client.INSTANCE.getThemeManager().get("Panel Descriptions Color"), "roboto-bold", 0.9);
			i+=12;
			for(ContentButton b : content.getValue()) {
				b.setPosition(gui.getX()+5, gui.getY()+40+i);
				b.drawScreen(mouseX, mouseY);
				i+=b.getPosition().getHeight()+2;
			}
		}
		
		this.active.getContentScreent().drawScreen(mouseX, mouseY);
		
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(binding != null) {
			if(keyCode == Keyboard.KEY_ESCAPE) {
				binding.getParent().getModule().setBind(null);
				binding = null;
				return;
			}
			binding.getParent().getModule().setBind(new Bind(keyCode, Ac.Keyboard));
			binding = null;
			return;
		}
		
		
		textBox.onKey(keyCode, typedChar);
		
		this.active.onKey(keyCode, typedChar);
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if(binding != null) {
			if(mouseButton == Keyboard.KEY_ESCAPE) {
				binding.getParent().getModule().setBind(null);
				binding = null;
				return;
			}
			binding.getParent().getModule().setBind(new Bind(mouseButton, Ac.Mouse));
			binding = null;
			return;
		}
		dragging.onClick(mouseX, mouseY, mouseButton);
		
		textBox.onClick(mouseX, mouseY, mouseButton);
		
		this.active.getContentScreent().onClick(mouseX, mouseY, mouseButton);
		
		buttons.values().forEach(l -> l.forEach(m -> m.onClick(mouseX, mouseY, mouseButton)));
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		this.active.getContentScreent().onRelease(mouseX, mouseY, state);
		dragging.onRelease();
	}

	@Override
	public void initGui() {

	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	
	
}

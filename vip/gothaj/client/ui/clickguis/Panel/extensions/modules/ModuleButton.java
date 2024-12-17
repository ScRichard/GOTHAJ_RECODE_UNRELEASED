package vip.gothaj.client.ui.clickguis.Panel.extensions.modules;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.renderer.GlStateManager;
import vip.gothaj.client.Client;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.ui.clickguis.Panel.ClickGui;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings.PanelBooleanSettings;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings.PanelCategorySetting;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings.PanelColorSettings;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings.PanelDescriptionSettings;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings.PanelKeybindSettings;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings.PanelLocationSettings;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings.PanelModeSettings;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings.PanelMultipleBooleanSettings;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings.PanelNumberSettings;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings.PanelRangeSettings;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings.PanelSettings;
import vip.gothaj.client.utils.animations.Animation;
import vip.gothaj.client.utils.animations.AnimationType;
import vip.gothaj.client.utils.client.ChatUtils;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.font.icons.IconUtils;
import vip.gothaj.client.utils.render.RenderUtils;
import vip.gothaj.client.utils.scissors.ScissorUtils;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.Button;
import vip.gothaj.client.utils.ui.PositionUtils;
import vip.gothaj.client.values.Value;
import vip.gothaj.client.values.settings.BooleanValue;
import vip.gothaj.client.values.settings.CategoryValue;
import vip.gothaj.client.values.settings.ColorValue;
import vip.gothaj.client.values.settings.DescriptionValue;
import vip.gothaj.client.values.settings.LocationValue;
import vip.gothaj.client.values.settings.ModeValue;
import vip.gothaj.client.values.settings.MultipleBooleanValue;
import vip.gothaj.client.values.settings.NumberValue;
import vip.gothaj.client.values.settings.RangeValue;

public class ModuleButton extends Button {

	private Mod module;

	private PositionUtils moduleEnabler = new PositionUtils(0, 0, 290, 30, 1);
	private PositionUtils position = new PositionUtils(0, 0, 290, 30, 1);

	private Animation animation = new Animation(100, AnimationType.EaseInOutBack), openAnimation = new Animation(200, AnimationType.EaseInOutBack);
	
	private boolean open;
	
	private ClickGui clickGui;
	
	private ArrayList<PanelSettings> settings = new ArrayList();
	double height;

	public ModuleButton(Mod module, ClickGui clickGui) {
		this.module = module;
		settings.clear();
		animation.reversed = true;
		this.clickGui = clickGui;
		
		settings.add(new PanelKeybindSettings(this));
		
		for(Value s : module.getSettings()) {
			if(s instanceof RangeValue) {
				settings.add(new PanelRangeSettings(this, (RangeValue) s));
			}
			if(s instanceof NumberValue) {
				settings.add(new PanelNumberSettings(this, (NumberValue) s));
			}
			if(s instanceof ModeValue) {
				settings.add(new PanelModeSettings(this, (ModeValue) s));
			}
			if(s instanceof BooleanValue) {
				settings.add(new PanelBooleanSettings(this, (BooleanValue) s));
			}
			if(s instanceof CategoryValue) {
				settings.add(new PanelCategorySetting(this, (CategoryValue) s));
			}
			if(s instanceof LocationValue) {
				settings.add(new PanelLocationSettings(this, (LocationValue) s));
			}
			if(s instanceof ColorValue) {
				settings.add(new PanelColorSettings(this, (ColorValue) s));
			}
			if(s instanceof MultipleBooleanValue) {
				settings.add(new PanelMultipleBooleanSettings(this, (MultipleBooleanValue) s));
			}
			if(s instanceof DescriptionValue) {
				settings.add(new PanelDescriptionSettings(this, (DescriptionValue) s));
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {
		RoundedUtils.drawRoundedRect(position, Client.INSTANCE.getThemeManager().get("Panel Module Background"), 3);
		
		animation.reversed = !this.module.isEnabled();
		
		this.openAnimation.reversed = !this.open;
		moduleEnabler.setX(position.getX());
		moduleEnabler.setY(position.getY());

		RoundedUtils.drawRoundedRect(position.getX() + 2, this.position.getY() + 2, 26, 26, Client.INSTANCE.getThemeManager().get("Panel Other Buttons Background"), 3);
		GlStateManager.pushMatrix();
		GlStateManager.translate(
				position.getX() + 15 - 15 * animation.calculateAnimation()
						- (position.getX()) * animation.calculateAnimation(),
				position.getY() + 15 - 15 * animation.calculateAnimation()
						- (position.getY()) * animation.calculateAnimation(),
				0);
		GlStateManager.scale(animation.calculateAnimation(), animation.calculateAnimation(), 0);
		RoundedUtils.drawRoundedRect(position.getX() + 2, this.position.getY() + 2, 26, 26, Client.INSTANCE.getThemeManager().get("Panel Active Color"), 3);
		IconUtils.drawIcon("check", position.getX() + 15 - IconUtils.getIconHeight("check",1.2) / 2,
				this.position.getY() + 15 - IconUtils.getIconHeight("check",1.2) / 2, Client.INSTANCE.getThemeManager().get("Panel Text Color"), 1.2);

		GlStateManager.popMatrix();
		
		
		
		String b = (module.getBind() == null) ? "NONE"
				: (Keyboard.getKeyName(module.getBind().getKey()).equals("NONE") ? module.getBind().getKey() + ""
						: Keyboard.getKeyName(module.getBind().getKey()));

		Fonts.drawString(module.getName(), this.position.getX() + 32, this.position.getY() + 5, Client.INSTANCE.getThemeManager().get("Panel Text Color"), "roboto-bold",
				1.05);
		Fonts.drawString(module.getDescription(), this.position.getX() + 34,
				this.position.getY() + 30 - 5 - Fonts.getHeight("roboto-bold"), Client.INSTANCE.getThemeManager().get("Panel Descriptions Color"),
				"roboto-medium");
		height = 0;
		
		ScissorUtils.stencilScisor(() -> RenderUtils.drawRect(this.position, -1),
				() -> {
					if(open || (!open && !this.openAnimation.isFinished())) {
						for(PanelSettings s : settings) {
							if(!s.isVisible()) continue;
							
							s.getPosition().setX(this.position.getX()+20);
							s.getPosition().setY(this.position.getY()+30+height);
							height+=s.getPosition().getHeight();
							s.drawScreen(mouseX, mouseY);
						}
					}
				}
				,
				!this.openAnimation.isFinished());
		
		position.setHeight(30+height*openAnimation.calculateAnimation());
	}


	public PositionUtils getModuleEnabler() {
		return moduleEnabler;
	}

	public Animation getAnimation() {
		return animation;
	}

	public Animation getOpenAnimation() {
		return openAnimation;
	}

	public boolean isOpen() {
		return open;
	}

	public ClickGui getClickGui() {
		return clickGui;
	}

	public ArrayList<PanelSettings> getSettings() {
		return settings;
	}

	@Override
	public void onClick(int mouseX, int mouseY, int button) {
		if (moduleEnabler.isInside(mouseX, mouseY)) {

			if (button == 0) {
				module.toggle();
			}
			if (button == 1) {
				this.open = !open;
			}
		}
		if(open) {
			for(PanelSettings s : settings) {
				if(!s.isVisible()) continue;
				s.onClick(mouseX, mouseY, button);
			}
		}
	}

	@Override
	public void onRelease(int mouseX, int mouseY, int button) {

		for(PanelSettings s : settings) {
			if(!s.isVisible()) continue;
			s.onRelease(mouseX, mouseY, button);
		}
		ChatUtils.debug("Released");
	}

	@Override
	public void onKey(int key, char ch) {
		// TODO Auto-generated method stub

	}

	public Mod getModule() {
		return module;
	}

	public void setModule(Mod module) {
		this.module = module;
	}

	public PositionUtils getPosition() {
		return position;
	}

	public void setPosition(double x, double y) {
		this.position.setX(x);
		this.position.setY(y);
	}

}

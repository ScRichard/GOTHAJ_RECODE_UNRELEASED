package vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings;

import java.util.ArrayList;

import vip.gothaj.client.Client;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.ModuleButton;
import vip.gothaj.client.utils.animations.Animation;
import vip.gothaj.client.utils.animations.AnimationType;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.font.icons.IconUtils;
import vip.gothaj.client.utils.render.RenderUtils;
import vip.gothaj.client.utils.scissors.ScissorUtils;
import vip.gothaj.client.utils.ui.PositionUtils;
import vip.gothaj.client.values.Value;
import vip.gothaj.client.values.settings.BooleanValue;
import vip.gothaj.client.values.settings.CategoryValue;
import vip.gothaj.client.values.settings.ColorValue;
import vip.gothaj.client.values.settings.LocationValue;
import vip.gothaj.client.values.settings.ModeValue;
import vip.gothaj.client.values.settings.MultipleBooleanValue;
import vip.gothaj.client.values.settings.NumberValue;
import vip.gothaj.client.values.settings.RangeValue;

public class PanelCategorySetting extends PanelSettings{

	private CategoryValue setting;
	
	private PositionUtils openable = new PositionUtils(0,0,290,15, 1);
	
	private ArrayList<PanelSettings> settings = new ArrayList();
	
	private Animation animation = new Animation(300, AnimationType.EaseInOutSine);
	
	private boolean open;
	double height;
	
	public PanelCategorySetting(ModuleButton parent, CategoryValue set) {
		super(parent);
		this.setting = set;
		this.position.setHeight(15);
		setVisible(set.getVisibility());
		
		for(Value s : setting.getSettings()) {
			if(s instanceof RangeValue) {
				settings.add(new PanelRangeSettings(this.parent, (RangeValue) s));
			}
			if(s instanceof NumberValue) {
				settings.add(new PanelNumberSettings(this.parent, (NumberValue) s));
			}
			if(s instanceof ModeValue) {
				settings.add(new PanelModeSettings(this.parent, (ModeValue) s));
			}
			if(s instanceof BooleanValue) {
				settings.add(new PanelBooleanSettings(this.parent, (BooleanValue) s));
			}
			if(s instanceof CategoryValue) {
				settings.add(new PanelCategorySetting(this.parent, (CategoryValue) s));
			}
			if(s instanceof LocationValue) {
				settings.add(new PanelLocationSettings(this.parent, (LocationValue) s));
			}
			if(s instanceof ColorValue) {
				settings.add(new PanelColorSettings(this.parent, (ColorValue) s));
			}
			if(s instanceof MultipleBooleanValue) {
				settings.add(new PanelMultipleBooleanSettings(this.parent, (MultipleBooleanValue) s));
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {
		openable.setX(this.position.getX());
		openable.setY(this.position.getY());
		openable.setWidth(this.position.getWidth());
		Fonts.drawString(setting.getName(), openable.getX(), openable.getY()+3, Client.INSTANCE.getThemeManager().get("Panel Text Color"), "roboto-medium");
		RenderUtils.drawRect(openable.getX()+Fonts.getWidth(setting.getName(), "roboto-medium")+10, openable.getY()+7, openable.getX2()-40, openable.getY()+8, Client.INSTANCE.getThemeManager().get("Panel Category Setting Line"));
		IconUtils.drawIcon("menu", openable.getX2()-30, openable.getY()+3, Client.INSTANCE.getThemeManager().get("Panel Text Color"), 0.7);
		
		this.animation.reversed = !open;
		height = 0;

		ScissorUtils.stencilScisor(() -> RenderUtils.drawRect(this.position, -1),
				() -> {
					if(open || (!open && !this.animation.isFinished())) {
						for(PanelSettings s : settings) {
							if(!s.isVisible()) continue;
							
							s.getPosition().setX(this.position.getX()+20);
							s.getPosition().setY(this.position.getY()+15+height);
							s.getPosition().setWidth(this.position.getWidth()-20);
							s.drawScreen(mouseX, mouseY);
							
							height += s.getPosition().getHeight();
						}
						RenderUtils.drawLine(this.position.getX()+5, this.position.getY()+20, this.position.getX()+5, this.position.getY()+10+height, Client.INSTANCE.getThemeManager().get("Panel Category Setting Line"), Client.INSTANCE.getThemeManager().get("Panel Category Setting Line"), 0.5);
					}
				}
				,
				!this.animation.isFinished() && parent.getAnimation().isFinished());
		
		this.position.setHeight(15+height*this.animation.calculateAnimation());
	}

	@Override
	public void onClick(int mouseX, int mouseY, int button) {
		if(openable.isInside(mouseX, mouseY) && button == 1) {
			open = !open;
		}
		if(open) {
			for(PanelSettings s : settings) {
				if(this.position.isInside(mouseX, mouseY)) {
					if(!s.isVisible()) continue;
					s.onClick(mouseX, mouseY, button);
				}
			}
		}
	}

	@Override
	public void onRelease(int mouseX, int mouseY, int button) {
		if(open) {
			for(PanelSettings s : settings) {
				if(!s.isVisible()) continue;
				s.onRelease(mouseX, mouseY, button);

			}
		}
	}

	@Override
	public void onKey(int key, char ch) {
		if(open) {
			for(PanelSettings s : settings) {
				if(!s.isVisible()) continue;
				s.onKey(key, ch);
			}
		}
	}
	
}

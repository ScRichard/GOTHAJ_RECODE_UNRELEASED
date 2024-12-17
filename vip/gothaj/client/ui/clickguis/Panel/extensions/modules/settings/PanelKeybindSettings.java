package vip.gothaj.client.ui.clickguis.Panel.extensions.modules.settings;

import org.lwjgl.input.Keyboard;

import vip.gothaj.client.Client;
import vip.gothaj.client.modules.Bind.Type;
import vip.gothaj.client.ui.clickguis.Panel.extensions.modules.ModuleButton;
import vip.gothaj.client.utils.buttons.SliderButton;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;

public class PanelKeybindSettings extends PanelSettings {

	private SliderButton button = new SliderButton(15, 10, false);

	public PanelKeybindSettings(ModuleButton parent) {
		super(parent);
		this.position.setHeight(20);
		button.setEnabled(
				parent.getModule().getBind() == null || parent.getModule().getBind().getType() == Type.TOGGLE);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {
		// Bind B Hold Toggle slider

		Fonts.drawString("Bind", this.position.getX(),
				this.position.getY() + 10 - Fonts.getHeight("roboto-medium") / 2,  Client.INSTANCE.getThemeManager().get("Panel Text Color"), "roboto-medium");

		String text = parent.getModule().getBind() == null ? "None"
				: (Keyboard.getKeyName(parent.getModule().getBind().getKey()).equals("NONE")
						? parent.getModule().getBind().getKey() + ""
						: Keyboard.getKeyName(parent.getModule().getBind().getKey()));

		RoundedUtils.drawRoundedRect(this.position.getX() + 23,
				this.position.getY() + 10 - Fonts.getHeight("roboto-medium") / 2 - 2,
				Fonts.getWidth(text, "roboto-medium") + 4, Fonts.getHeight("roboto-medium") + 4, Client.INSTANCE.getThemeManager().get("Panel Active Color"), 2);

		Fonts.drawString(text, this.position.getX() + 24.5,
				this.position.getY() + 10 - Fonts.getHeight("roboto-medium") / 2, Client.INSTANCE.getThemeManager().get("Panel Text Color"), "roboto-medium");

		if (parent.getModule().getBind() != null) {
			Fonts.drawString("Hold",
					this.position.getX() + this.position.getWidth() / 2 - 7.5
							- Fonts.getWidth("Hold", "roboto-bold", 0.7) - 2,
					this.position.getY() + 10 - Fonts.getHeight("roboto-bold", 0.7) / 2, Client.INSTANCE.getThemeManager().get("Panel Text Color"), "roboto-bold", 0.7);
			Fonts.drawString("Toggle", this.position.getX() + this.position.getWidth() / 2 + 7.5 + 1,
					this.position.getY() + 10 - Fonts.getHeight("roboto-bold", 0.7) / 2, Client.INSTANCE.getThemeManager().get("Panel Text Color"), "roboto-bold", 0.7);
			button.getPosition().setX(this.position.getX() + this.position.getWidth() / 2 - 7.5);
			button.getPosition().setY(this.position.getY() + 5);
			button.drawScreen(mouseX, mouseY);
		}
	}

	@Override
	public void onClick(int mouseX, int mouseY, int button) {
		String text = parent.getModule().getBind() == null ? "None"
				: (Keyboard.getKeyName(parent.getModule().getBind().getKey()).equals("NONE")
						? parent.getModule().getBind().getKey() + ""
						: Keyboard.getKeyName(parent.getModule().getBind().getKey()));
		if (this.position.getX() + 23 <= mouseX && this.position.getY() + 10 - Fonts.getHeight("roboto-medium") / 2 - 2 <= mouseY && this.position.getX() + 23+Fonts.getWidth(text, "roboto-medium") + 4 >= mouseX && this.position.getY() + 10 - Fonts.getHeight("roboto-medium") / 2+Fonts.getHeight("roboto-medium") + 4 >= mouseY) {
			this.getParent().getClickGui().binding = this;
			System.out.println("binding");
		}

		if (parent.getModule().getBind() != null) {
			this.button.onClick(mouseX, mouseY, button);
			if (this.button.isEnabled()) {
				parent.getModule().getBind().setType(Type.TOGGLE);
			} else {
				parent.getModule().getBind().setType(Type.HOLD);
			}
		}

	}

	@Override
	public void onKey(int key, char ch) {

	}

}

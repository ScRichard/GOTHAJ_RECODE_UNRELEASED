package vip.gothaj.client.modules.ext.client.hud;

import java.util.ArrayList;

import vip.gothaj.client.Client;
import vip.gothaj.client.event.EventListener;
import vip.gothaj.client.event.events.EventRenderGui;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.modules.ext.client.HudModule;
import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.render.RenderUtils;
import vip.gothaj.client.utils.resource.ResourceUtils;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.ArrayModule;
import vip.gothaj.client.values.Setting;

public class ModuleList extends Setting<HudModule> {

	private ArrayList<ArrayModule> modules = new ArrayList();

	public ModuleList(HudModule parent) {
		super(parent);
	}

	@Override
	public void onEnable() {
		super.onEnable();
		modules.clear();
		for (Mod m : Client.INSTANCE.getModuleManager().getModules()) {
			modules.add(new ArrayModule(m));
		}
		modules.sort((m1, m2) -> {
			if (getModuleWidth(m1.getModule()) < getModuleWidth(m2.getModule())) {
				return 1;
			}
			return -1;
		});
	}

	@EventListener
	public void onRenderGui(EventRenderGui e) {

		int i = 0;
		for (ArrayModule module : modules) {
			module.getAnimation().reversed = !module.getModule().isEnabled();
			if (module.getAnimation().calculateAnimation() == 0 && !module.getModule().isEnabled())
				continue;

			int o = this.getParent().verticalDirection.getActiveMode().getName().toLowerCase().equals("down") ? 1 : -1;

			module.getPosition().setWidth(getModuleWidth(module.getModule()));
			module.getPosition().setHeight(15);

			module.getPosition().setY(((module.getPosition().getY() * 9)
					+ (this.getParent().modulelistLocation.getPosY() + i * module.getPosition().getHeight() * o)) / 10);

			double x = this.getParent().horizontalDirection.getActiveMode().getName().toLowerCase().equals("left")
					? (this.getParent().modulelistLocation.getPosX() - module.getPosition().getWidth())
							+ module.getPosition().getWidth() * module.getAnimation().calculateAnimation()
					: this.getParent().modulelistLocation.getPosX()
							- module.getPosition().getWidth() * module.getAnimation().calculateAnimation();

			module.getPosition().setX(x);

			boolean rounded = true;
			if (rounded) {
				double lt = 0;
				double rt = 0;
				double rb = 0;
				double lb = 0;
				if (getSortedModules().size() != 1) {
					if (i == 0) {
						ArrayModule next = getSortedModules().get(i + 1);
						if (this.getParent().verticalDirection.getActiveMode().getName().toLowerCase().equals("down")) {
							lt = rt = 4;
						} else
							rb = lb = 4;

						double radius = (module.getPosition().getWidth() - next.getPosition().getWidth() < 4
								? module.getPosition().getWidth() - next.getPosition().getWidth()
								: 4);

						if (this.getParent().horizontalDirection.getActiveMode().getName().toLowerCase()
								.equals("left")) {
							if (this.getParent().verticalDirection.getActiveMode().getName().toLowerCase()
									.equals("up")) {
								rt = radius;
							} else
								rb = radius;
						} else {
							if (this.getParent().verticalDirection.getActiveMode().getName().toLowerCase()
									.equals("up")) {
								lt = radius;
							} else
								lb = radius;
						}
					} else if (i == getSortedModules().size() - 1) {
						if (this.getParent().verticalDirection.getActiveMode().getName().toLowerCase().equals("up")) {
							lt = rt = 4;
						} else
							rb = lb = 4;
					} else {
						ArrayModule next = getSortedModules().get(i + 1);
						double radius = (module.getPosition().getWidth() - next.getPosition().getWidth() < 4
								? module.getPosition().getWidth() - next.getPosition().getWidth()
								: 4);
						if (this.getParent().horizontalDirection.getActiveMode().getName().toLowerCase()
								.equals("left")) {
							if (this.getParent().verticalDirection.getActiveMode().getName().toLowerCase()
									.equals("up")) {
								rt = radius;
							} else
								rb = radius;
						} else {
							if (this.getParent().verticalDirection.getActiveMode().getName().toLowerCase()
									.equals("up")) {
								lt = radius;
							} else
								lb = radius;
						}

					}
				} else {
					lt = rt = rb = lb = 4;
				}

				RenderUtils.drawCustomRoundedRect(module.getPosition(), 0x90000000, lt, rt, rb, lb);
			} else {
				RenderUtils.drawRect(module.getPosition(), 0x90000000);
			}

			if (parent.outlineEnabled.isEnabled()) {
				double posY = module.getPosition().getY2();
				if (this.getParent().verticalDirection.getActiveMode().getName().toLowerCase().equals("down")) {
					posY = module.getPosition().getY();
				}
				if (i == 0) {
					if ((parent.outlineCustom.get("first top") && this.getParent().verticalDirection.getActiveMode()
							.getName().toLowerCase().equals("down"))
							|| (parent.outlineCustom.get("last bottom") && this.getParent().verticalDirection
									.getActiveMode().getName().toLowerCase().equals("up"))) {
						RenderUtils.drawLine(module.getPosition().getX(), posY, module.getPosition().getX2(), posY, -1, 1);
					}
					
				} else {

					double diff = module.getPosition().getX() - getSortedModules().get(i - 1).getPosition().getX();
					double diff2 = module.getPosition().getX2() - getSortedModules().get(i - 1).getPosition().getX2();

					if ((parent.outlineCustom.get("bottom") && this.getParent().verticalDirection.getActiveMode()
							.getName().toLowerCase().equals("down"))
							|| (parent.outlineCustom.get("top") && this.getParent().verticalDirection
									.getActiveMode().getName().toLowerCase().equals("up"))) {
						RenderUtils.drawLine(module.getPosition().getX() - diff, posY, module.getPosition().getX(), posY,
								-1, 1);

						RenderUtils.drawLine(module.getPosition().getX2() - diff2, posY, module.getPosition().getX2(), posY,
								-1, 1);
					}
					
					
				}
				if(parent.outlineCustom.get("left")) {
					RenderUtils.drawLine(module.getPosition().getX(), module.getPosition().getY(),
							module.getPosition().getX(), module.getPosition().getY2(), -1, 1);
				}
				if(parent.outlineCustom.get("right")) {
					RenderUtils.drawLine(module.getPosition().getX2(), module.getPosition().getY(),
							module.getPosition().getX2(), module.getPosition().getY2(), -1, 1);
				}
				
				posY = module.getPosition().getY();
				if (this.getParent().verticalDirection.getActiveMode().getName().toLowerCase().equals("down")) {
					posY = module.getPosition().getY2();
				}
				if (i == getSortedModules().size() - 1) {
					if ((parent.outlineCustom.get("last bottom") && this.getParent().verticalDirection.getActiveMode()
							.getName().toLowerCase().equals("down"))
							|| (parent.outlineCustom.get("first top") && this.getParent().verticalDirection
									.getActiveMode().getName().toLowerCase().equals("up"))) {
						RenderUtils.drawLine(module.getPosition().getX(), posY, module.getPosition().getX2(), posY, -1, 1);
					}
				}
			}

			String one = (parent.horizontalDirection.getActiveMode().getName().toLowerCase().equals("right")
					? module.getModule().getName()
					: module.getModule().getInfo());
			String third = (parent.horizontalDirection.getActiveMode().getName().toLowerCase().equals("left")
					? module.getModule().getName()
					: module.getModule().getInfo());

			renderString(one, module.getModule().getInfo().equals("") ? "" : " - ", third,
					module.getPosition().getX() + 1.5, module.getPosition().getY()
							+ module.getPosition().getHeight() / 2 - Fonts.getHeight("roboto-medium") / 2,
					-1, 0xffaaaaaa, 0xff777777);

			i++;
		}
	}

	public ArrayList<ArrayModule> getSortedModules() {
		ArrayList<ArrayModule> mods = new ArrayList();
		for (ArrayModule module : modules) {
			module.getAnimation().reversed = !module.getModule().isEnabled();
			if (module.getAnimation().calculateAnimation() == 0 && !module.getModule().isEnabled())
				continue;
			mods.add(module);
		}
		return mods;
	}

	public double getModuleWidth(Mod m) {
		boolean spacing = true;
		String splitter = "-";
		String size = (spacing ? m.getName() : m.getName().replace(" ", ""))
				+ (m.getInfo().equals("") ? "" : " " + splitter + " " + m.getInfo());

		return Fonts.getWidth(size, "roboto-medium") + 4;
	}

	public void renderString(String one, String two, String three, double x, double y, int colorfirst, int colorSecond,
			int colorThird) {
		if (!one.equals(""))
			Fonts.drawString(one, x, y, colorfirst, "roboto-medium");
		if (!two.equals(""))
			Fonts.drawString(two, x + Fonts.getWidth(one, "roboto-medium"), y, colorSecond, "roboto-medium");
		if (!three.equals(""))
			Fonts.drawString(three, x + Fonts.getWidth(one, "roboto-medium") + Fonts.getWidth(two, "roboto-medium"), y,
					colorThird, "roboto-medium");
	}
}

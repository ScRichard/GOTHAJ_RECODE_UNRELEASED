package vip.gothaj.client.modules.ext.client;

import vip.gothaj.client.modules.Category;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.modules.ext.client.hud.ModuleList;
import vip.gothaj.client.modules.ext.client.hud.watermarks.CSGOWatermark;
import vip.gothaj.client.modules.ext.client.hud.watermarks.LogoWatermark;
import vip.gothaj.client.modules.ext.client.hud.watermarks.ModernWatermark;
import vip.gothaj.client.utils.client.Tuple;
import vip.gothaj.client.values.Value;
import vip.gothaj.client.values.settings.BooleanValue;
import vip.gothaj.client.values.settings.CategoryValue;
import vip.gothaj.client.values.settings.LocationValue;
import vip.gothaj.client.values.settings.ModeValue;
import vip.gothaj.client.values.settings.MultipleBooleanValue;

public class HudModule extends Mod {

	private CategoryValue watermark = new CategoryValue(this, "Watermark");

	public BooleanValue watermarkEnabled = new BooleanValue(this, "Enabled", true, null);
	public LocationValue watermarkLocation = new LocationValue(this, "Location", 0, 0,
			() -> watermarkEnabled.isEnabled());

	public ModeValue watermarkModes = new ModeValue(this, "Mode",
			new Value[] { new Value(this, "Modern", new ModernWatermark(this)),
					new Value(this, "CSGO", new CSGOWatermark(this)),
					new Value(this, "Logo", new LogoWatermark(this)) },
			() -> watermarkEnabled.isEnabled());

	private CategoryValue modulelist = new CategoryValue(this, "Module List");
	public BooleanValue modulelistEnabled = new BooleanValue(this, "Enabled", true, new ModuleList(this));
	public LocationValue modulelistLocation = new LocationValue(this, "Location", 0, 0,
			() -> watermarkEnabled.isEnabled());

	private CategoryValue direction = new CategoryValue(this, "Direction");

	public ModeValue horizontalDirection = new ModeValue(this, "Horizontal",
			new Value[] { new Value(this, "Left"), new Value(this, "Right") }, () -> watermarkEnabled.isEnabled());
	public ModeValue verticalDirection = new ModeValue(this, "Vartical",
			new Value[] { new Value(this, "Up"), new Value(this, "Down") }, () -> watermarkEnabled.isEnabled());

	private CategoryValue outline = new CategoryValue(this, "Outline");
	public BooleanValue outlineEnabled = new BooleanValue(this, "Enabled", false, null);
	public MultipleBooleanValue outlineCustom = new MultipleBooleanValue(this, "Outline Customization","", new Tuple[] {
			new Tuple<String, Boolean>("First top", true),
			new Tuple<String, Boolean>("Left", true),
			new Tuple<String, Boolean>("Right", true),
			new Tuple<String, Boolean>("Top", true),
			new Tuple<String, Boolean>("Bottom", true),
			new Tuple<String, Boolean>("Last Bottom", true)
	}, () -> watermarkEnabled.isEnabled());
	public HudModule() {
		super("Hud", "Displays basic things on screen", null, Category.CLIENT);
		direction.addSettings(horizontalDirection, verticalDirection);
		outline.addSettings(outlineEnabled, outlineCustom);

		watermark.addSettings(watermarkEnabled, watermarkLocation, watermarkModes);
		modulelist.addSettings(modulelistEnabled, modulelistLocation, direction, outline);

		this.addSettings(watermark, modulelist);
	}

}

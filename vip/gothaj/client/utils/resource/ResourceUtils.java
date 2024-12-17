package vip.gothaj.client.utils.resource;

import java.util.HashMap;

import net.minecraft.util.ResourceLocation;
import vip.gothaj.client.utils.Wrapper;

public class ResourceUtils extends Wrapper {
	
	private static HashMap<String, ResourceLocation> resources = new HashMap<String, ResourceLocation>();
	
	public static void loadResources() {
		resources.put("alpha-panel", new ResourceLocation("gothaj/imgs/alpha-panel-slider.png"));
		resources.put("logo", new ResourceLocation("gothaj/imgs/gothaj.png"));
		resources.put("logo_long", new ResourceLocation("gothaj/imgs/gothaj_long.png"));
	}
	
	public static ResourceLocation getResource(String name) {
		return resources.get(name);
	}

}

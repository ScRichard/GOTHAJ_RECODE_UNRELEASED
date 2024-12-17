package vip.gothaj.client.scripts.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import vip.gothaj.client.scripts.api.world.EntityJS;
import vip.gothaj.client.utils.Wrapper;

public class WorldJS extends Wrapper {
	
	public static List<EntityJS> getAllEntities() {
		List<EntityJS> en = new ArrayList();
		for(Entity e : mc.theWorld.loadedEntityList) {
			en.add(new EntityJS(e));
		}
		return en;
	}
	
	
}

package vip.gothaj.client.modules.ext.visuals;

import java.awt.Color;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import vip.gothaj.client.Client;
import vip.gothaj.client.event.EventListener;
import vip.gothaj.client.event.events.EventRender3D;
import vip.gothaj.client.modules.Category;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.utils.render.RenderUtils;

public class ESPModule extends Mod{
	
	public ESPModule() {
		super("ESP", "Displays players behind wall", null, Category.VISUALS);
	}
	@EventListener
	public void onRender3D(EventRender3D e) {
		
	}

}

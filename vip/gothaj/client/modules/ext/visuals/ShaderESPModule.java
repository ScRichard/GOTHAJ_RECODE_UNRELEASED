package vip.gothaj.client.modules.ext.visuals;

import java.awt.Color;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import vip.gothaj.client.event.EventListener;
import vip.gothaj.client.event.events.EventRender3D;
import vip.gothaj.client.event.events.EventRenderGui;
import vip.gothaj.client.modules.Category;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.utils.render.RenderUtils;
import vip.gothaj.client.utils.shader.ShaderRenderer.ShaderType;
import vip.gothaj.client.utils.shader.impl.BloomUtils;
import vip.gothaj.client.utils.shader.impl.OutlineUtils;
import vip.gothaj.client.values.settings.ColorValue;

public class ShaderESPModule extends Mod {

	private BloomUtils bloom = new BloomUtils();
	
	private OutlineUtils outline = new OutlineUtils();
	
	private ColorValue playerColor = new ColorValue(this, "Players", 0xffff0000, 0xffAAAAAA);
	
	private ColorValue storageColor = new ColorValue(this, "Storage Color", 0xff00FF00, 0xffAAAAAA);
	
	public ShaderESPModule() {
		super("Shader ESP", "Makes outline on players and storages", null, Category.VISUALS);
		this.addSettings(playerColor, storageColor);
	}
	
	@EventListener
	public void onRender3D(EventRender3D e) {
		
		bloom.runnable.add(() -> {
			for (final Entity player : mc.theWorld.playerEntities) {
				if (mc.getRenderManager() == null || !(player instanceof EntityPlayer) || (player == mc.thePlayer && mc.gameSettings.thirdPersonView == 0) || RenderUtils.isInViewFrustrum(player)) {
					continue;
				}
				RendererLivingEntity.setShaderBrightness(new Color(0,0,255));
				mc.getRenderManager().renderEntityStatic(player, e.getPartialTicks(), false);
				RendererLivingEntity.unsetShaderBrightness();

			}
			for (final TileEntity player : mc.theWorld.loadedTileEntityList) {
				if (mc.getRenderManager() == null || !(player instanceof TileEntityChest)) {
					continue;
				}
				double x = player.getPos().getX() - mc.getRenderManager().viewerPosX;
				double y = player.getPos().getY() - mc.getRenderManager().viewerPosY;
				double z = player.getPos().getZ() - mc.getRenderManager().viewerPosZ;
				RendererLivingEntity.setShaderBrightness(new Color(0,255,255));
				TileEntityRendererDispatcher.instance.renderTileEntityAt(player,x,y,z ,e.getPartialTicks());
				RendererLivingEntity.unsetShaderBrightness();

			}

			RenderHelper.disableStandardItemLighting();
			mc.entityRenderer.disableLightmap();
	    });
		bloom.execute(ShaderType.Render3D, e.getPartialTicks());
	}
	
	@EventListener
	public void onRender2D(EventRenderGui e) {
		bloom.execute(ShaderType.Render2D, mc.timer.renderPartialTicks);
	}
	
}

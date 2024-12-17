package vip.gothaj.client.utils.scissors;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import vip.gothaj.client.utils.Wrapper;
import vip.gothaj.client.utils.render.ColorUtils;
import vip.gothaj.client.utils.render.RenderUtils;
import vip.gothaj.client.utils.render.StencilUtils;

public class ScissorUtils extends Wrapper {
	
	public static void stencilScisor(Runnable scissoredWindow, Runnable scissorDepends, boolean enabled) {
		if(enabled) {
			StencilUtils.initStencil();
			GL11.glEnable(GL11.GL_STENCIL_TEST);
			StencilUtils.bindWriteStencilBuffer();
			scissoredWindow.run();
			StencilUtils.bindReadStencilBuffer(1);
		}
		
		scissorDepends.run();
		
		if(enabled) StencilUtils.uninitStencilBuffer();
	}
	
}

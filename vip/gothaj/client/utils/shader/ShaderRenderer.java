package vip.gothaj.client.utils.shader;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import vip.gothaj.client.utils.Wrapper;

public abstract class ShaderRenderer extends Wrapper{
	
	public List<Runnable> runnable = new ArrayList();
	
	public abstract void execute(ShaderType type, float partialTicks);
	
	public abstract void reset();
	
	public enum ShaderType{
		Render3D,
		Render2D
	}
	
}

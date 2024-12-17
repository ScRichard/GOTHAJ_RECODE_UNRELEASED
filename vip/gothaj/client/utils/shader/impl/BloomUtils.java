package vip.gothaj.client.utils.shader.impl;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import vip.gothaj.client.utils.client.Kernel;
import vip.gothaj.client.utils.shader.Shader;
import vip.gothaj.client.utils.shader.ShaderRenderer;
import vip.gothaj.client.utils.shader.ShaderRenderer.ShaderType;

public class BloomUtils extends ShaderRenderer {

	private Framebuffer inputFramebuffer = new Framebuffer(1, 1, true);
    private Framebuffer outputFramebuffer = new Framebuffer(1, 1, true);
    
    private Shader shader = new Shader(new ResourceLocation("gothaj/shaders/bloom.frag"));
    
    private float radius = 6, lastRadius = 5, copression;
    
    private FloatBuffer kernel;
    
    private ScaledResolution sr;
	
	@Override
	public void execute(ShaderType type, float partialTicks) {
		if(type == ShaderType.Render3D) {
			reset();
			if(runnable.isEmpty()) {
				return;
			}
			inputFramebuffer.bindFramebuffer(true);
			
			for(Runnable r : runnable) {
				r.run();
			}
			
			inputFramebuffer.unbindFramebuffer();
			mc.getFramebuffer().bindFramebuffer(true);
			
		} else {
			sr = new ScaledResolution(mc);
			if(runnable.isEmpty()) {
				reset();
				return;
			}
			
			this.inputFramebuffer.bindFramebuffer(true);
			
			for(Runnable r : runnable) {
				r.run();
			}
			
			this.outputFramebuffer.bindFramebuffer(true);
			shader.startProgram();
			
			if(radius != lastRadius) {
				kernel = BufferUtils.createFloatBuffer(256);
		        for (int i = 0; i <= radius; i++) {
		            kernel.put(Kernel.calculateGaussianValue(i, (float) (radius / 2)));
		        }

		        kernel.rewind();
			}
			lastRadius = radius;

			shader.uniform1f("u_saturation", 2f);
	        shader.uniform1f("u_radius", radius);
	        shader.uniformFB("u_kernel", kernel);
	        shader.uniform1i("u_texture1", 0);
	        shader.uniform1i("u_texture2", 20);

	        shader.uniform2f("u_texel_size", 1.0F / sr.getScaledWidth(), 1.0F / sr.getScaledHeight());
	        shader.uniform2f("u_direction", (float) 1, 0.0F);

	        GlStateManager.enableBlend();
	        GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_SRC_ALPHA);
	        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
	        inputFramebuffer.bindFramebufferTexture();
	        shader.renderShader(0, 0, sr.getScaledWidth(), sr.getScaledHeight());

	        mc.getFramebuffer().bindFramebuffer(true);
	        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        shader.uniform2f("u_direction", 0.0F, (float) 1);
	        outputFramebuffer.bindFramebufferTexture();
	        GL13.glActiveTexture(GL13.GL_TEXTURE20);
	        inputFramebuffer.bindFramebufferTexture();
	        GL13.glActiveTexture(GL13.GL_TEXTURE0);
	        shader.renderShader(0, 0, sr.getScaledWidth(), sr.getScaledHeight());
	        GlStateManager.disableBlend();

	        shader.stopProgram();
	        runnable.clear();
		}
	}

	@Override
	public void reset() {
		sr = new ScaledResolution(mc);
    	if (mc.displayWidth != inputFramebuffer.framebufferWidth || mc.displayHeight != inputFramebuffer.framebufferHeight) {
            inputFramebuffer.deleteFramebuffer();
            inputFramebuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, true);

            outputFramebuffer.deleteFramebuffer();
            outputFramebuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, true);
        } else {
            inputFramebuffer.framebufferClear();
            outputFramebuffer.framebufferClear();
        }

        inputFramebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
        outputFramebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
	}
	
}

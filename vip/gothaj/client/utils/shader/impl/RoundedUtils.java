package vip.gothaj.client.utils.shader.impl;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import vip.gothaj.client.utils.render.RenderUtils;
import vip.gothaj.client.utils.shader.Shader;
import vip.gothaj.client.utils.ui.PositionUtils;


public class RoundedUtils {

	private static Shader roundedRect = new Shader(new ResourceLocation("gothaj/shaders/roundedRect.frag"));
	private static Shader roundedTexture = new Shader(new ResourceLocation("gothaj/shaders/roundedTexture.frag"));
	private static Shader roundedOutlinedRect = new Shader(new ResourceLocation("gothaj/shaders/roundedOutlinedRect.frag"));
	private static Shader roundedRectCorners = new Shader(new ResourceLocation("gothaj/shaders/roundedRectWithCorners.frag"));
	
	public static void drawRoundedRect(PositionUtils position, int color, float rounding) {
		drawRoundedRect(position.getX(), position.getY(), position.getWidth(), position.getHeight(), color, rounding);
	}
	
	public static void drawRoundedRect(PositionUtils position, int color, float rLT,float rRT,float rRB,float rLB) {
		drawRoundedRect(position.getX(), position.getY(), position.getWidth(), position.getHeight(), color, rLT, rRT, rRB, rLB);
	}
	
	public static void drawRoundedRect(double x, double y, double width, double height, int color, float rounding) {
		float f3 = (float) (color >> 24 & 255) / 255.0F;
		float f = (float) (color >> 16 & 255) / 255.0F;
		float f1 = (float) (color >> 8 & 255) / 255.0F;
		float f2 = (float) (color & 255) / 255.0F;

		GlStateManager.enableBlend();
		roundedRect.startProgram();

		roundedRect.uniform1f("radius", rounding);
		roundedRect.uniform2f("size", (float)width, (float)height);
		roundedRect.uniform4f("color", f, f1, f2, f3);
		
		int textureID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

		roundedRect.renderShader(x, y, (float)width, (float)height);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

		roundedRect.stopProgram();
		GlStateManager.disableBlend();
	}
	
	public static void drawRoundedImage(ResourceLocation resource ,double x, double y, double width, double height, float rounding) {
		GlStateManager.enableBlend();
		roundedRectCorners.startProgram();
		
		roundedRectCorners.uniform1i("texture", 0);
		roundedRectCorners.uniform1f("radius", rounding);
		roundedRectCorners.uniform2f("size", (float)width, (float)height);
		
		int textureID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

		RenderUtils.drawImage(x, y, width, height, resource, -1);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

		roundedRectCorners.stopProgram();
		GlStateManager.disableBlend();
	}
	
	public static void drawRoundedRect(double x, double y, double width, double height, int color, float rLT,float rRT,float rRB,float rLB) {
		float f3 = (float) (color >> 24 & 255) / 255.0F;
		float f = (float) (color >> 16 & 255) / 255.0F;
		float f1 = (float) (color >> 8 & 255) / 255.0F;
		float f2 = (float) (color & 255) / 255.0F;

		GlStateManager.enableBlend();
		roundedRectCorners.startProgram();

		roundedRectCorners.uniform4f("radius", rLB, rLT,rRB, rRT);
		roundedRectCorners.uniform2f("size", (float)width, (float)height);
		roundedRectCorners.uniform4f("color", f, f1, f2, f3);
		
		int textureID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

		roundedRectCorners.renderShader(x, y, (float)width, (float)height);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

		roundedRectCorners.stopProgram();
		GlStateManager.disableBlend();
	}
	
	public static void drawRoundedOutlinedRect(double x, double y, double width, double height, int color, float rounding, float outlineSize) {
		float f3 = (float) (color >> 24 & 255) / 255.0F;
		float f = (float) (color >> 16 & 255) / 255.0F;
		float f1 = (float) (color >> 8 & 255) / 255.0F;
		float f2 = (float) (color & 255) / 255.0F;

		GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        roundedOutlinedRect.startProgram();

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        
        roundedOutlinedRect.uniform1f("outlineThickness", outlineSize);
        roundedOutlinedRect.uniform2f("location", (float) x * sr.getScaleFactor(),
                (float) ((Minecraft.getMinecraft().displayHeight - (height * sr.getScaleFactor())) - (y * sr.getScaleFactor())));
        roundedOutlinedRect.uniform2f("rectSize",(float)width * sr.getScaleFactor(), (float)height * sr.getScaleFactor());
        roundedOutlinedRect.uniform1f("radius", rounding*2);
        roundedOutlinedRect.uniform4f("outlineColor", f, f1, f2, f3);

        roundedOutlinedRect.renderShader(x - (1 + outlineSize), y - (1 + outlineSize), width + (2 + outlineSize), height + (2 + outlineSize));
        roundedOutlinedRect.stopProgram();
        GlStateManager.disableBlend();
	}
	
}

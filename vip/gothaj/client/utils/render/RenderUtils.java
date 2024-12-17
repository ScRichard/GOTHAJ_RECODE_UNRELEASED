package vip.gothaj.client.utils.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import vip.gothaj.client.utils.shader.Shader;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.PositionUtils;

public class RenderUtils {
	private static Shader colorPicker = new Shader(new ResourceLocation("gothaj/shaders/colorPicker.frag"));
	private static Shader hueSlider = new Shader(new ResourceLocation("gothaj/shaders/hueSlider.frag"));

	public static void start2D() {
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableTexture2D();
		GlStateManager.disableCull();
		GlStateManager.disableAlpha();
		GlStateManager.disableDepth();
	}
	
	public static void stop2D() {
		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.enableCull();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GlStateManager.resetColor();
	}
	
	public static void stop3D() {
		GlStateManager.enableCull();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public static void start3D() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);

		GL11.glDepthMask(false);
		GlStateManager.disableCull();
	}
	public static void drawImage(double x, double y, double width, double height, ResourceLocation image, int color) {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GlStateManager.enableBlend();
		GL11.glDepthMask(false);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		RenderUtils.color(color);
		Minecraft.getMinecraft().getTextureManager().bindTexture(image);
		Gui.drawModalRectWithCustomSizedTexture((float) x, (float) y, 0, 0, width, height, width, height);
		GlStateManager.resetColor();
		GL11.glDepthMask(true);
		GlStateManager.disableBlend();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	public static void drawRect(double x, double y, double x1, double y1, int color) {
		
		if(x > x1) {
			double j = x;
			x = x1;
			x1 = j;
		}
		if(y > y1) {
			double j = y;
			y = y1;
			y1 = j;
		}
		start2D();
		
		RenderUtils.color(color);
		
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x1, y);
		GL11.glVertex2d(x1, y1);
		GL11.glVertex2d(x, y1);
		
		GL11.glEnd();
		
		stop2D();
	}
	public static void drawOutlinedRect(PositionUtils location, int i, int j) {
		drawOutlinedRect(location.getX(), location.getY(), location.getX2(), location.getY2(), i, j);
	}
	public static void drawOutlinedRect(double x, double y, double x1, double y1, int color, double outlineDepth) {
		
		if(x > x1) {
			double j = x;
			x = x1;
			x1 = j;
		}
		if(y > y1) {
			double j = y;
			y = y1;
			y1 = j;
		}
		start2D();
		
		RenderUtils.color(color);
		GL11.glLineWidth((float) outlineDepth);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x1, y);
		GL11.glVertex2d(x1, y1);
		GL11.glVertex2d(x, y1);
		
		GL11.glEnd();
		
		stop2D();
	}
	
	public static void drawRect(PositionUtils position, int color) {
		
		start2D();
		
		RenderUtils.color(color);
		
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glVertex2d(position.getX(), position.getY());
		GL11.glVertex2d(position.getX()+position.getWidth(), position.getY());
		GL11.glVertex2d(position.getX()+position.getWidth(), position.getY()+ position.getHeight());
		GL11.glVertex2d(position.getX(), position.getY()+ position.getHeight());
		
		GL11.glEnd();
		
		stop2D();
	}
	
	public static void drawCustomRect(double x, double y, double x1, double y1, int color, boolean rounded, double rounding) {
		if(rounded) {
			RoundedUtils.drawRoundedRect(x, y, x1-x, y1-y, color, (float) rounding);
		}else {
			drawRect(x,y,x1,y1,color);
		}
	}
	public static void drawCustomRect(PositionUtils position, int color, boolean rounded, double rounding) {
		if(rounded) {
			RoundedUtils.drawRoundedRect(position, color, (float) rounding);
		}else {
			drawRect(position,color);
		}
	}
	public static void drawCustomRoundedRect(double x, double y, double x1, double y1, int color, double roundingLT, double roundingRT, double roundingRB, double roundingLB) {
		if(x > x1) {
			double j = x;
			x = x1;
			x1 = j;
		}
		if(y > y1) {
			double j = y;
			y = y1;
			y1 = j;
		}
		start2D();
		
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		color(color);
		//LT
		for(double i = 270 ; i < 360; i+=1) {
			GL11.glVertex2d(x+roundingLT+Math.sin(i * Math.PI / 180) * roundingLT, y+roundingLT-Math.cos(i * Math.PI / 180)* roundingLT);
		}
		for(double i = 0 ; i < 90; i+=1) {
			GL11.glVertex2d(x1-roundingRT+Math.sin(i * Math.PI / 180) * roundingRT, y+roundingRT-Math.cos(i * Math.PI / 180)* roundingRT);
		}
		for(double i = 90 ; i < 180; i+=1) {
			GL11.glVertex2d(x1-roundingRB+Math.sin(i * Math.PI / 180) * roundingRB, y1-roundingRB-Math.cos(i * Math.PI / 180)* roundingRB);
		}
		for(double i = 180 ; i < 270; i+=1) {
			GL11.glVertex2d(x+roundingLB+Math.sin(i * Math.PI / 180) * roundingLB, y1-roundingLB-Math.cos(i * Math.PI / 180)* roundingLB);
		}
		GL11.glEnd();
		
		stop2D();
	}
	
	public static void drawGradientRect(double x, double y, double x1, double y1, int colorLeftT,int colorRightT,int colorRightB,int colorLeftB) {
		
		if(x > x1) {
			double j = x;
			x = x1;
			x1 = j;
		}
		if(y > y1) {
			double j = y;
			y = y1;
			y1 = j;
		}
		start2D();
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glBegin(GL11.GL_QUADS);
		RenderUtils.color(colorLeftT);
		GL11.glVertex2d(x, y);
		RenderUtils.color(colorRightT);
		GL11.glVertex2d(x1, y);
		RenderUtils.color(colorRightB);
		GL11.glVertex2d(x1, y1);
		RenderUtils.color(colorLeftB);
		GL11.glVertex2d(x, y1);
		
		GL11.glEnd();
		
		stop2D();
	}
	public static void drawColorPicker(double x, double y, double width, double height, float hue) {

		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		colorPicker.startProgram();
		colorPicker.uniform2f("u_resolution", (float) width, (float) height);
		colorPicker.uniform1f("hue", (float) hue);


		colorPicker.renderShader(x, y, width, height);

		colorPicker.stopProgram();
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	public static void drawColorSlider(double x, double y, double width, double height) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		hueSlider.startProgram();
		hueSlider.uniform2f("u_resolution", (float) width, (float) height);

		hueSlider.renderShader(x, y, width, height);

		hueSlider.stopProgram();
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}
	
	public static void drawColorPicker(PositionUtils color1, float hue1) {
		drawColorPicker(color1.getX(),color1.getY(),color1.getWidth(),color1.getHeight(), hue1);
	}
	public static void drawColorSlider(PositionUtils color1) {
		drawColorSlider(color1.getX(),color1.getY(),color1.getWidth(),color1.getHeight());
	}

	
	public static void drawLine(double x, double y, double x1, double y1, int endC, int midC, double size) {
		if(x > x1) {
			double j = x;
			x = x1;
			x1 = j;
		}
		if(y > y1) {
			double j = y;
			y = y1;
			y1 = j;
		}
		start2D();
		GL11.glLineWidth((float) size);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		
		
		color(endC);
		GL11.glVertex2d(x, y);
		color(midC);
		GL11.glVertex2d(x+(x1-x)/2, y+(y1-y)/2);
		color(endC);
		GL11.glVertex2d(x1, y1);
		
		GL11.glEnd();
		stop2D();
	}
	
	public static void drawLine(double x, double y, double x1, double y1, int endC, double size) {
		if(x > x1) {
			double j = x;
			x = x1;
			x1 = j;
		}
		if(y > y1) {
			double j = y;
			y = y1;
			y1 = j;
		}
		start2D();
		GL11.glLineWidth((float) size);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		
		color(endC);
		GL11.glVertex2d(x, y);
		color(endC);
		GL11.glVertex2d(x1, y1);
		
		GL11.glEnd();
		stop2D();
	}
	
	public static void drawLocationBox(double x, double y, double x1, double y1, int k, int l) {
		if(x > x1) {
			double j = x;
			x = x1;
			x1 = j;
		}
		if(y > y1) {
			double j = y;
			y = y1;
			y1 = j;
		}
		double size = x1-x;
		double widthBox = size / 9;
		double heightBox = size / 6;
		GL11.glPushMatrix();
		for(int i = 0; i < 9 ; i++) {
			drawLine(x+widthBox/2+i*widthBox, y, x+widthBox/2+i*widthBox, y1, k, l, 2);
		}
		for(int i = 0; i < 6 ; i++) {
			drawLine(x, y+widthBox/2+i*widthBox, x1, y+widthBox/2+i*widthBox, k, l, 2);
		}
		GL11.glPopMatrix();
		
	}
	
	public static void color(int color) {
		float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;

        GlStateManager.color(f, f1, f2, f3);
	}
	
	//Scisor
	public static void enableScisor() {
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
	}

	public static void disableScisor() {
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}

	public static void scissor(ScaledResolution scaledResolution, double x, double y, double width, double height) {
		final int scaleFactor = scaledResolution.getScaleFactor();
		GL11.glScissor((int) Math.round(x * scaleFactor),
				(int) Math.round((scaledResolution.getScaledHeight() - (y + height)) * scaleFactor),
				(int) Math.round(width * scaleFactor), (int) Math.round(height * scaleFactor));
	}

	public static void drawCustomRoundedRect(PositionUtils position, int color, double roundingLT, double roundingRT, double roundingRB, double roundingLB) {
		drawCustomRoundedRect(position.getX(), position.getY(), position.getX2(), position.getY2(), color, roundingLT, roundingRT, roundingRB, roundingLB);
		
	}

	public static boolean isInViewFrustrum(Entity player) {

		return false;
	}

}

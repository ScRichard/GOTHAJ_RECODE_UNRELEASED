package vip.gothaj.client.utils.shader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import net.minecraft.util.ResourceLocation;
import vip.gothaj.client.utils.Wrapper;

public class Shader extends Wrapper{

	private int programId;

    private int program;
    private final String vertexLoc = "#version 120\n" +
            "\n" +
            "void main() {\n" +
            "    gl_TexCoord[0] = gl_MultiTexCoord0;\n" +
            "    gl_Position = gl_ModelViewProjectionMatrix*gl_Vertex;\n" +
            "}";

    public Shader(ResourceLocation location) {
    	String s;
    	program = 0;
    	this.programId = 0;
		try {
			s = readShader(mc.getResourceManager().getResource(location).getInputStream());
			program = GL20.glCreateProgram();
	        GL20.glAttachShader(this.program, createShader(s, GL20.GL_FRAGMENT_SHADER));
	        GL20.glAttachShader(this.program, createShader(vertexLoc, GL20.GL_VERTEX_SHADER));
	        GL20.glLinkProgram(program);
	        this.programId = program;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }
    
    public Shader(String fragmentShaderLoc) {
        program = GL20.glCreateProgram();
        GL20.glAttachShader(this.program, createShader(fragmentShaderLoc, GL20.GL_FRAGMENT_SHADER));
        GL20.glAttachShader(this.program, createShader(vertexLoc, GL20.GL_VERTEX_SHADER));
        GL20.glLinkProgram(program);
        this.programId = program;
    }

    public void startProgram() {
    	GL20.glUseProgram(programId);
    }

    public void uniformFB(final String name, final FloatBuffer floatBuffer) {
        GL20.glUniform1(getLocation( name), floatBuffer);
    }

    public void uniform1i(final String name, final int i) {
        GL20.glUniform1i(getLocation( name), i);
    }

    public void uniform2i( final String name, final int i, final int j) {
        GL20.glUniform2i(getLocation( name), i, j);
    }

    public void uniform1f( final String name, final float f) {
        GL20.glUniform1f(getLocation( name), f);
    }

    public void uniform2f( final String name, final float f, final float g) {
        GL20.glUniform2f(getLocation( name), f, g);
    }

    public void uniform3f( final String name, final float f, final float g, final float h) {
        GL20.glUniform3f(getLocation( name), f, g, h);
    }

    public void uniform4f( final String name, final float f, final float g, final float h, final float i) {
        GL20.glUniform4f(getLocation( name), f, g, h, i);
    }

    private int getLocation( final String name) {
    	return GL20.glGetUniformLocation(programId, name);
    }
    
    public int getProgramId() {
        return programId;
    }

    public void stopProgram() {
    	GL20.glUseProgram(0);
    }
    public void renderShader(double x,double y , double width, double height) {
    	GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2d(x, y);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2d(x, y+height);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2d(x+width, y+height);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2d(x+width, y);
        GL11.glEnd();
    }
    private static String readShader(final InputStream inputStream)
    {
        final StringBuilder stringBuilder = new StringBuilder();

        try
        {
            final InputStreamReader inputReader = new InputStreamReader(inputStream);
            final BufferedReader bufferedReader = new BufferedReader(inputReader);
            String line;

            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line).append('\n');
            }
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
    
    private int createShader(String oskar, int shaderType) {
    	final int shader;
    	shader = GL20.glCreateShader(shaderType);
    	GL20.glShaderSource(shader, oskar);
    	GL20.glCompileShader(shader);
    	System.out.println(GL20.glGetShaderInfoLog(shader, 1024));
    	return shader;
    }
}

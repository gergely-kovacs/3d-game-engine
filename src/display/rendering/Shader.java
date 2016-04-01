package display.rendering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Shader {

	private int shaderId;
	
	public Shader(String filePath, int type) {
		StringBuilder shaderSource = new StringBuilder();
         
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Could not read file.");
            e.printStackTrace();
            System.exit(-1);
        }
         
        shaderId = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderId, shaderSource);
        GL20.glCompileShader(shaderId);
        
        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println("Could not compile shader: " + filePath + ".");
        }
	}

	public int getShaderId() {
		return shaderId;
	}
	
	public void dispose() {
		GL20.glDeleteShader(shaderId);
	}
}

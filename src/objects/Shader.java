package objects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL20;

public class Shader {

	private int shaderId;
	
	public Shader(String filename, int type) {
		StringBuilder shaderSource = new StringBuilder();
         
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file.");
            e.printStackTrace();
            System.exit(-1);
        }
         
        shaderId = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderId, shaderSource);
        GL20.glCompileShader(shaderId);
	}

	public int getShaderId() {
		return shaderId;
	}
	
	public void dispose() {
		GL20.glDeleteShader(shaderId);
	}
}

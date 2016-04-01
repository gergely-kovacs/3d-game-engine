package display.rendering;

import java.util.ArrayList;

import org.lwjgl.opengl.GL20;

public class ShaderProgram {
	private int pId, vsID, fsID;
	
	public ShaderProgram(int vsID, int fsID, ArrayList<String> attribs) {
		 pId = GL20.glCreateProgram();
		 
		 GL20.glAttachShader(pId, vsID);
         GL20.glAttachShader(pId, fsID);
         
         bindAttribs(attribs);
         
         GL20.glLinkProgram(pId);
         GL20.glValidateProgram(pId);
         
         this.vsID = vsID;
         this.fsID = fsID;
	}
	
	private void bindAttribs(ArrayList<String> attribs) {
		for (int i = 0; i < attribs.size(); i++) {
			GL20.glBindAttribLocation(pId, i, attribs.get(i));
		}
	}
	
	public void dispose() {
		GL20.glUseProgram(0);
		
    	GL20.glDetachShader(pId, vsID);
    	GL20.glDetachShader(pId, fsID);
    	
    	GL20.glDeleteProgram(pId);
	}

	public int getProgramId() {
		return pId;
	}
}

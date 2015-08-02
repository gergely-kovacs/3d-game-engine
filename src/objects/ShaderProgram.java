package objects;

import java.util.ArrayList;

import org.lwjgl.opengl.GL20;

public class ShaderProgram {
	private int pID, vsID, fsID;
	
	public ShaderProgram(int vsID, int fsID, ArrayList<String> attribs) {
		 pID = GL20.glCreateProgram();
		 
		 GL20.glAttachShader(pID, vsID);
         GL20.glAttachShader(pID, fsID);
         
         bindAttribs(attribs);
         
         GL20.glLinkProgram(pID);
         GL20.glValidateProgram(pID);
         
         this.vsID = vsID;
         this.fsID = fsID;
	}
	
	public void bindAttribs(ArrayList<String> attribs) {
		for (int i = 0; i < attribs.size(); i++) {
			GL20.glBindAttribLocation(pID, i, attribs.get(i));
		}
	}
	
	public void dispose() {
		GL20.glUseProgram(0);
    	GL20.glDetachShader(pID, vsID);
    	GL20.glDetachShader(pID, fsID);
    	 
    	GL20.glDeleteShader(vsID);
    	GL20.glDeleteShader(fsID);
    	GL20.glDeleteProgram(pID);
	}

	public int getpID() {
		return pID;
	}
}

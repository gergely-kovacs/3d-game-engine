package objects;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class CubemapTexture {
	
	private int texId;

	public CubemapTexture(String[] filename, int textureUnit) {
		texId = GL11.glGenTextures();
        GL13.glActiveTexture(textureUnit);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texId);
        
		for (int i = 0; i < 6; i++) {
			ByteBuffer buf = null;
	        int tWidth = 0;
	        int tHeight = 0;
	         
	        try {
	            InputStream in = new FileInputStream(filename[i]);
	            
	            PNGDecoder decoder = new PNGDecoder(in);
	             
	            tWidth = decoder.getWidth();
	            tHeight = decoder.getHeight();
	             
	            buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
	            decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
	            buf.flip();
	             
	            in.close();
	            
	            GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA, tWidth, tHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.exit(-1);
	        }
		}
		
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, 0);
	}

	public int getId() {
		return texId;
	}
}

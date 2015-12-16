package objects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import util.maths.Vec2f;
import util.maths.Vec3f;

public class Model {
	private int vaoId, vboPosId, vboTexId, vboNormId, vertexCount;
	
	public Model(String filename) {
		List<Vec3f> vertices = new ArrayList<Vec3f>();
		List<Vec2f> textures = new ArrayList<Vec2f>();
		List<Vec3f> normals = new ArrayList<Vec3f>();
		List<Face> faces = new ArrayList<Face>();
		BufferedReader br;
		String line;
		try {
			br = new BufferedReader(new FileReader(filename));
			while ((line = br.readLine()) != null) {
				
				if(line.startsWith("v ")) {
					vertices.add(new Vec3f(
						Float.parseFloat(line.split(" ")[1]),
						Float.parseFloat(line.split(" ")[2]),
						Float.parseFloat(line.split(" ")[3])));
				}
				
				else if (line.startsWith("vt ")) {
					textures.add(new Vec2f(
						Float.parseFloat(line.split(" ")[1]),
						1.0f - Float.parseFloat(line.split(" ")[2])));
				}
				
				else if (line.startsWith("vn ")) {
					normals.add(new Vec3f(
						Float.parseFloat(line.split(" ")[1]),
						Float.parseFloat(line.split(" ")[2]),
						Float.parseFloat(line.split(" ")[3])));
				}
				
				else if (line.startsWith("f ")) {
					faces.add(new Face(
						new Vec3f(
							Float.parseFloat(line.split(" ")[1].split("/")[0]),
							Float.parseFloat(line.split(" ")[2].split("/")[0]),
							Float.parseFloat(line.split(" ")[3].split("/")[0])),
					
						new Vec3f(
							Float.parseFloat(line.split(" ")[1].split("/")[1]),
							Float.parseFloat(line.split(" ")[2].split("/")[1]),
							Float.parseFloat(line.split(" ")[3].split("/")[1])),
					
						new Vec3f(
							Float.parseFloat(line.split(" ")[1].split("/")[2]),
							Float.parseFloat(line.split(" ")[2].split("/")[2]),
							Float.parseFloat(line.split(" ")[3].split("/")[2]))));
				}
				
				else continue;
			}
			
			br.close();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		} 
		
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(faces.size() * 9);
		FloatBuffer textureBuffer = BufferUtils.createFloatBuffer(faces.size() * 6);
		FloatBuffer normalBuffer = BufferUtils.createFloatBuffer(faces.size() * 9);
		
    	for (int i = 0; i < faces.size(); i++) {
    		for (int j = 0; j < 3; j++) {
    			if (j == 0) {
		    		vertexBuffer.put(vertices.get((int) faces.get(i).vertexIndices.x - 1).x);
		    		vertexBuffer.put(vertices.get((int) faces.get(i).vertexIndices.x - 1).y);
		    		vertexBuffer.put(vertices.get((int) faces.get(i).vertexIndices.x - 1).z);
		    		textureBuffer.put(textures.get((int) faces.get(i).textureIndices.x - 1).x);
		    		textureBuffer.put(textures.get((int) faces.get(i).textureIndices.x - 1).y);
		    		normalBuffer.put(normals.get((int) faces.get(i).normalIndices.x - 1).x);
		    		normalBuffer.put(normals.get((int) faces.get(i).normalIndices.x - 1).y);
		    		normalBuffer.put(normals.get((int) faces.get(i).normalIndices.x - 1).z);
    			}
    			
    			else if (j == 1) {
		    		vertexBuffer.put(vertices.get((int) faces.get(i).vertexIndices.y - 1).x);
		    		vertexBuffer.put(vertices.get((int) faces.get(i).vertexIndices.y - 1).y);
		    		vertexBuffer.put(vertices.get((int) faces.get(i).vertexIndices.y - 1).z);
		    		textureBuffer.put(textures.get((int) faces.get(i).textureIndices.y - 1).x);
		    		textureBuffer.put(textures.get((int) faces.get(i).textureIndices.y - 1).y);
		    		normalBuffer.put(normals.get((int) faces.get(i).normalIndices.y - 1).x);
		    		normalBuffer.put(normals.get((int) faces.get(i).normalIndices.y - 1).y);
		    		normalBuffer.put(normals.get((int) faces.get(i).normalIndices.y - 1).z);
    			}
    			
    			else if (j == 2) {
		    		vertexBuffer.put(vertices.get((int) faces.get(i).vertexIndices.z - 1).x);
		    		vertexBuffer.put(vertices.get((int) faces.get(i).vertexIndices.z - 1).y);
		    		vertexBuffer.put(vertices.get((int) faces.get(i).vertexIndices.z - 1).z);
		    		textureBuffer.put(textures.get((int) faces.get(i).textureIndices.z - 1).x);
		    		textureBuffer.put(textures.get((int) faces.get(i).textureIndices.z - 1).y);
		    		normalBuffer.put(normals.get((int) faces.get(i).normalIndices.z - 1).x);
		    		normalBuffer.put(normals.get((int) faces.get(i).normalIndices.z - 1).y);
		    		normalBuffer.put(normals.get((int) faces.get(i).normalIndices.z - 1).z);
    			}
    		}
    	} vertexBuffer.flip(); textureBuffer.flip(); normalBuffer.flip();
    	
    	vertexCount = faces.size() * 3;
		
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);
        
        vboPosId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboPosId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        vboTexId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboTexId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        vboNormId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboNormId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        GL30.glBindVertexArray(0);
	}
	
	public void dispose() {
		GL30.glBindVertexArray(vaoId);
    	
    	GL20.glDisableVertexAttribArray(0);
    	GL20.glDisableVertexAttribArray(1);
    	GL20.glDisableVertexAttribArray(2);
    	
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    	GL15.glDeleteBuffers(vboPosId);
    	GL15.glDeleteBuffers(vboTexId);
    	GL15.glDeleteBuffers(vboNormId);
    	
    	GL30.glBindVertexArray(0);
    	GL30.glDeleteVertexArrays(vaoId);
	}

	public int getVaoId() {
		return vaoId;
	}

	public int getVboPosId() {
		return vboPosId;
	}

	public int getVboTexId() {
		return vboTexId;
	}

	public int getVboNormId() {
		return vboNormId;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
	
}

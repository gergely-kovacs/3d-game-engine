package objects;

import static org.lwjgl.opengl.GL11.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import utils.maths.Vec2f;
import utils.maths.Vec3f;

public class Model {
	
	public List<Vec3f> vertices = new ArrayList<Vec3f>();
	public List<Vec3f> normals = new ArrayList<Vec3f>();
	public List<Vec2f> textures = new ArrayList<Vec2f>();
	public List<Face> faces = new ArrayList<Face>();
	
	private Vec3f position, angle, scale;
	
	private float shineDamper, reflectivity;
	
	private int vaoID, vboVertID, vboTexID, vboNormID, vboIndID;
	
	public Model(String filename, Vec3f position, Vec3f angle, Vec3f scale, float shineDamper, float reflectivity) {
		BufferedReader bf;
		String line;
		try {
			bf = new BufferedReader(new FileReader(filename));
			while ((line = bf.readLine()) != null) {
				
				if(line.startsWith("v ")) {
					Vec3f vertex = new Vec3f(
						Float.valueOf(line.split(" ")[1]),
						Float.valueOf(line.split(" ")[2]),
						Float.valueOf(line.split(" ")[3]));
					vertices.add(vertex);
				}
				
				else if (line.startsWith("vt ")) {
					Vec2f texture = new Vec2f(
						Float.valueOf(line.split(" ")[1]),
						Float.valueOf(line.split(" ")[2]));
					textures.add(texture);
				}
				
				else if (line.startsWith("vn ")) {
					Vec3f normal = new Vec3f(
						Float.valueOf(line.split(" ")[1]),
						Float.valueOf(line.split(" ")[2]),
						Float.valueOf(line.split(" ")[3]));
					normals.add(normal);
				}
				
				else if (line.startsWith("f ")) {
					Vec3f vertexIndices = new Vec3f(
						Float.valueOf(line.split(" ")[1].split("/")[0]),
						Float.valueOf(line.split(" ")[2].split("/")[0]),
						Float.valueOf(line.split(" ")[3].split("/")[0]));
					
					Vec3f normalIndices = new Vec3f(
						Float.valueOf(line.split(" ")[1].split("/")[2]),
						Float.valueOf(line.split(" ")[2].split("/")[2]),
						Float.valueOf(line.split(" ")[3].split("/")[2]));
					
					Vec3f textureIndices = new Vec3f(
						Float.valueOf(line.split(" ")[1].split("/")[1]),
						Float.valueOf(line.split(" ")[2].split("/")[1]),
						Float.valueOf(line.split(" ")[3].split("/")[1]));
					
					faces.add(new Face(vertexIndices, normalIndices, textureIndices));
				}
				
				else continue;
			}
			bf.close();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		} 
		
		loadToBuffers();
		
		this.position = position;
		this.angle = angle;
		this.scale = scale;
		
		this.shineDamper = shineDamper;
		this.reflectivity = reflectivity;
	}
	
	public void loadToBuffers() {
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.size() * 3);
    	for (int i = 0; i < vertices.size(); i ++) {
    		vertexBuffer.put(vertices.get(i).x);
    		vertexBuffer.put(vertices.get(i).y);
    		vertexBuffer.put(vertices.get(i).z);
    	} vertexBuffer.flip();
    	
    	FloatBuffer normalBuffer = BufferUtils.createFloatBuffer(normals.size() * 3);
    	for (int i = 0; i < normals.size(); i ++) {
    		normalBuffer.put(normals.get(i).x);
    		normalBuffer.put(normals.get(i).y);
    		normalBuffer.put(normals.get(i).z);
    	} normalBuffer.flip();
    	
    	FloatBuffer textureBuffer = BufferUtils.createFloatBuffer(textures.size() * 2);
    	for (int i = 0; i < textures.size(); i ++) {
    		textureBuffer.put(textures.get(i).x);
    		textureBuffer.put(textures.get(i).y);
    	} textureBuffer.flip();
    	
    	ShortBuffer indexBuffer = BufferUtils.createShortBuffer(faces.size() * 3);
    	for (int i = 0; i < faces.size(); i ++) {
    		indexBuffer.put((short) (faces.get(i).vertexIndices.x - 1));
    		indexBuffer.put((short) (faces.get(i).vertexIndices.y - 1));
    		indexBuffer.put((short) (faces.get(i).vertexIndices.z - 1));
    	} indexBuffer.flip();
    	
        vaoID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);
        
        vboVertID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboVertID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        vboNormID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboNormID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        vboTexID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboTexID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        GL30.glBindVertexArray(0);
        
        vboIndID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboIndID);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void render() {
		GL30.glBindVertexArray(vaoID);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboIndID);
        
        GL11.glDrawElements(GL_TRIANGLES, faces.size() * 3, GL_UNSIGNED_SHORT, 0);
        
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
	}
	
	public void dispose() {
		GL30.glBindVertexArray(vaoID);
    	
    	GL20.glDisableVertexAttribArray(0);
    	GL20.glDisableVertexAttribArray(1);
    	GL20.glDisableVertexAttribArray(2);
    	
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    	GL15.glDeleteBuffers(vboVertID);
    	GL15.glDeleteBuffers(vboNormID);
    	GL15.glDeleteBuffers(vboTexID);
    	
    	GL30.glBindVertexArray(0);
    	GL30.glDeleteVertexArrays(vaoID);
    	
    	GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    	GL15.glDeleteBuffers(vboIndID);
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVboVertID() {
		return vboVertID;
	}

	public int getVboTexID() {
		return vboTexID;
	}

	public int getVboNormID() {
		return vboNormID;
	}
	
	public int getVboIndID() {
		return vboIndID;
	}

	public Vec3f getPosition() {
		return position;
	}

	public void setPosition(Vec3f position) {
		this.position = position;
	}
	
	public void setPosition(float x, float y, float z) {
		position.x = x;
		position.y = y;
		position.z = z;
	}

	public Vec3f getAngle() {
		return angle;
	}

	public void setAngle(Vec3f angle) {
		this.angle = angle;
	}
	
	public void setAngle(float x, float y, float z) {
		angle.x = x;
		angle.y = y;
		angle.z = z;
	}

	public Vec3f getScale() {
		return scale;
	}

	public void setScale(Vec3f scale) {
		this.scale = scale;
	}
	
	public void setScale(float x, float y, float z) {
		scale.x = x;
		scale.y = y;
		scale.z = z;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
}

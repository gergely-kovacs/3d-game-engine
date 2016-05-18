package objects.terrain;

import java.nio.FloatBuffer;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import objects.Model;
import objects.Texture;
import util.Util;
import util.maths.Mat4f;
import util.maths.Vec3f;

public class Terrain {

	private static final int NUMBER_OF_OCTAVES = 4;
	private static final float COEFFICIENT = (float) Math.pow(2, NUMBER_OF_OCTAVES - 1);
	private static final float AMPLITUDE = 1.5f;
	private static final float SMOOTHING = 0.3f;
	private static final float TEXTURE_STRETCHING = 40.0f;
	
	private static final float SIZE = 100.0f;
	private static final int GRID_COUNT = 100;
	private static final float GRID_SIZE = SIZE / GRID_COUNT;
	
	private Model model;
	
	private FloatBuffer matBuff;
	private Mat4f mMat;
	
	private int mMatUniLoc, texId;
	
	private Random randGen;
	
	public Terrain() {
		randGen = new Random();
		int vertexCount = (GRID_COUNT + 1) * (GRID_COUNT + 1 );
		float[] vertices = new float[vertexCount * 3];
		float[] normals = new float[vertexCount * 3];
		float[] textureCoords = new float[vertexCount * 2];
		int[] indices = new int[GRID_COUNT * GRID_COUNT * 6];
		for (int i = 0; i < vertexCount; i++) {
			vertices[i*3] = -SIZE/2 + (i % (GRID_COUNT + 1) * GRID_SIZE);
			vertices[i*3+2] = -SIZE/2 + ((float) Math.floor(i % vertexCount / (GRID_COUNT + 1)) * GRID_SIZE);
			vertices[i*3+1] = getHeight(vertices[i*3], vertices[i*3+2]);
			textureCoords[i*2] = (float) i % (GRID_COUNT + 1) / GRID_COUNT * TEXTURE_STRETCHING;
			textureCoords[i*2+1] = (float) Math.floor(i % vertexCount / (GRID_COUNT + 1)) / GRID_COUNT * TEXTURE_STRETCHING;
			Vec3f normal = computeNormal(vertices[i*3], vertices[i*3+2]);
			normals[i*3] = normal.x;
			normals[i*3+1] = normal.y;
			normals[i*3+2] = normal.z;
		}
		int indexpointer = 0;
		for (int row = 0; row < GRID_COUNT; row++){
			for (int column = 0; column < GRID_COUNT; column++){
				indices[indexpointer] = row * (GRID_COUNT + 1) + column;
				indices[indexpointer+1] = (row + 1) * (GRID_COUNT + 1) + column;
				indices[indexpointer+2] = row * (GRID_COUNT + 1) + column + 1;
				indices[indexpointer+3] = row * (GRID_COUNT + 1) + column + 1;
				indices[indexpointer+4] = (row + 1) * (GRID_COUNT + 1) + column;
				indices[indexpointer+5] = (row + 1) * (GRID_COUNT + 1) + column + 1;
				indexpointer += 6;
			}
		}
		model = new Model(vertices, textureCoords, normals, indices);
		Texture texture = new Texture("res/textures/Sand_3_Diffuse.png", GL13.GL_TEXTURE0);
		texId = texture.getId();
		
		matBuff = BufferUtils.createFloatBuffer(16);
		mMat = new Mat4f();
	}
	
	public void render() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
		
		GL30.glBindVertexArray(model.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, model.getVboIndId());
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getIndicesCount(), GL11.GL_UNSIGNED_INT, 0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public float getHeight(float x, float z) {
		float height = 0;
		for (int i = 0; i < NUMBER_OF_OCTAVES; i++) {
			float frequencyFactor = (float) (Math.pow(2, i) / COEFFICIENT);
			float smoothingFactor = (float) (Math.pow(SMOOTHING, i));
			height += getInterpolatedNoise(x * frequencyFactor, z * frequencyFactor) * AMPLITUDE * smoothingFactor;
		}
		return height;
	}
	
	private float getNoise(float x, float z) {
		randGen.setSeed((long) (x * 84419 + z * 13767));
		return randGen.nextFloat() * 2.0f - 1.0f;
	}
	
	private float getSmoothNoise(float x, float z) {
		float corners = (getNoise(x - GRID_SIZE, z - GRID_SIZE) + getNoise(x + GRID_SIZE, z - GRID_SIZE) +
				getNoise(x - GRID_SIZE, z + GRID_SIZE) + getNoise(x + GRID_SIZE, z + GRID_SIZE)) / 4;
		float sides = (getNoise(x - GRID_SIZE, z) + getNoise(x, z + GRID_SIZE) + getNoise(x + GRID_SIZE, z) +
				getNoise(x, z - GRID_SIZE)) / 2;
		float center = getNoise(x, z);
		return corners + sides + center;
	}
	
	private float getInterpolatedNoise(float x, float z) {
		float roundX = Util.roundDown(x, GRID_SIZE);
		float roundZ = Util.roundDown(z, GRID_SIZE);
		float remX, remZ;
		if (x < 0) remX = roundX - x;
			else remX = x - roundX;
		if (z < 0) remZ = roundZ - z; 
			else remZ = z - roundZ;
		
		float v1 = getSmoothNoise(roundX, roundZ);
		float v2 = getSmoothNoise(roundX + GRID_SIZE, roundZ);
		float v3 = getSmoothNoise(roundX, roundZ + GRID_SIZE);
		float v4 = getSmoothNoise(roundX + GRID_SIZE, roundZ + GRID_SIZE);
		float i1 = Util.trigInterp(v1, v2, remX);
		float i2 = Util.trigInterp(v3, v4, remX);
		return Util.trigInterp(i1, i2, remZ);
	}
	
	private Vec3f computeNormal(float x, float z) {
		float heightLeft = getHeight(x - GRID_SIZE, z);
		float heightRight = getHeight(x + GRID_SIZE, z);
		float heightDown = getHeight(x, z - GRID_SIZE);
		float heightUp = getHeight(x, z + GRID_SIZE);
		Vec3f normal = new Vec3f(heightLeft - heightRight, 2.0f, heightDown - heightUp);
		normal.normalize();
		return normal;
	}

	public void getUniforms(int shaderProgramId) {
		mMatUniLoc = GL20.glGetUniformLocation(shaderProgramId, "model");
	}

	public void specifyUniforms() {
		mMat.store(matBuff);
        GL20.glUniformMatrix4fv(mMatUniLoc, false, matBuff);
	}
	
}

package objects;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import util.maths.Mat4f;
import util.maths.Vec3f;

public class Entity{
	protected FloatBuffer matBuff;
	protected Mat4f mMat;
	protected Vec3f position, direction, scale;
	protected float shineDamper, reflectivity;
	protected int texId, vaoId, vertexCount, mMatUniLoc,
		shineDamperUniLoc, reflectivityUniLoc;

	public Entity(Model model, Texture texture,
			Vec3f position, Vec3f direction, Vec3f scale,
			float shineDamper, float reflectivity) {
		texId = texture.getId();
		
		vaoId = model.getVaoId();
		vertexCount = model.getVertexCount();
		
		this.position = position;
		this.direction = direction;
		this.scale = scale;
		
		matBuff = BufferUtils.createFloatBuffer(16);
		mMat = new Mat4f();
		
		mMat.scale(scale);
		mMat.rotateX((float) Math.toRadians(direction.x));
		mMat.rotateY((float) Math.toRadians(direction.y));
		mMat.rotateZ((float) Math.toRadians(direction.z));
		mMat.translate(position);
		
		this.shineDamper = shineDamper;
		this.reflectivity = reflectivity;
	}
	
	public void render() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
		
		GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
        
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public void specifyUniforms() {
		mMat.store(matBuff);
        GL20.glUniformMatrix4fv(mMatUniLoc, false, matBuff);
		GL20.glUniform1f(shineDamperUniLoc, shineDamper);
        GL20.glUniform1f(reflectivityUniLoc, reflectivity);
	}
	
	public void getUniforms(int shaderProgramId) {
		mMatUniLoc = GL20.glGetUniformLocation(shaderProgramId, "model");
		shineDamperUniLoc = GL20.glGetUniformLocation(shaderProgramId, "shineDamper");
		reflectivityUniLoc = GL20.glGetUniformLocation(shaderProgramId, "reflectivity");
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

	public Vec3f getDirection() {
		return direction;
	}

	public void setDirection(Vec3f direction) {
		this.direction = direction;
	}
	
	public void setDirection(float x, float y, float z) {
		direction.x = x;
		direction.y = y;
		direction.z = z;
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

}

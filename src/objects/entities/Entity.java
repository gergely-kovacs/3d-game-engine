package objects.entities;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import objects.Model;
import objects.Texture;
import util.maths.Mat4f;
import util.maths.Vec3f;

public class Entity{
	protected FloatBuffer matBuff;
	protected Mat4f mMat;
	protected Vec3f position, orientation, scale;
	protected float shineDamper, reflectivity;
	protected int texId, vaoId, vertexCount, mMatUniLoc,
		shineDamperUniLoc, reflectivityUniLoc;

	public Entity(Model model, Texture texture,
			Vec3f position, Vec3f orientation, Vec3f scale,
			float shineDamper, float reflectivity) {
		texId = texture.getId();
		
		vaoId = model.getVaoId();
		vertexCount = model.getVertexCount();
		
		this.position = position;
		this.orientation = orientation;
		this.scale = scale;
		
		matBuff = BufferUtils.createFloatBuffer(16);
		mMat = new Mat4f();
		
		transform();
		
		this.shineDamper = shineDamper;
		this.reflectivity = reflectivity;
	}
	
	public void transform() {
		mMat.loadIdentity();
		mMat.translate(position.x, position.y, position.z);
		mMat.rotateX(orientation.x);
		mMat.rotateY(orientation.y);
		mMat.rotateZ(orientation.z);
		mMat.scale(scale.x, scale.y, scale.z);
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
		this.position.x = position.x;
		this.position.y = position.y;
		this.position.z = position.z;
	}
	
	public void setPosition(float x, float y, float z) {
		position.x = x;
		position.y = y;
		position.z = z;
	}

	public Vec3f getOrientation() {
		return orientation;
	}

	public void setOrientation(Vec3f direction) {
		this.orientation.x = direction.x;
		this.orientation.y = direction.y;
		this.orientation.z = direction.z;
	}
	
	public void setOrientation(float x, float y, float z) {
		orientation.x = x;
		orientation.y = y;
		orientation.z = z;
	}

	public Vec3f getScale() {
		return scale;
	}

	public void setScale(Vec3f scale) {
		this.scale.x = scale.x;
		this.scale.y = scale.y;
		this.scale.z = scale.z;
	}
	
	public void setScale(float x, float y, float z) {
		scale.x = x;
		scale.y = y;
		scale.z = z;
	}

}

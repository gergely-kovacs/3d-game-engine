package objects;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import display.DisplayManager;
import util.maths.Matrix4f;
import util.maths.Vec3f;

public class Camera {
	private FloatBuffer matBuff;
	private Matrix4f pMat;
	private Vec3f position;
	private float yaw, pitch, roll;
	private int pMatUniLoc;
	
	public Camera(Vec3f position, float yaw, float pitch, float roll, float fov) {
		this.position = position;
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
		
		matBuff = BufferUtils.createFloatBuffer(16);
        pMat = new Matrix4f();
        
        pMat.setPerspective(3.1415926535f / 180.0f * fov, (float) DisplayManager.WIDTH / (float) DisplayManager.HEIGHT, 0.1f, 100.0f);
	}
	
	public void specifyUniforms() {
		pMat.get(matBuff);
        GL20.glUniformMatrix4fv(pMatUniLoc, false, matBuff);
	}
	
	public void getUniforms(int shaderProgramId) {
		pMatUniLoc = GL20.glGetUniformLocation(shaderProgramId, "projection");
	}
	
	public Vec3f getPosition() {
		return position;
	}

	public void setPosition(Vec3f position) {
		this.position = position;
	}
	
	public void setPosX(float x) {
		this.position.x = x;
	}
	
	public void setPosY(float y) {
		this.position.y = y;
	}
	
	public void setPosZ(float z) {
		this.position.z = z;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}
}

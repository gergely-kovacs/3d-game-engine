package objects;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import display.DisplayManager;
import util.maths.Matrix4f;
import util.maths.Vec3f;
import world.World;

public class Camera {
	private FloatBuffer matBuff;
	private Matrix4f mMat, vMat, pMat;
	private Vec3f position;
	private float yaw, pitch, roll;
	private int mMatUniLoc, vMatUniLoc, pMatUniLoc;
	
	public Camera(Vec3f position, float yaw, float pitch, float roll) {
		this.position = position;
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
		
		matBuff = BufferUtils.createFloatBuffer(16);
		mMat = new Matrix4f();
        vMat = new Matrix4f();
        pMat = new Matrix4f();
        
        pMat.setPerspective(3.1415926535f / 180.0f * 45.0f, (float) DisplayManager.WIDTH / (float) DisplayManager.HEIGHT, 0.015f, 100.0f);
        
        mMatUniLoc = GL20.glGetUniformLocation(World.program.getProgramId(), "model");
		vMatUniLoc = GL20.glGetUniformLocation(World.program.getProgramId(), "view");
		pMatUniLoc = GL20.glGetUniformLocation(World.program.getProgramId(), "projection");
	}
	
	public void specifyUniforms() {
		pMat.get(matBuff);
        GL20.glUniformMatrix4fv(pMatUniLoc, false, matBuff);
        vMat.get(matBuff);
        GL20.glUniformMatrix4fv(vMatUniLoc, false, matBuff);
        mMat.get(matBuff);
        GL20.glUniformMatrix4fv(mMatUniLoc, false, matBuff);
	}
	
	public void update() {
		vMat.identity();
        vMat.rotateX(3.1415926535f / 180f * pitch);
        vMat.rotateY(3.1415926535f / 180f * yaw);
        vMat.translate(position.x, position.y, -position.z);
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

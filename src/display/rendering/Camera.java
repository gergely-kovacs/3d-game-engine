package display.rendering;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import display.DisplayManager;
import util.maths.Mat4f;
import util.maths.Vec3f;
import world.World;

public class Camera {
	public final float HEIGHT = 0.2f;
	public boolean isAirborne = true;
	
	private FloatBuffer matBuff;
	private Mat4f pMat;
	private Vec3f position;
	private float yaw, pitch, roll;
	private int pMatUniLoc;
	private float verticalSpeed = 0.0f;
	
	public Camera(Vec3f position, float yaw, float pitch, float roll, float fov) {
		this.position = position;
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
		
		matBuff = BufferUtils.createFloatBuffer(16);
		pMat = new Mat4f();
        
        pMat.loadPerspective(fov, (float) DisplayManager.WIDTH / (float) DisplayManager.HEIGHT, 0.01f, 1000.0f);
	}
	
	public void fall() {
		if (isAirborne) {
			if (position.y > World.terrain.getHeight(position.x, position.z) + HEIGHT) {
				position.y += verticalSpeed * DisplayManager.getDelta() + DisplayManager.getDelta() * World.GRAVITY;
				verticalSpeed -= DisplayManager.getDelta();
			} else {
				isAirborne = false;
				verticalSpeed = 0.0f;
			}
		} else {
			position.y = World.terrain.getHeight(position.x, position.z) + HEIGHT;
		}
	}
	
	public void jump() {
		if (!isAirborne) {
			verticalSpeed = 1.25f;
			isAirborne = true;
			position.y += verticalSpeed * DisplayManager.getDelta() + DisplayManager.getDelta() * World.GRAVITY;
			verticalSpeed -= DisplayManager.getDelta();
		}
	}
	
	public void specifyUniforms() {
		pMat.store(matBuff);
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

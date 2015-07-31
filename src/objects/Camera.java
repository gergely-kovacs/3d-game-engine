package objects;

import utils.maths.Vector3f;

public class Camera {
	private Vector3f position;
	private float yaw, pitch, roll;
	
	public Camera(Vector3f position, float yaw, float pitch, float roll) {
		this.position = position;
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
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

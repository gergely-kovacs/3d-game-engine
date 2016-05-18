package objects.entities;

import display.DisplayManager;
import objects.Model;
import objects.Texture;
import util.maths.Vec3f;
import world.World;

public class MovableEntity extends Entity {
	private boolean isAirborne = true;
	private float verticalSpeed = 0.0f;
	private float speed;

	public MovableEntity(Model model, Texture texture,
			Vec3f position, Vec3f direction, Vec3f scale,
			float shineDamper, float reflectivity, float speed) {
		super(model, texture, position, direction, scale, shineDamper, reflectivity);
		this.speed = speed;
	}

	public void follow(Vec3f target) {
		if (!isAirborne) {
			float xDirection = target.x - position.x;
			float zDirection = target.z - position.z;
			float length = (float) Math.sqrt(xDirection * xDirection + zDirection * zDirection);
			xDirection /= length;
			zDirection /= length;
			
			position.x += xDirection * speed * DisplayManager.getDelta();
			position.z += zDirection * speed * DisplayManager.getDelta();
			
			float angleToCamera = (float) Math.toDegrees(Math.atan2(xDirection, zDirection));
			float difference = angleToCamera - orientation.y;
			
			orientation.y += difference * 10.0f * DisplayManager.getDelta();
		}
		
		fall();
	}
	
	public void fall() {
		if (isAirborne) {
			if (position.y > World.terrain.getHeight(position.x, position.z)) {
				position.y += verticalSpeed * DisplayManager.getDelta() + DisplayManager.getDelta() * World.GRAVITY;
				verticalSpeed -= DisplayManager.getDelta();
			} else {
				isAirborne = false;
				verticalSpeed = 0.0f;
			}
		} else {
			position.y = World.terrain.getHeight(position.x, position.z);
		}
		
		transform();
	}
	
	public void jump() {
		if (!isAirborne) {
			verticalSpeed = 1.25f;
			isAirborne = true;
			position.y += verticalSpeed * DisplayManager.getDelta() + DisplayManager.getDelta() * World.GRAVITY;
			verticalSpeed -= DisplayManager.getDelta();
		}
	}
}

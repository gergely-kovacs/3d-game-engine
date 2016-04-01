package objects.entities;

import display.DisplayManager;
import objects.Model;
import objects.Texture;
import util.maths.Vec3f;
import world.World;

public class MovableEntity extends Entity {
	private boolean isAirborne = true;
	private float verticalSpeed = 0.0f;

	public MovableEntity(Model model, Texture texture,
			Vec3f position, Vec3f direction, Vec3f scale,
			float shineDamper, float reflectivity) {
		super(model, texture, position, direction, scale, shineDamper, reflectivity);
	}

	public void move() {
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

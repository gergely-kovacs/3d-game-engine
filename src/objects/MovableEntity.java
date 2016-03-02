package objects;

import display.DisplayManager;
import util.maths.Vec3f;
import world.World;

public class MovableEntity extends Entity {
	private boolean isAirborne = true;

	public MovableEntity(Model model, Texture texture,
			Vec3f position, Vec3f direction, Vec3f scale,
			float shineDamper, float reflectivity) {
		super(model, texture, position, direction, scale, shineDamper, reflectivity);
	}

	public void move() {
		if (isAirborne) {
			if (position.y > World.terrain.getHeight(position.x, position.z)) {
				position.y += DisplayManager.getDelta() * World.GRAVITY;
			} else {
				isAirborne = false;
			}
		} else {
			position.y = World.terrain.getHeight(position.x, position.z);
		}
		
		mMat.identity();
		mMat.scale(scale.x, scale.y, scale.z);
		mMat.rotateX(3.1415926535f / 180f * direction.x);
		mMat.rotateY(3.1415926535f / 180f * direction.y);
		mMat.rotateZ(3.1415926535f / 180f * direction.z);
		mMat.translate(position.x, position.y, position.z);
	}
}

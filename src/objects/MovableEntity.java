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
		
		mMat.loadIdentity();
		mMat.scale(scale);
		mMat.rotateX((float) Math.toRadians(direction.x));
		mMat.rotateY((float) Math.toRadians(direction.y));
		mMat.rotateZ((float) Math.toRadians(direction.z));
		mMat.translate(position);
	}
}

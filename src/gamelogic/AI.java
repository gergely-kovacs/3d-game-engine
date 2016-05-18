package gamelogic;

import objects.entities.MovableEntity;
import world.World;

public class AI {

	public static void update() {
		for (MovableEntity entity : World.movableEntities) {
			entity.follow(World.camera.getPosition());
		}
	}
}

package gamelogic;

import objects.entities.Entity;
import world.World;

public class GameLogic {
	
	public static boolean shouldGameCommence() {
		for (Entity entity : World.movableEntities) {
			if (entity.getBounds().isPointInside(World.camera.getPosition())) {
				System.out.println("An entity has collided with the player.");
				return false;
			}
		}
		return true;
	}
}

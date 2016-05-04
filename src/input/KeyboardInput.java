package input;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL11;

import display.DisplayManager;
import world.World;

public class KeyboardInput extends GLFWKeyCallback {

	float rotationDelta = 20.0f, speed = 1.0f;
	
	public static boolean[] keys = new boolean[65535];
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		try {
			keys[key] = action != GLFW_RELEASE;
		}
		catch (ArrayIndexOutOfBoundsException exception) {
			System.out.println("The pressed key's index is out of the array's bounds.");
		}
	}
	
	public void update() {
		float delta = (float) DisplayManager.getDelta();
		
		if (keys[GLFW_KEY_ESCAPE]) {
			glfwSetWindowShouldClose(DisplayManager.window, GL11.GL_TRUE);
		}
		
        if (keys[GLFW_KEY_W]) {
    		World.camera.setPosZ((float) (World.camera.getPosition().z - (delta * speed * Math.cos(Math.toRadians(World.camera.getYaw())))));
    		World.camera.setPosX((float) (World.camera.getPosition().x + (delta * speed * Math.sin(Math.toRadians(World.camera.getYaw())))));
		}
        if (keys[GLFW_KEY_S]) {
        	World.camera.setPosZ((float) (World.camera.getPosition().z + (delta * speed * Math.cos(Math.toRadians(World.camera.getYaw())))));
        	World.camera.setPosX((float) (World.camera.getPosition().x - (delta * speed * Math.sin(Math.toRadians(World.camera.getYaw())))));
		}
        if (keys[GLFW_KEY_A]) {
        	World.camera.setPosX((float) (World.camera.getPosition().x - (delta * speed * Math.cos(Math.toRadians(World.camera.getYaw())))));
        	World.camera.setPosZ((float) (World.camera.getPosition().z - (delta * speed * Math.sin(Math.toRadians(World.camera.getYaw())))));
		}
        if (keys[GLFW_KEY_D]) {
        	World.camera.setPosX((float) (World.camera.getPosition().x + (delta * speed * Math.cos(Math.toRadians(World.camera.getYaw())))));
        	World.camera.setPosZ((float) (World.camera.getPosition().z + (delta * speed * Math.sin(Math.toRadians(World.camera.getYaw())))));
		}
		if (keys[GLFW_KEY_SPACE]) {
			World.camera.jump();
		}
	}
}

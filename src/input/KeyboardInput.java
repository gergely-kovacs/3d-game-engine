package input;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL11;

import display.DisplayManager;
import world.World;

public class KeyboardInput extends GLFWKeyCallback {

	float rotationDelta = 2.5f, speed = 1.5f;
	
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
			World.camera.getPosition().y += delta * speed;
		}
		if (keys[GLFW_KEY_C]) {
			World.camera.getPosition().y -= delta * speed;
		}
		
		if (keys[GLFW_KEY_KP_4]) {
			World.nyuszi.setOrientation(World.nyuszi.getOrientation().add(0.0f, rotationDelta, 0.0f));
		}
		if (keys[GLFW_KEY_KP_6]) {
			World.nyuszi.setOrientation(World.nyuszi.getOrientation().add(0.0f, -rotationDelta, 0.0f));
		}
		if (keys[GLFW_KEY_KP_ADD]) {
			World.nyuszi.setScale(World.nyuszi.getScale().add(0.25f, 0.25f, 0.25f));
		}
		if (keys[GLFW_KEY_KP_SUBTRACT]) {
			World.nyuszi.setScale(World.nyuszi.getScale().add(-0.25f, -0.25f, -0.25f));
		}
		if (keys[GLFW_KEY_RIGHT_SHIFT]) {
			World.nyuszi.jump();
		}
		if (keys[GLFW_KEY_UP]) {
			World.nyuszi.setPosition(World.nyuszi.getPosition().add(0, 0, -speed * delta));
		}
		if (keys[GLFW_KEY_DOWN]) {
			World.nyuszi.setPosition(World.nyuszi.getPosition().add(0, 0, speed * delta));
		}
		if (keys[GLFW_KEY_LEFT]) {
			World.nyuszi.setPosition(World.nyuszi.getPosition().add(-speed * delta, 0, 0));
		}
		if (keys[GLFW_KEY_RIGHT]) {
			World.nyuszi.setPosition(World.nyuszi.getPosition().add(speed * delta, 0, 0));
		}
	}
	
}

package input;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL11;

import world.World;

public class InputHandler extends GLFWKeyCallback {

	float rotationDelta = 2.5f, posDelta = 0.025f;
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if ( action != GLFW_RELEASE ) {
            switch (key) {
                case GLFW_KEY_ESCAPE:
                	glfwSetWindowShouldClose(window, GL11.GL_TRUE);
                	break;
                	
                case GLFW_KEY_UP:
                	World.camera.setPitch(World.camera.getPitch() - rotationDelta);
                    break;
                case GLFW_KEY_DOWN:
                	World.camera.setPitch(World.camera.getPitch() + rotationDelta);
                    break;
                case GLFW_KEY_LEFT:
                	World.camera.setYaw(World.camera.getYaw() - rotationDelta);
                    break;
                case GLFW_KEY_RIGHT:
                	World.camera.setYaw(World.camera.getYaw() + rotationDelta);
                    break;
                    
                case GLFW_KEY_W:
                	World.camera.setPosZ(World.camera.getPosition().z -= posDelta * Math.cos((double) (3.1415926535f / 180f * World.camera.getYaw())));
                	World.camera.setPosX(World.camera.getPosition().x -= posDelta * Math.sin((double) (3.1415926535f / 180f * World.camera.getYaw())));
                    break;
                case GLFW_KEY_S:
                	World.camera.setPosZ(World.camera.getPosition().z += posDelta * Math.cos((double) (3.1415926535f / 180f * World.camera.getYaw())));
                	World.camera.setPosX(World.camera.getPosition().x += posDelta * Math.sin((double) (3.1415926535f / 180f * World.camera.getYaw())));
                    break;
                case GLFW_KEY_SPACE:
                    World.camera.getPosition().y -= posDelta;
                    break;
                case GLFW_KEY_C:
                    World.camera.getPosition().y += posDelta;
                    break;
                case GLFW_KEY_A:
                	World.camera.setPosZ(World.camera.getPosition().z -= posDelta * Math.sin((double) (3.1415926535f / 180f * World.camera.getYaw())));
                	World.camera.setPosX(World.camera.getPosition().x += posDelta * Math.cos((double) (3.1415926535f / 180f * World.camera.getYaw())));
                    break;
                case GLFW_KEY_D:
                	World.camera.setPosZ(World.camera.getPosition().z += posDelta * Math.sin((double) (3.1415926535f / 180f * World.camera.getYaw())));
                	World.camera.setPosX(World.camera.getPosition().x -= posDelta * Math.cos((double) (3.1415926535f / 180f * World.camera.getYaw())));
                    break;
            }
		}
	}
}

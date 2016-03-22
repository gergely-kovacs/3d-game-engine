package input;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL11;

import util.maths.Vec3f;
import world.World;

public class KeyboardInput extends GLFWKeyCallback {

	float rotationDelta = 2.5f, posDelta = 0.05f;
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if ( action != GLFW_RELEASE ) {
            switch (key) {
                case GLFW_KEY_ESCAPE:
                	glfwSetWindowShouldClose(window, GL11.GL_TRUE);
                	break;
                	
                case GLFW_KEY_KP_ADD:
                	World.nyuszi.setScale(World.nyuszi.getScale().add(Vec3f.UNIT_VECTOR));
                	break;
                case GLFW_KEY_KP_SUBTRACT:
                	World.nyuszi.setScale(World.nyuszi.getScale().subtract(Vec3f.UNIT_VECTOR));
                	break;
                	
                	
                case GLFW_KEY_KP_5:
                	glfwSetCursorPos(window, 0, 0);
                	World.camera.setYaw(0.0f);
                	World.camera.setPitch(0.0f);
                	break;
                	
                case GLFW_KEY_UP:
                	World.nyuszi.setPosition(World.nyuszi.getPosition().add(0, 0, -posDelta));
                	break;
                case GLFW_KEY_DOWN:
                	World.nyuszi.setPosition(World.nyuszi.getPosition().add(0, 0, posDelta));
                	break;
                case GLFW_KEY_LEFT:
                	World.nyuszi.setPosition(World.nyuszi.getPosition().add(-posDelta, 0, 0));
                	break;
                case GLFW_KEY_RIGHT:
                	World.nyuszi.setPosition(World.nyuszi.getPosition().add(posDelta, 0, 0));
                	break;
                    
                case GLFW_KEY_W:
                	World.camera.setPosZ((float) (World.camera.getPosition().z + (posDelta * Math.cos((double) (3.1415926535f / 180f * World.camera.getYaw())))));
                	World.camera.setPosX((float) (World.camera.getPosition().x + (posDelta * Math.sin((double) (3.1415926535f / 180f * World.camera.getYaw())))));
                    break;
                case GLFW_KEY_S:
                	World.camera.setPosZ((float) (World.camera.getPosition().z - (posDelta * Math.cos((double) (3.1415926535f / 180f * World.camera.getYaw())))));
                	World.camera.setPosX((float) (World.camera.getPosition().x - (posDelta * Math.sin((double) (3.1415926535f / 180f * World.camera.getYaw())))));
                    break;
                case GLFW_KEY_SPACE:
                    World.camera.getPosition().y += posDelta;
                    break;
                case GLFW_KEY_C:
                    World.camera.getPosition().y -= posDelta;
                    break;
                case GLFW_KEY_A:
                	World.camera.setPosZ((float) (World.camera.getPosition().z + (posDelta * Math.sin((double) (3.1415926535f / 180f * World.camera.getYaw())))));
                	World.camera.setPosX((float) (World.camera.getPosition().x - (posDelta * Math.cos((double) (3.1415926535f / 180f * World.camera.getYaw())))));
                    break;
                case GLFW_KEY_D:
                	World.camera.setPosZ((float) (World.camera.getPosition().z - (posDelta * Math.sin((double) (3.1415926535f / 180f * World.camera.getYaw())))));
                	World.camera.setPosX((float) (World.camera.getPosition().x + (posDelta * Math.cos((double) (3.1415926535f / 180f * World.camera.getYaw())))));
                    break;
            }
		}
	}
}

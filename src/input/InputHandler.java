package input;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWKeyCallback;

import display.Display;
import utils.maths.Vec3f;

public class InputHandler extends GLFWKeyCallback {

	float rotationDelta = 5f;
    float scaleDelta = 0.1f;
    float posDelta = 0.05f;
    Vec3f scaleAddResolution = new Vec3f(scaleDelta, scaleDelta, scaleDelta);
    Vec3f scaleMinusResolution = new Vec3f(-scaleDelta, -scaleDelta, -scaleDelta);
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		//if ( action == GLFW_RELEASE ) {
            switch (key) {
                case GLFW_KEY_ESCAPE:
                	glfwSetWindowShouldClose(window, GL_TRUE);
                	break;
                	
                case GLFW_KEY_UP:
                	Display.camera.setPitch(Display.camera.getPitch() - rotationDelta);
                    break;
                case GLFW_KEY_DOWN:
                	Display.camera.setPitch(Display.camera.getPitch() + rotationDelta);
                    break;
                case GLFW_KEY_LEFT:
                	Display.camera.setYaw(Display.camera.getYaw() - rotationDelta);
                    break;
                case GLFW_KEY_RIGHT:
                	Display.camera.setYaw(Display.camera.getYaw() + rotationDelta);
                    break;
                    
                case GLFW_KEY_W:
                    Display.camera.getPosition().z -= posDelta;
                    break;
                case GLFW_KEY_S:
                    Display.camera.getPosition().z += posDelta;
                    break;
                case GLFW_KEY_SPACE:
                    Display.camera.getPosition().y += posDelta;
                    break;
                case GLFW_KEY_C:
                    Display.camera.getPosition().y -= posDelta;
                    break;
                case GLFW_KEY_A:
                    Display.camera.getPosition().x -= posDelta;
                    break;
                case GLFW_KEY_D:
                    Display.camera.getPosition().x += posDelta;
                    break;
            }
		//}
	}
}

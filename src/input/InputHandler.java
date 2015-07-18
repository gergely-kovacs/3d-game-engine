package input;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWKeyCallback;

import utils.math.Vector3f;
import display.Display;

public class InputHandler extends GLFWKeyCallback {

	float rotationDelta = 5f;
    float scaleDelta = 0.1f;
    float posDelta = 0.05f;
    Vector3f scaleAddResolution = new Vector3f(scaleDelta, scaleDelta, scaleDelta);
    Vector3f scaleMinusResolution = new Vector3f(-scaleDelta, -scaleDelta, -scaleDelta);
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		//if ( action == GLFW_RELEASE ) {
            switch (key) {
                case GLFW_KEY_ESCAPE:
                	glfwSetWindowShouldClose(window, GL_TRUE);
                	break;
                	
                case GLFW_KEY_UP:
                    Display.camAng.x -= rotationDelta;
                    break;
                case GLFW_KEY_DOWN:
                	Display.camAng.x += rotationDelta;
                    break;
                case GLFW_KEY_LEFT:
                    Display.camAng.y -= rotationDelta;
                    break;
                case GLFW_KEY_RIGHT:
                	Display.camAng.y += rotationDelta;
                    break;
                    
                case GLFW_KEY_W:
                    Display.camPos.z += posDelta;
                    break;
                case GLFW_KEY_S:
                    Display.camPos.z -= posDelta;
                    break;
                case GLFW_KEY_SPACE:
                    Display.camPos.y -= posDelta;
                    break;
                case GLFW_KEY_C:
                    Display.camPos.y += posDelta;
                    break;
                case GLFW_KEY_A:
                    Display.camPos.x += posDelta;
                    break;
                case GLFW_KEY_D:
                    Display.camPos.x -= posDelta;
                    break;
            }
		//}
	}
}

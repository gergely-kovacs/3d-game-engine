package input;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWKeyCallback;

public class InputHandler extends GLFWKeyCallback {

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if ( action == GLFW_RELEASE ) {
            switch (key) {
                case GLFW_KEY_ESCAPE:
                	glfwSetWindowShouldClose(window, GL_TRUE);
                	break;
            }
		}
	}
}

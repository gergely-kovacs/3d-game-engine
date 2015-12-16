package util;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GL11;

public class Util {
	private static int frameCount = 0;
	private static double lastTime = 0;
	
	public static void monitorFrameRate() {
        frameCount++;
        if (glfwGetTime() - lastTime >= 1.0d) {
        	System.out.println(frameCount); // TODO: render using bitmap font
        	lastTime = glfwGetTime();
        	frameCount = 0;
        }
    }
	
	public static void monitorProcessingTime() {
        frameCount++;
        if (glfwGetTime() - lastTime >= 1.0d) {
        	System.out.println(1000.0d / (double) frameCount); // TODO: render using bitmap font
        	lastTime = glfwGetTime();
        	frameCount = 0;
        }
    }
	
	public static void checkForGLErrors() {
		int errorValue = GL11.glGetError();
        
        if (errorValue != GL11.GL_NO_ERROR) {
            switch(errorValue){
            case (GL11.GL_INVALID_ENUM):
            	System.out.println("Invalid enumeration!");
            	break;
            case (GL11.GL_INVALID_VALUE):
            	System.out.println("Invalid value!");
            	break;
			case (GL11.GL_INVALID_OPERATION):
				System.out.println("Invalid operation!");
            	break;
			case (GL11.GL_STACK_OVERFLOW):
				System.out.println("Stack overflow!");
				break;
			case (GL11.GL_STACK_UNDERFLOW):
				System.out.println("Stack underflow!");
				break;
			case (GL11.GL_OUT_OF_MEMORY):
				System.out.println("Out of memory!");
				break;
            default: System.out.println("Unknown error!"); break;
            }
        }
	}
	
	public static float calcDelta(float fromValue, float toValue, float stepSize, float duration) {
		int numberOfSteps = (int) (Math.abs(fromValue - toValue) / stepSize);
		float delta = duration / numberOfSteps;
		return delta;
	}

}

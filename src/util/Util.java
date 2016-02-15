package util;

import static org.lwjgl.glfw.GLFW.*;

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
	
	public static float calcDelta(float fromValue, float toValue, float stepSize, float duration) {
		int numberOfSteps = (int) (Math.abs(fromValue - toValue) / stepSize);
		float delta = duration / numberOfSteps;
		return delta;
	}

}

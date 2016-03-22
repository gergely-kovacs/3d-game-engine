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
	
	public static float computeDelta(float fromValue, float toValue, float stepSize, float duration) {
		int numberOfSteps = (int) (Math.abs(fromValue - toValue) / stepSize);
		float delta = duration / numberOfSteps;
		return delta;
	}
	
	public static float cosInterp(float v1, float v2, float a) {
		double angle = a * Math.PI;
		float normalized = (float) (1.0f - Math.cos(angle)) * 0.5f;
		return v1 * (1.0f - normalized) + v2 * normalized;
	}
	
	public static float roundDown(float numToRound, float multiple) {
	    if (multiple == 0)
	        return numToRound;

	    float remainder = Math.abs(numToRound) % multiple;
	    if (remainder == 0)
	        return numToRound;

	    if (numToRound < 0)
	        return -(Math.abs(numToRound) + multiple - remainder);
	    else
	        return numToRound - remainder;
	}

}

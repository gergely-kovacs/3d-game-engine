package input;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import util.maths.Vec2f;
import world.World;

public class CursorInput extends GLFWCursorPosCallback {
	
	private DoubleBuffer xBuffer, yBuffer;
	
	public CursorInput() {
		xBuffer = BufferUtils.createDoubleBuffer(1);
		yBuffer = BufferUtils.createDoubleBuffer(1);
	}
	
	@Override
	public void invoke(long window, double xpos, double ypos) {
		GLFW.glfwGetCursorPos(window, xBuffer, yBuffer);
		
		if (yBuffer.get(0) < 90 && yBuffer.get(0) > -90) {
			World.camera.setPitch((float) ypos);
		} else if (yBuffer.get(0) < -90) {
			GLFW.glfwSetCursorPos(window, xpos, -90.0d);
		} else if (yBuffer.get(0) > 90) {
			GLFW.glfwSetCursorPos(window, xpos, 90.0d);
		}
		
		if (xBuffer.get(0) < 360 && xBuffer.get(0) > -360) {
			World.camera.setYaw((float) xpos);
		} else if (xBuffer.get(0) < -360) {
			GLFW.glfwSetCursorPos(window, -xpos % 360.0d, ypos);
		} else if (xBuffer.get(0) > 360) {
			GLFW.glfwSetCursorPos(window, xpos % 360.0d, ypos);
		}
	}
	
	public Vec2f getCursorPosition() {
		return new Vec2f((float) xBuffer.get(0), (float) yBuffer.get(0));
	}

}

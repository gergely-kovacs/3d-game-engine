package util;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;

public class ErrorHandler extends GLFWErrorCallback {

	@Override
	public void invoke(int error, long description) {
		System.out.println("A GLFW error has occured: " + error + ", " + description + "!");
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
}

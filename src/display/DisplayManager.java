package display;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

import input.CursorInput;
import input.KeyboardInput;
import objects.Model;
import objects.Texture;
import util.ErrorHandler;
import util.Util;
import world.World;
 
public class DisplayManager {
	public static final int WIDTH = 1280, HEIGHT = 720;
	
    private final String TITLE = "Szakdolgozat";
    
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWCursorPosCallback cursorCallback;
	
    public Model nyusziModel;
    public Texture nyusziDiffuse;
    
    private long window;
    
    public void run() {
        try {
            init();
            loop();
 
            glfwDestroyWindow(window);
            keyCallback.release();
            cursorCallback.release();
            errorCallback.release();
        }
        finally {
        	World.texturedModelSp.dispose();
        	
            glfwTerminate();
        }
    }
    
    private void init() {
    	if ( glfwInit() != GL11.GL_TRUE )
        	throw new IllegalStateException("Unable to initialize GLFW!");
 
        window = glfwCreateWindow(WIDTH, HEIGHT, TITLE, NULL, NULL);
        if ( window == NULL )
        	throw new RuntimeException("Failed to create a window!");
        
        glfwSetErrorCallback(errorCallback = new ErrorHandler());
        glfwSetKeyCallback(window, keyCallback = new KeyboardInput());
        glfwSetCursorPosCallback(window, cursorCallback = new CursorInput());
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        
        glfwSetWindowPos(window,
            (GLFWvidmode.width(vidmode) - WIDTH) / 2,
            (GLFWvidmode.height(vidmode) - HEIGHT) / 2 );
        
        glfwMakeContextCurrent(window);
        GLContext.createFromCurrent();
        
        glfwSwapInterval(1);
        
        GL11.glClearColor(0.0f, 0.02f, 0.025f, 1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        
        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        
        World.init();
    }
 
    private void loop() {
        while ( glfwWindowShouldClose(window) == GL11.GL_FALSE ) {
        	World.day.passTime();
        	
            World.camera.update();
            
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            
            GL20.glUseProgram(World.texturedModelSp.getProgramId());
            World.camera.getUniforms(World.texturedModelSp.getProgramId());
            World.camera.specifyUniforms();
            World.sun.getUniforms(World.texturedModelSp.getProgramId());
            World.sun.specifyUniforms();
            World.nyuszi.getUniforms(World.texturedModelSp.getProgramId());
            World.nyuszi.specifyUniforms();
            
            World.nyuszi.render();
            GL20.glUseProgram(0);
            
            GL20.glUseProgram(World.skyboxSp.getProgramId());
            World.camera.getUniforms(World.skyboxSp.getProgramId());
            World.camera.specifyUniforms();
            World.sun.getUniforms(World.skyboxSp.getProgramId());
            World.sun.specifyUniforms();
            
            World.skybox.render();
            GL20.glUseProgram(0);
            
            glfwSwapBuffers(window);
            glfwPollEvents();
            
            Util.monitorFrameRate();
            ErrorHandler.checkForGLErrors();
        }
    }
    
    public static void main(String[] args) {
        new DisplayManager().run();
    }
}

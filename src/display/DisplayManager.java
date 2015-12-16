package display;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

import input.InputHandler;
import objects.Model;
import objects.Texture;
import util.Util;
import world.World;
 
public class DisplayManager {
	public static final int WIDTH = 1280, HEIGHT = 720;
	
    private final String TITLE = "Mernoki tervezes";
    
    private GLFWKeyCallback keyCallback;
	
    public Model nyusziModel;
    public Texture nyusziDiffuse;
    
    
    private long window;
    
    public void run() {
        try {
            init();
            loop();
 
            glfwDestroyWindow(window);
            keyCallback.release();
        }
        finally {
        	World.program.dispose();
        	
            glfwTerminate();
        }
    }
    
    private void init() {
    	if ( glfwInit() != GL11.GL_TRUE )
        	throw new IllegalStateException("Unable to initialize GLFW!");
 
        window = glfwCreateWindow(WIDTH, HEIGHT, TITLE, NULL, NULL);
        if ( window == NULL )
        	throw new RuntimeException("Failed to create a window!");
        
        glfwSetKeyCallback(window, keyCallback = new InputHandler());

        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        
        glfwSetWindowPos(window,
            (GLFWvidmode.width(vidmode) - WIDTH) / 2,
            (GLFWvidmode.height(vidmode) - HEIGHT) / 2 );
        
        glfwMakeContextCurrent(window);
        GLContext.createFromCurrent();
        
        glfwSwapInterval(0);
        
        GL11.glClearColor(0.0f, 0.02f, 0.025f, 1.0f);
        
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        
        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        
        World.init();
    }
 
    private void loop() {
        while ( glfwWindowShouldClose(window) == GL11.GL_FALSE ) {
        	World.timeMan.passTime();
        	
            World.camera.update();
            
            GL20.glUseProgram(World.program.getProgramId());
            World.camera.specifyUniforms();
            if (World.sun != null) World.sun.specifyUniforms();
            World.nyuszi.specifyUniforms();
            GL20.glUseProgram(0);
        	
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            
            GL20.glUseProgram(World.program.getProgramId());
            World.nyuszi.render();
            GL20.glUseProgram(0);
            
            glfwSwapBuffers(window);
            glfwPollEvents();
            
            Util.monitorFrameRate();
            Util.checkForGLErrors();
        }
    }
    
    public static void main(String[] args) {
        new DisplayManager().run();
    }
}

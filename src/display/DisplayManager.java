package display;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

import display.rendering.Renderer;
import display.rendering.Shader;
import display.rendering.ShaderProgram;
import gamelogic.AI;
import gamelogic.GameLogic;
import input.CursorInput;
import input.KeyboardInput;
import objects.entities.MovableEntity;
import util.ErrorHandler;
import util.Util;
import world.World;
 
public class DisplayManager {
	public static final int WIDTH = 1600, HEIGHT = 900;
	public static long window;
    private final String TITLE = "Szakdolgozat";
    
    private ErrorHandler errorCallback;
    private KeyboardInput keyCallback;
    private CursorInput cursorCallback;
    
    private Renderer entityRenderer, skyboxRenderer, terrainRenderer,
    	lightRenderer;
    
	private static double delta, deltaTracker;

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
        	// TODO: dispose of everything
            glfwTerminate();
        }
    }
    
    private void init() {
    	if ( glfwInit() != GL11.GL_TRUE )
        	throw new IllegalStateException("Unable to initialize GLFW!");
 
        window = glfwCreateWindow(WIDTH, HEIGHT, TITLE, glfwGetPrimaryMonitor(), NULL);
        if ( window == NULL )
        	throw new RuntimeException("Failed to create a window!");
        
        glfwSetErrorCallback(errorCallback = new ErrorHandler());
        glfwSetKeyCallback(window, keyCallback = new KeyboardInput());
        glfwSetCursorPosCallback(window, cursorCallback = new CursorInput());
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        /*ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window,
            (GLFWvidmode.width(vidmode) - WIDTH) / 2,
            (GLFWvidmode.height(vidmode) - HEIGHT) / 2 );*/
        
        glfwMakeContextCurrent(window);
        GLContext.createFromCurrent();
        
        glfwSwapInterval(0);
        
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        
        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        
        World.init();
        
        initShaders();
    }
    
    private void initShaders() {
    	int entityVs = new Shader("res/shaders/entityVs.glsl", GL20.GL_VERTEX_SHADER).getShaderId();
        int entityFs = new Shader("res/shaders/entityFs.glsl", GL20.GL_FRAGMENT_SHADER).getShaderId();
    	
        ArrayList<String> entityAttribs = new ArrayList<String>();
        entityAttribs.add("vertexPosition");
        entityAttribs.add("texture");
        entityAttribs.add("vertexNormal");
    	ShaderProgram entitySp = new ShaderProgram(entityVs, entityFs, entityAttribs);
    	entityRenderer = new Renderer(entitySp);
    	
    	int skyboxVs = new Shader("res/shaders/skyboxVs.glsl", GL20.GL_VERTEX_SHADER).getShaderId();
    	int skyboxFs = new Shader("res/shaders/skyboxFs.glsl", GL20.GL_FRAGMENT_SHADER).getShaderId();
    	
    	ArrayList<String> skyboxAttribs = new ArrayList<String>();
    	skyboxAttribs.add("vertexPosition");
    	ShaderProgram skyboxSp = new ShaderProgram(skyboxVs, skyboxFs, skyboxAttribs);
    	skyboxRenderer = new Renderer(skyboxSp);
    	
    	int terrainVs = new Shader("res/shaders/terrainVs.glsl", GL20.GL_VERTEX_SHADER).getShaderId();
    	int terrainFs = new Shader("res/shaders/terrainFs.glsl", GL20.GL_FRAGMENT_SHADER).getShaderId();
    	
    	ArrayList<String> terrainAttribs = new ArrayList<String>();
    	terrainAttribs.add("vertexPosition");
    	terrainAttribs.add("texture");
    	terrainAttribs.add("vertexNormal");
    	ShaderProgram terrainSp = new ShaderProgram(terrainVs, terrainFs, terrainAttribs);
    	terrainRenderer = new Renderer(terrainSp);
    	
    	int lightVs = new Shader("res/shaders/lightVs.glsl", GL20.GL_VERTEX_SHADER).getShaderId();
    	int lightFs = new Shader("res/shaders/lightFs.glsl", GL20.GL_FRAGMENT_SHADER).getShaderId();
    	
    	ArrayList<String> lightAttribs = new ArrayList<String>();
    	terrainAttribs.add("vertexPosition");
    	terrainAttribs.add("in_TextureCoords");
    	ShaderProgram lightSp = new ShaderProgram(lightVs, lightFs, lightAttribs);
    	lightRenderer = new Renderer(lightSp);
	}
 
	private void loop() {
        while ( glfwWindowShouldClose(window) == GL11.GL_FALSE &&
        		GameLogic.shouldGameCommence()) {
        	
        	World.day.passTime();
        	World.camera.fall();
        	
        	AI.update();
        	
        	entityRenderer.updateCamera();
        	skyboxRenderer.updateSkyboxCamera();
        	terrainRenderer.updateCamera();
        	lightRenderer.updateCamera();
        	
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            
            GL20.glUseProgram(entityRenderer.getProgramId());
            entityRenderer.getUniforms();
            entityRenderer.specifyUniforms();
            
            World.camera.getUniforms(entityRenderer.getProgramId());
            World.camera.specifyUniforms();
            World.sun.getUniforms(entityRenderer.getProgramId());
            World.sun.specifyUniforms();
            
            for (MovableEntity entity : World.movableEntities) {
            	entity.getUniforms(entityRenderer.getProgramId());
            	entity.specifyUniforms();
            	entity.render();
            }
            
            GL20.glUseProgram(0);
            
            GL20.glUseProgram(skyboxRenderer.getProgramId());
            skyboxRenderer.getUniforms();
            skyboxRenderer.specifyUniforms();
            
            World.camera.getUniforms(skyboxRenderer.getProgramId());
            World.camera.specifyUniforms();
            World.sun.getUniforms(skyboxRenderer.getProgramId());
            World.sun.specifyUniforms();
            
            World.skybox.render();
            
            GL20.glUseProgram(0);
            
            GL20.glUseProgram(terrainRenderer.getProgramId());
            terrainRenderer.getUniforms();
            terrainRenderer.specifyUniforms();
            
            World.camera.getUniforms(terrainRenderer.getProgramId());
            World.camera.specifyUniforms();
            World.sun.getUniforms(terrainRenderer.getProgramId());
            World.sun.specifyUniforms();
            
        	World.terrain.getUniforms(terrainRenderer.getProgramId());
        	World.terrain.specifyUniforms();
        	World.terrain.render();
            
            GL20.glUseProgram(0);
            
            GL20.glUseProgram(lightRenderer.getProgramId());
            lightRenderer.getUniforms();
            lightRenderer.specifyUniforms();
            
            World.camera.getUniforms(lightRenderer.getProgramId());
            World.camera.specifyUniforms();
            World.sun.getUniforms(lightRenderer.getProgramId());
            World.sun.specifyUniforms();
            
        	World.sun.getUniforms(lightRenderer.getProgramId());
        	World.sun.specifyUniforms();
        	World.sun.render();
            
            GL20.glUseProgram(0);
            
            glfwSwapBuffers(window);
            glfwPollEvents();
            keyCallback.update();
            
            delta = GLFW.glfwGetTime() - deltaTracker;
    		deltaTracker = GLFW.glfwGetTime();
            
            Util.monitorFrameRate();
            ErrorHandler.checkForGLErrors();
        }
    }
	
	public static double getDelta() {
		return delta;
	}
    
    public static void main(String[] args) {
        new DisplayManager().run();
    }
}

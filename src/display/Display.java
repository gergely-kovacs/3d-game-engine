package display;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

import input.InputHandler;
import objects.Camera;
import objects.Light;
import objects.Model;
import objects.Shader;
import utils.maths.Matrix4f;
import utils.maths.Vec3f;
import utils.maths.Vector3f;
 
public class Display {
	private final int WIDTH = 800, HEIGHT = 600;
    private final String TITLE = "Nyuszi";
	
    private long window;
    
    private int vsID, fsID, pID,
    	pMatLoc, vMatLoc, mMatLoc,
    	lPosLoc, lColLoc,
    	shineDamperLoc, reflectivityLoc;
    
    private Matrix4f pMat, vMat, mMat;
    
    private FloatBuffer matBuff;
    
    private GLFWKeyCallback keyCallback;
    
    public static Model nyuszi;
    
    public static Camera camera;
    
    public void run() {
        try {
            init();
            loop();
 
            glfwDestroyWindow(window);
            keyCallback.release();
        }
        finally {
        	/*GL13.glActiveTexture(GL13.GL_TEXTURE0);
        	GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        	GL11.glDeleteTextures(texID);
        	GL13.glActiveTexture(0);*/
        	
        	GL20.glUseProgram(0);
        	GL20.glDetachShader(pID, vsID);
        	GL20.glDetachShader(pID, fsID);
        	 
        	GL20.glDeleteShader(vsID);
        	GL20.glDeleteShader(fsID);
        	GL20.glDeleteProgram(pID);
        	
        	nyuszi.dispose();
        	
            glfwTerminate();
        }
    }
 
    private void init() {
        if ( glfwInit() != GL11.GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
 
        window = glfwCreateWindow(WIDTH, HEIGHT, TITLE, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
        
        glfwSetKeyCallback(window, keyCallback = new InputHandler());

        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        
        glfwSetWindowPos(
            window,
            (GLFWvidmode.width(vidmode) - WIDTH) / 2,
            (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );
        
        glfwMakeContextCurrent(window);
        GLContext.createFromCurrent();
        
        glfwSwapInterval(1);
        
        glClearColor(0.15f, 0.15f, 0.15f, 1.0f);
        
        glEnable(GL_DEPTH_TEST);
        
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    }
 
    private void loop() {
    	nyuszi = new Model("res/models/bunny2.obj",
			new Vec3f(0f, 0f, 0f),
			new Vec3f(0f, 0f, 0f),
			new Vec3f(1.0f, 1.0f, 1.0f),
			4.88f,
			0.12f);
    	
    	camera = new Camera(new Vector3f(0f, 0.1f, 0.5f), 0f, 0f, 0f);
        
        vsID = new Shader("res/shaders/vertexShader.glsl", GL20.GL_VERTEX_SHADER).getShaderID();
        fsID = new Shader("res/shaders/fragmentShader.glsl", GL20.GL_FRAGMENT_SHADER).getShaderID();
        
        pID = GL20.glCreateProgram();
        
        GL20.glAttachShader(pID, vsID);
        GL20.glAttachShader(pID, fsID);
        
        GL20.glBindAttribLocation(pID, 0, "vertexPosition");
        GL20.glBindAttribLocation(pID, 1, "vertexNormal");
        //GL20.glBindAttribLocation(pID, 1, "texture");
        
        GL20.glLinkProgram(pID);
        GL20.glValidateProgram(pID);
        
        //texID = loadTexture("res/textures/Texture1.png", GL13.GL_TEXTURE0);
        
        mMatLoc = GL20.glGetUniformLocation(pID, "model");
        vMatLoc = GL20.glGetUniformLocation(pID, "view");
        pMatLoc = GL20.glGetUniformLocation(pID, "projection");
        
        mMat = new Matrix4f();
        vMat = new Matrix4f();
        pMat = new Matrix4f();
        
        pMat.setPerspective(3.1415926535f / 180f * 60f, (float) WIDTH / (float) HEIGHT, 0.015f, 100f);
        
        matBuff = BufferUtils.createFloatBuffer(16);
        
        lPosLoc = GL20.glGetUniformLocation(pID, "lightPosition");
        lColLoc = GL20.glGetUniformLocation(pID, "lightColour");
        
        Light l = new Light(new Vec3f(0f, 0.5f, 0.5f), new Vec3f(0.6f, 0.6f, 0.6f));
        
        shineDamperLoc = GL20.glGetUniformLocation(pID, "shineDamper");
        reflectivityLoc = GL20.glGetUniformLocation(pID, "reflectivity");
        
        while ( glfwWindowShouldClose(window) == GL_FALSE ) {
            vMat.identity();
            vMat.rotateX(3.1415926535f / 180f * (camera.getPitch()));
            vMat.rotateY(3.1415926535f / 180f * (camera.getYaw()));
            vMat.translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);
        	
            GL20.glUseProgram(pID);
             
            pMat.get(matBuff);
            GL20.glUniformMatrix4fv(pMatLoc, false, matBuff);
            vMat.get(matBuff);
            GL20.glUniformMatrix4fv(vMatLoc, false, matBuff);
            mMat.get(matBuff);
            GL20.glUniformMatrix4fv(mMatLoc, false, matBuff);
            
            GL20.glUniform3f(lPosLoc, l.getPosition().x, l.getPosition().y, l.getPosition().z);
            GL20.glUniform3f(lColLoc, l.getColour().x, l.getColour().y, l.getColour().z);
            
            GL20.glUniform1f(shineDamperLoc, nyuszi.getShineDamper());
            GL20.glUniform1f(reflectivityLoc, nyuszi.getReflectivity());
            
            GL20.glUseProgram(0);
        	
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            GL20.glUseProgram(pID);
            
            /*GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);*/
            
            nyuszi.render();
            
            /*GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
            GL13.glActiveTexture(0);*/
            
            GL20.glUseProgram(0);
            
            glfwSwapBuffers(window);
 
            glfwPollEvents();
        }
    }
    
    /*private int loadTexture(String filename, int textureUnit) {
        ByteBuffer buf = null;
        int tWidth = 0;
        int tHeight = 0;
         
        try {
            InputStream in = new FileInputStream(filename);
            
            PNGDecoder decoder = new PNGDecoder(in);
             
            tWidth = decoder.getWidth();
            tHeight = decoder.getHeight();
             
            buf = ByteBuffer.allocateDirect(
                    4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
            buf.flip();
             
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
         
        int texId = GL11.glGenTextures();
        GL13.glActiveTexture(textureUnit);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
         
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
         
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, tWidth, tHeight, 0, 
                GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
         
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
         
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, 
                GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, 
                GL11.GL_LINEAR_MIPMAP_LINEAR);
         
        return texId;
    }*/
 
    public static void main(String[] args) {
        new Display().run();
    }
}

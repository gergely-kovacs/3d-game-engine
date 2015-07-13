package display;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import input.InputHandler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

import utils.Matrix4f;
import utils.Vector3f;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
 
public class Display {
	
    private long window;
    
    private int vaoID, vboVertID, vboIndID, vsID, fsID, pID, texID,
    	pMatLoc, vMatLoc, mMatLoc;
    
    private Matrix4f pMat, vMat, mMat;
    
    public static Vector3f mScale, mPos, mAng, camPos;
    
    private FloatBuffer matBuff;
    
    private GLFWKeyCallback   keyCallback;
    
    private final int WIDTH = 800, HEIGHT = 600;
    private final String TITLE = "Szakdolgozat";
 
    public void run() {
        try {
            init();
            loop();
 
            glfwDestroyWindow(window);
            keyCallback.release();
        }
        finally {
        	GL13.glActiveTexture(GL13.GL_TEXTURE0);
        	GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        	GL11.glDeleteTextures(texID);
        	GL13.glActiveTexture(0);
        	
        	GL20.glUseProgram(0);
        	GL20.glDetachShader(pID, vsID);
        	GL20.glDetachShader(pID, fsID);
        	 
        	GL20.glDeleteShader(vsID);
        	GL20.glDeleteShader(fsID);
        	GL20.glDeleteProgram(pID);
        	
        	GL30.glBindVertexArray(vaoID);
        	
        	GL20.glDisableVertexAttribArray(0);
        	GL20.glDisableVertexAttribArray(1);
        	
        	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        	GL15.glDeleteBuffers(vboVertID);
        	
        	GL30.glBindVertexArray(0);
        	GL30.glDeleteVertexArrays(vaoID);
        	
        	GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        	GL15.glDeleteBuffers(vboIndID);
        	
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
        
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }
 
    private void loop() {
    	
        float[] vertices = {
        	-0.5f, 0.5f, 0f,	0f, 0f,		// 0
        	-0.5f, -0.5f, 0f,	0f, 1f,		// 1
        	0.5f, 0.5f, 0f,		1f, 0f,		// 2
        	0.5f, -0.5f, 0f,	1f, 1f };	// 3
        
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices);
        vertexBuffer.flip();
        
        byte[] indices = {
    		0, 1, 2,
    		1, 3, 2 };
        
        ByteBuffer indexBuffer = BufferUtils.createByteBuffer(indices.length);
        indexBuffer.put(indices);
        indexBuffer.flip();
        
        vaoID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);
        
        vboVertID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboVertID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 20, 0);
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 20, 12);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        GL30.glBindVertexArray(0);
        
        mPos = new Vector3f(0f, 0f, 0f);
        mAng = new Vector3f(0f, 0f, 0f);
        mScale = new Vector3f(1.0f, 1.0f, 1.0f);
        camPos = new Vector3f(0f, 0f, -1f);
        
        vboIndID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboIndID);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        
        vsID = loadShader("res/shaders/vertexShader.glsl", GL20.GL_VERTEX_SHADER);
        fsID = loadShader("res/shaders/fragmentShader.glsl", GL20.GL_FRAGMENT_SHADER);
        
        pID = GL20.glCreateProgram();
        GL20.glAttachShader(pID, vsID);
        GL20.glAttachShader(pID, fsID);
        
        GL20.glBindAttribLocation(pID, 0, "position");
        GL20.glBindAttribLocation(pID, 1, "texture");
        
        GL20.glLinkProgram(pID);
        GL20.glValidateProgram(pID);
        
        mMatLoc = GL20.glGetUniformLocation(pID, "model");
        vMatLoc = GL20.glGetUniformLocation(pID, "view");
        pMatLoc = GL20.glGetUniformLocation(pID, "projection");
        
        texID = loadTexture("res/textures/Texture1.png", GL13.GL_TEXTURE0);
        
        mMat = new Matrix4f();
        vMat = new Matrix4f();
        pMat = new Matrix4f();
        
        pMat.perspective(3.1415926535f / 180f * 75f, (float) WIDTH / (float) HEIGHT, 0.1f, 100f);
        
        matBuff = BufferUtils.createFloatBuffer(16);
        
        while ( glfwWindowShouldClose(window) == GL_FALSE ) {
            vMat = new Matrix4f();
            mMat = new Matrix4f();
            
            vMat.translate(camPos.x, camPos.y, camPos.z);
             
            mMat.rotateZ((float) (3.1415926535f / 180f * mAng.z));
            mMat.rotateY((float) (3.1415926535f / 180f * mAng.y));
            mMat.rotateX((float) (3.1415926535f / 180f * mAng.x));
            mMat.translate(mPos.x, mPos.y, mPos.z);
            mMat.scale(mScale.x, mScale.y, mScale.z);
             
            GL20.glUseProgram(pID);
             
            pMat.get(0, matBuff);
            GL20.glUniformMatrix4fv(pMatLoc, false, matBuff);
            vMat.get(0, matBuff);
            GL20.glUniformMatrix4fv(vMatLoc, false, matBuff);
            mMat.get(0, matBuff);
            GL20.glUniformMatrix4fv(mMatLoc, false, matBuff);
             
            GL20.glUseProgram(0);
        	
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            GL20.glUseProgram(pID);
            
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
            
            GL30.glBindVertexArray(vaoID);
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboIndID);
            
            GL11.glDrawElements(GL_TRIANGLES, indices.length, GL11.GL_UNSIGNED_BYTE, 0);
            
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
            
            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL30.glBindVertexArray(0);
            
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
            GL13.glActiveTexture(0);
            
            GL20.glUseProgram(0);
            
            glfwSwapBuffers(window);
 
            glfwPollEvents();
        }
    }
    
    private int loadShader(String filename, int type) {
        StringBuilder shaderSource = new StringBuilder();
        int shaderID = 0;
         
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file.");
            e.printStackTrace();
            System.exit(-1);
        }
         
        shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
         
        return shaderID;
    }
    
    private int loadTexture(String filename, int textureUnit) {
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
    }
 
    public static void main(String[] args) {
        new Display().run();
    }
    
}

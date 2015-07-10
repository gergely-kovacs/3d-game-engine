package display;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
 
public class Display {
	
    private GLFWErrorCallback errorCallback;
    
    private long window;
    
    private int vaoID, vboPosID, vboColID, vsID, fsID, pID;
 
    public void run() {
        try {
            init();
            loop();
 
            glfwDestroyWindow(window);
        }
        finally {
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
        	GL15.glDeleteBuffers(vboColID);
        	GL15.glDeleteBuffers(vboPosID);
        	
        	GL30.glBindVertexArray(0);
        	GL30.glDeleteVertexArrays(vaoID);
        	
            glfwTerminate();
            errorCallback.release();
        }
    }
 
    private void init() {
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
 
        if ( glfwInit() != GL11.GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
 
        int WIDTH = 800;
        int HEIGHT = 600;
 
        window = glfwCreateWindow(WIDTH, HEIGHT, "Graphics tests!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        
        glfwSetWindowPos(
            window,
            (GLFWvidmode.width(vidmode) - WIDTH) / 2,
            (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );
        
        glfwMakeContextCurrent(window);
    }
 
    private void loop() {
        GLContext.createFromCurrent();
 
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        
        float[] vertices = { -1f, 1f, 0f, 1f,
        		-1f, -1f, 0f, 1f,
        		0f, 0f, 0f, 1f };
        
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices);
        vertexBuffer.flip();
        
        float[] colors = { 1f, 0f, 0f, 1f,
        		0f, 0f, 1f, 1f,
        		0f, 1f, 0f, 1f };
        
        FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(colors.length);
        colorBuffer.put(vertices);
        colorBuffer.flip();
        
        vaoID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);
        
        vboPosID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboPosID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        vboColID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboColID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        GL30.glBindVertexArray(0);
        
        vsID = loadShader("vertexShader.glsl", GL20.GL_VERTEX_SHADER);
        fsID = loadShader("fragmentShader.glsl", GL20.GL_FRAGMENT_SHADER);
        
        pID = GL20.glCreateProgram();
        GL20.glAttachShader(pID, vsID);
        GL20.glAttachShader(pID, fsID);
        
        GL20.glBindAttribLocation(pID, 0, "in_Position");
        GL20.glBindAttribLocation(pID, 1, "in_Color");
        
        GL20.glLinkProgram(pID);
        GL20.glValidateProgram(pID);
        
        while ( glfwWindowShouldClose(window) == GL_FALSE ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            GL20.glUseProgram(pID);
            
            GL30.glBindVertexArray(vaoID);
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            
            GL11.glDrawArrays(GL_TRIANGLES, 0, 3);
            
            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL30.glBindVertexArray(0);
            
            GL20.glUseProgram(0);
            
            glfwSwapBuffers(window);
 
            glfwPollEvents();
        }
    }
    
    public int loadShader(String filename, int type) {
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
 
    public static void main(String[] args) {
        new Display().run();
    }
    
}

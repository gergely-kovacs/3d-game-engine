package world;

import java.util.ArrayList;

import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import objects.Camera;
import objects.Model;
import objects.Shader;
import objects.ShaderProgram;
import objects.Texture;
import objects.TexturedModel;
import objects.lights.DiffuseLight;
import util.maths.Vec3f;

public class World {
	
	public static ShaderProgram program;
	public static TimeManager timeMan;
	public static Camera camera;
    public static DiffuseLight sun;
    public static TexturedModel nyuszi;
	
	public static void init() {
		int vsID = new Shader("res/shaders/vertexShader.glsl", GL20.GL_VERTEX_SHADER).getShaderId();
        int fsID = new Shader("res/shaders/fragmentShader.glsl", GL20.GL_FRAGMENT_SHADER).getShaderId();
    	
        ArrayList<String> attribs = new ArrayList<String>();
        attribs.add("vertexPosition");
        attribs.add("texture");
        attribs.add("vertexNormal");
    	program = new ShaderProgram(vsID, fsID, attribs);
    	
        camera = new Camera(new Vec3f(0.0f, 0.0f, 0.5f), 0f, 0f, 0f);
        
        sun = new DiffuseLight(new Vec3f(-100.0f, 0.0f, 25.0f), new Vec3f(0.0f, 0.0f, 0.0f));
        
        timeMan = new TimeManager(24);
    	
    	Model nyusziModel = new Model("res/models/bunny2.obj");
    	Texture nyusziDiffuse = new Texture("res/textures/bunny_diffuse.png", GL13.GL_TEXTURE0);
    	nyuszi = new TexturedModel(nyusziModel, nyusziDiffuse,
			new Vec3f(0f, 0f, 0f), new Vec3f(0f, 0f, 0f), new Vec3f(1.0f, 1.0f, 1.0f),
			5f, 0.05f);
	}

}

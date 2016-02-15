package world;

import java.util.ArrayList;

import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import objects.Camera;
import objects.CubemapTexture;
import objects.Model;
import objects.Shader;
import objects.ShaderProgram;
import objects.Skybox;
import objects.Texture;
import objects.TexturedModel;
import objects.lights.DiffuseLight;
import util.maths.Vec3f;

public class World {
	
	public static ShaderProgram texturedModelSp, skyboxSp;
	public static TimeManager day;
	public static Camera camera;
    public static DiffuseLight sun;
    public static TexturedModel nyuszi;
    public static Skybox skybox;
	
	public static void init() {
		int texturedModelVs = new Shader("res/shaders/texturedModelVs.glsl", GL20.GL_VERTEX_SHADER).getShaderId();
        int texturedModelFs = new Shader("res/shaders/texturedModelFs.glsl", GL20.GL_FRAGMENT_SHADER).getShaderId();
    	
        ArrayList<String> texturedModelAttribs = new ArrayList<String>();
        texturedModelAttribs.add("vertexPosition");
        texturedModelAttribs.add("texture");
        texturedModelAttribs.add("vertexNormal");
    	texturedModelSp = new ShaderProgram(texturedModelVs, texturedModelFs, texturedModelAttribs);
    	
    	int skyboxVs = new Shader("res/shaders/skyboxVs.glsl", GL20.GL_VERTEX_SHADER).getShaderId();
    	int skyboxFs = new Shader("res/shaders/skyboxFs.glsl", GL20.GL_FRAGMENT_SHADER).getShaderId();
    	
    	ArrayList<String> skyboxAttribs = new ArrayList<String>();
    	skyboxAttribs.add("vertexPosition");
    	skyboxSp = new ShaderProgram(skyboxVs, skyboxFs, skyboxAttribs);
    	
        camera = new Camera(new Vec3f(0.0f, 0.0f, 0.9f), 0.0f, 0.0f, 0.0f, 45.0f); // TODO: fix initial camera direction
        
        sun = new DiffuseLight(new Vec3f(0.0f, 0.0f, 0.0f), new Vec3f(0.0f, 0.0f, 0.0f)); // TODO: give diffuse lights a model and texture?
        
        day = new TimeManager(36.0f);
    	
    	Model nyusziModel = new Model("res/models/bunny2.obj");
    	Texture nyusziDiffuse = new Texture("res/textures/bunny_diffuse.png", GL13.GL_TEXTURE0);
    	nyuszi = new TexturedModel(nyusziModel, nyusziDiffuse,
			new Vec3f(0f, 0f, 0f), new Vec3f(0f, 0f, 0f), new Vec3f(1.0f, 1.0f, 1.0f),
			5f, 0.05f);
    	
    	String[] cubeMapTextureFiles = new String[6];
    	cubeMapTextureFiles[0] = "res/textures/skybox/right.png";
    	cubeMapTextureFiles[1] = "res/textures/skybox/left.png";
    	cubeMapTextureFiles[2] = "res/textures/skybox/top.png";
    	cubeMapTextureFiles[3] = "res/textures/skybox/bottom.png";
    	cubeMapTextureFiles[4] = "res/textures/skybox/back.png";
    	cubeMapTextureFiles[5] = "res/textures/skybox/front.png";
    	CubemapTexture skyboxTexture = new CubemapTexture(cubeMapTextureFiles, GL13.GL_TEXTURE0);
    	skybox = new Skybox(skyboxTexture); // TODO: fix skybox view matrix (so that it moves with the camera), get a skybox with no sun texture
	}

}

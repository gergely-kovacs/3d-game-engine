package world;

import org.lwjgl.opengl.GL13;

import objects.Camera;
import objects.CubemapTexture;
import objects.Model;
import objects.MovableEntity;
import objects.Skybox;
import objects.Texture;
import objects.lights.DiffuseLight;
import objects.terrain.Terrain;
import util.maths.Vec3f;

public class World {
	
	public static final float GRAVITY = -0.5f;
	
	public static TimeManager day;
	public static Camera camera;
    public static DiffuseLight sun;
    public static MovableEntity nyuszi;
    public static Skybox skybox;
    public static Terrain terrain;
	
	public static void init() {
        camera = new Camera(new Vec3f(0.0f, 5.0f, 5.0f), 0.0f, 0.0f, 0.0f, 45.0f);
        
        sun = new DiffuseLight(new Vec3f(0.0f, 0.0f, 0.0f), new Vec3f(0.0f, 0.0f, 0.0f)); // TODO: give diffuse lights a model and texture?
        
        day = new TimeManager(72.0f);
    	
    	nyuszi = new MovableEntity(new Model("res/models/bunny3.obj"),
			new Texture("res/textures/bunny_diffuse.png", GL13.GL_TEXTURE0),
			new Vec3f(0f, 0.5f, 0.0f), new Vec3f(0.0f, 180.0f, 0.0f), Vec3f.UNIT_VECTOR,
			5f, 0.05f);
    	
    	String[] cubeMapTextureFiles = new String[6];
    	cubeMapTextureFiles[0] = "res/textures/skybox/right.png";
    	cubeMapTextureFiles[1] = "res/textures/skybox/left.png";
    	cubeMapTextureFiles[2] = "res/textures/skybox/top.png";
    	cubeMapTextureFiles[3] = "res/textures/skybox/bottom.png";
    	cubeMapTextureFiles[4] = "res/textures/skybox/back.png";
    	cubeMapTextureFiles[5] = "res/textures/skybox/front.png";
    	CubemapTexture skyboxTexture = new CubemapTexture(cubeMapTextureFiles, GL13.GL_TEXTURE0);
    	skybox = new Skybox(skyboxTexture); // TODO: get a skybox without sun texture, maybe even without clouds
    	
    	terrain = new Terrain();
	}

}

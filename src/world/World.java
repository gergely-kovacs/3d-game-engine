package world;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL13;

import display.rendering.Camera;
import objects.CubemapTexture;
import objects.Model;
import objects.Texture;
import objects.entities.MovableEntity;
import objects.lights.ModelledLightSource;
import objects.skybox.Skybox;
import objects.terrain.Terrain;
import util.maths.Vec3f;

public class World {
	
	public static final float GRAVITY = -0.5f;
	
	public static TimeManager day;
	public static Camera camera;
    public static ModelledLightSource sun;
    public static List<MovableEntity> movableEntities;
    public static Skybox skybox;
    public static Terrain terrain;
	
	public static void init() {
        camera = new Camera(new Vec3f(0.0f, 1.0f, 10.0f), 0.0f, 0.0f, 0.0f, 30.0f);
        
        sun = new ModelledLightSource(new Model("res/models/sun.obj"),
        		new Texture("res/textures/sun.png", GL13.GL_TEXTURE0),
        		new Vec3f(0.0f, 0.0f, 0.0f), new Vec3f(0.0f, 0.0f, 0.0f));
        
        day = new TimeManager(48.0f);
    	
        movableEntities = new ArrayList<>();
        
        MovableEntity ghost = new MovableEntity(new Model("res/models/ghost.obj"),
			new Texture("res/textures/ghost_diffuse.png", GL13.GL_TEXTURE0),
			new Vec3f(0f, 0.0f, 0.0f), Vec3f.NULL_VECTOR, new Vec3f(0.1f, 0.1f, 0.1f),
			1.0f, 0.25f, 0.75f);
    	
    	movableEntities.add(ghost);
    	
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

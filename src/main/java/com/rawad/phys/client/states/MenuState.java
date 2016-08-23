package com.rawad.phys.client.states;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import com.rawad.gamehelpers.client.gamestates.State;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.client.Client;
import com.rawad.phys.client.renderengine.DebugRender;
import com.rawad.phys.client.renderengine.Texture;
import com.rawad.phys.client.renderengine.WorldRender;
import com.rawad.phys.entity.EEntity;
import com.rawad.phys.entity.RenderingComponent;
import com.rawad.phys.entity.TransformComponent;
import com.rawad.phys.fileparser.ObjFileParser;
import com.rawad.phys.game.RenderingSystem;
import com.rawad.phys.loader.Loader;
import com.rawad.phys.math.Vector3f;

public class MenuState extends State {
	
	// Define renderers up here, initialize and add them in constructor.
	
	private final float SPEED = 1.0f;
	
	private Entity camera;
	
	private Entity crate;
	
	private Texture texture;
	
	private GLFWKeyCallbackI keyCallback;
	
	private float rotation;
	
	@Override
	public void init() {
		
		camera = Entity.createEntity(EEntity.CAMERA);
		
		crate = Entity.createEntity(EEntity.CRATE);
		
		Client client = game.getProxies().get(Client.class);
		Loader loader = client.getLoaders().get(Loader.class);
		
		ObjFileParser objFileParser = client.getFileParsers().get(ObjFileParser.class);
		
		texture = loader.loadTexture("monkey");
		
		crate.getComponent(RenderingComponent.class).setTexture(texture);
		crate.getComponent(RenderingComponent.class).setModel(loader.loadModel(objFileParser, "monkey"));
		// TODO: Properly export monkey model. Add key callback for moving model (wasd -> move, urdl arrows -> rotate)
		
		TransformComponent crateTransform = crate.getComponent(TransformComponent.class);
		crateTransform.setPosition(new Vector3f(0f, 0f, -3.5f));
		crateTransform.setRotationAxis(new Vector3f(1f, 1f, 0));
		
		WorldRender worldRender = new WorldRender(camera);
		
		masterRender.getRenders().put(worldRender);
		masterRender.getRenders().put(new DebugRender());
		
		gameSystems.put(new RenderingSystem(worldRender));
		
		rotation = 0;
		
		keyCallback = (window, key, scancode, action, mods) -> {
			
			Vector3f position = crateTransform.getPosition();
			Vector3f scale = crateTransform.getScale();
			float rot = crateTransform.getRotation();
			
			if(action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT)
			switch(key) {
			
			case GLFW.GLFW_KEY_W:
				position = position.add(new Vector3f(0, SPEED, 0));
				break;
				
			case GLFW.GLFW_KEY_A:
				position = position.add(new Vector3f(-SPEED, 0, 0));
				break;
				
			case GLFW.GLFW_KEY_S:
				position = position.add(new Vector3f(0, -SPEED, 0));
				break;
				
			case GLFW.GLFW_KEY_D:
				position = position.add(new Vector3f(SPEED, 0, 0));
				break;
				
			case GLFW.GLFW_KEY_UP:
				rotation -= SPEED / 5f;
				break;
				
			case GLFW.GLFW_KEY_LEFT:
				rot -= SPEED;
				break;
				
			case GLFW.GLFW_KEY_DOWN:
				rotation += SPEED / 5f;
				break;
				
			case GLFW.GLFW_KEY_RIGHT:
				rot += SPEED;
				break;
				
			case GLFW.GLFW_KEY_EQUAL:
				scale = scale.add(new Vector3f(SPEED, SPEED, SPEED));
				break;
				
			case GLFW.GLFW_KEY_MINUS:
				scale = scale.subtract(new Vector3f(SPEED, SPEED, SPEED));
				break;
				
			}
			
			crateTransform.setPosition(position);
			crateTransform.setScale(scale);
			crateTransform.setRotationAxis(new Vector3f(0, (float) Math.cos(rotation), (float) Math.sin(rotation)));
			crateTransform.setRotation(rot);
			
//			Vector3f rotationAxis = crateTransform.getRotationAxis();
//			Logger.log(Logger.DEBUG, "new pos: (" + position.x + ", " + position.y + ", " + position.z + "), "
//					+ "Rotation axis: (" + rotationAxis.x + ", " + rotationAxis.y + ", " + rotationAxis.z + "), "
//							+ "Rotation: " + rot);
			
		};
		
	}
	
	@Override
	public void terminate() {
		texture.delete();
	}
	
	@Override
	public void onActivate() {
		
		game.getProxies().get(Client.class).getWindow().setKeyCallback(keyCallback);
		
		world.addEntity(camera);
		world.addEntity(crate);
		
	}
	
	@Override
	public void onDeactivate() {
		
	}
	
}

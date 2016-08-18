package com.rawad.phys.client.states;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

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
	
	private Entity camera;
	
	private Entity crate;
	
	private Texture texture;
	
	private GLFWMouseButtonCallback mouseButtonCallback;
	private GLFWCursorPosCallback cursorPosCallback;
	
	private boolean dragging;
	
	@Override
	public void init() {
		
		camera = Entity.createEntity(EEntity.CAMERA);
		
		crate = Entity.createEntity(EEntity.CRATE);
		
		Client client = game.getProxies().get(Client.class);
		Loader loader = client.getLoaders().get(Loader.class);
		
		ObjFileParser objFileParser = client.getFileParsers().get(ObjFileParser.class);
		
		texture = loader.loadTexture("unknown");
		
		crate.getComponent(RenderingComponent.class).setTexture(texture);
		crate.getComponent(RenderingComponent.class).setModel(loader.loadModel(objFileParser, "cube"));
		
		WorldRender worldRender = new WorldRender(camera);
		
		masterRender.getRenders().put(worldRender);
		masterRender.getRenders().put(new DebugRender());
		
		gameSystems.put(new RenderingSystem(worldRender));
		
		dragging = false;
		
		mouseButtonCallback = new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				
				dragging = button == GLFW.GLFW_MOUSE_BUTTON_LEFT && action == GLFW.GLFW_PRESS;
				
			}
		};
		
		cursorPosCallback = new GLFWCursorPosCallback() {
			
			private double prevX = 0;
			private double prevY = 0;
			
			@Override
			public void invoke(long window, double posX, double posY) {
				
				if(dragging) {
					
					float dx = (float) (posX - prevX);
					float dy = (float) (posY - prevY);
					
					crate.getComponent(TransformComponent.class).getRotation().add(new Vector3f(dy, dx, 0));
//					worldRender.setModelMatrix(tmRender.getModelMatrix()
//							.multiply(Matrix4f.rotate(dx, 0, 1, 0)
//							.multiply(Matrix4f.rotate(dy, 1, 0, 0))));
					
				}
				
				prevX = posX;
				prevY = posY;
				
			}
			
		};
		
	}
	
	@Override
	public void terminate() {
		texture.delete();
	}
	
	@Override
	public void onActivate() {
		
		Client client = game.getProxies().get(Client.class);
		
		GLFW.glfwSetMouseButtonCallback(client.getWindow().getId(), mouseButtonCallback);
		GLFW.glfwSetCursorPosCallback(client.getWindow().getId(), cursorPosCallback);
		
		world.addEntity(camera);
		world.addEntity(crate);
		
	}
	
	@Override
	public void onDeactivate() {
		
	}
	
}

package com.rawad.phys.client.states;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import com.rawad.gamehelpers.client.gamestates.State;
import com.rawad.phys.client.Client;
import com.rawad.phys.client.renderengine.Texture;
import com.rawad.phys.client.renderengine.TexturedModelRender;
import com.rawad.phys.fileparser.ObjFileParser;
import com.rawad.phys.loader.Loader;
import com.rawad.phys.math.Matrix4f;

public class MenuState extends State {
	
	// Define renderers up here, initialize and add them in constructor.
	
	private Texture texture;
	
	private GLFWMouseButtonCallback mouseButtonCallback;
	private GLFWCursorPosCallback cursorPosCallback;
	
	private boolean dragging;
	
	@Override
	public void init() {
		
		TexturedModelRender tmRender = new TexturedModelRender();
		masterRender.getRenders().put(tmRender);
		
		mouseButtonCallback = new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				
				dragging = button == GLFW.GLFW_MOUSE_BUTTON_LEFT && action == GLFW.GLFW_PRESS;
				
			}
		};
		
		dragging = false;
		
		cursorPosCallback = new GLFWCursorPosCallback() {
			
			private double prevX = 0;
			private double prevY = 0;
			
			@Override
			public void invoke(long window, double posX, double posY) {
				
				if(dragging) {
					
					float dx = (float) (posX - prevX);
					float dy = (float) - (posY - prevY);// - in front because OpenGL coordinate system.
					
					tmRender.setModelMatrix(tmRender.getModelMatrix()
							.multiply(Matrix4f.rotate(dx, 1, 0, 0)
							.multiply(Matrix4f.rotate(dy, 0, 1, 0))));
					
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
		Loader loader = client.getLoaders().get(Loader.class);
		
		GLFW.glfwSetMouseButtonCallback(client.getWindow().getId(), mouseButtonCallback);
		GLFW.glfwSetCursorPosCallback(client.getWindow().getId(), cursorPosCallback);
		
		ObjFileParser objFileParser = client.getFileParsers().get(ObjFileParser.class);
		
		texture = loader.loadTexture("unknown");
		
		masterRender.getRenders().get(TexturedModelRender.class).setModel(loader.loadModel(objFileParser, "cube"), 
				texture);
		
	}
	
	@Override
	public void onDeactivate() {
		
	}
	
}

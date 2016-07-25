package com.rawad.phys.client.states;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import com.rawad.phys.client.fileparser.ObjFileParser;
import com.rawad.phys.client.graphics.Texture;
import com.rawad.phys.client.renderengine.TexturedModelRenderer;
import com.rawad.phys.loader.Loader;
import com.rawad.phys.math.Matrix4f;

public class MenuState extends State {
	
	// Define renderers up here, initialize and add them in constructor.
	
	private Texture texture;
	
	private GLFWCursorPosCallback mouseCallback;
	
	private Matrix4f view;
	
	public MenuState() {
		super();
		
		view = new Matrix4f();
		
		TexturedModelRenderer tmRenderer = new TexturedModelRenderer();
		masterRenderer.getRenderers().put(tmRenderer);
		
		mouseCallback = new GLFWCursorPosCallback() {
			
			private double prevPosX = 0;
			private double prevPosY = 0;
			
			private boolean drag = false;
			
			@Override
			public void invoke(long window, double posX, double posY) {
				
				drag = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
				
				if(drag) {
					
					double dx = posX - prevPosX;
					double dy = posY - prevPosY;
					
				}
				
				prevPosX = posX;
				prevPosY = posY;
				
			}
			
		};
		
	}
	
	@Override
	public void update() {
		
	}
	
	/**
	 * @see com.rawad.phys.client.states.State#onActive()
	 */
	@Override
	public void onActive() {
		
		GLFW.glfwSetCursorPosCallback(sm.getWindow().getId(), mouseCallback);
		
		ObjFileParser objFileParser = new ObjFileParser();
		
		texture = Loader.loadTexture("unknown");
		
		masterRenderer.getRenderers().get(TexturedModelRenderer.class).setModel(Loader.loadModel(objFileParser, "cube"), 
				texture);
		
	}
	
	@Override
	public void onDeactive() {
		super.onDeactive();
		
		texture.delete();
		
	}
	
}

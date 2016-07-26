package com.rawad.phys.client.states;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import com.rawad.phys.client.fileparser.ObjFileParser;
import com.rawad.phys.client.graphics.Texture;
import com.rawad.phys.client.renderengine.TexturedModelRenderer;
import com.rawad.phys.loader.Loader;
import com.rawad.phys.math.Vector2f;
import com.rawad.phys.math.Vector3f;

public class MenuState extends State {
	
	// Define renderers up here, initialize and add them in constructor.
	
	private Texture texture;
	
	private GLFWMouseButtonCallback mouseButtonCallback;
	private GLFWCursorPosCallback cursorPosCallback;
	
	private boolean dragging = false;
	private boolean startedDragging = false;
	
	public MenuState() {
		super();
		
		TexturedModelRenderer tmRenderer = new TexturedModelRenderer();
		masterRenderer.getRenderers().put(tmRenderer);
		
		mouseButtonCallback = new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				
				dragging = button == GLFW.GLFW_MOUSE_BUTTON_LEFT && action == GLFW.GLFW_PRESS;
				
			}
		};
		
		cursorPosCallback = new GLFWCursorPosCallback() {
			
			private double prevX = 0;
			private double prevY = 0;
			
			private double startX = 0;
			private double startY = 0;
			
			@Override
			public void invoke(long window, double posX, double posY) {
				
				if(startedDragging) {
					startX = posX;
					startY = posY;
					
					prevX = posX;
					prevY = posY;
					
					startedDragging = false;
					
				}
				
				if(dragging) {
					
					final float smooth = 10f;
					
					float dx = (float) (posX - prevX);
					float dy = (float) - (posY - prevY);// - in front because OpenGL coordinate system.
					
					float distanceX = (float) (posX - startX) / smooth;
					float distanceY = (float) - (posY - startY) / smooth;
					
					Vector2f rotateDirection = new Vector2f(distanceX, distanceY);
					
					Vector3f rotationAxis = new Vector3f(-dy, dx, 0f);// or (dy, -dx). Perpendicular to rotateDirection.
					
					tmRenderer.setRotationAxis(rotationAxis);
					tmRenderer.setAngle(rotateDirection.length() + tmRenderer.getAngle());
					
				}
				
				prevX = posX;
				prevY = posY;
				
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
		
		GLFW.glfwSetMouseButtonCallback(sm.getWindow().getId(), mouseButtonCallback);
		GLFW.glfwSetCursorPosCallback(sm.getWindow().getId(), cursorPosCallback);
		
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

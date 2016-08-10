package com.rawad.phys.client.states;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import com.rawad.gamehelpers.client.gamestates.State;
import com.rawad.phys.client.graphics.Texture;
import com.rawad.phys.client.renderengine.TexturedModelRender;
import com.rawad.phys.fileparser.ObjFileParser;
import com.rawad.phys.loader.Loader;
import com.rawad.phys.math.Matrix4f;
import com.rawad.phys.math.Vector2f;
import com.rawad.phys.math.Vector4f;

public class MenuState extends State {
	
	// Define renderers up here, initialize and add them in constructor.
	
	private Texture texture;
	
	private GLFWMouseButtonCallback mouseButtonCallback;
	private GLFWCursorPosCallback cursorPosCallback;
	
	private boolean dragging = false;
	private boolean startedDragging = false;
	
	public MenuState() {
		super();
		
		TexturedModelRender tmRenderer = new TexturedModelRender();
		masterRender.getRenders().put(tmRenderer);
		
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
					startX = posX + 1;
					startY = posY + 1;
					
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
					
					Vector4f rotationAxis = new Vector4f(-dy, dx, 0f, 0f);// Perpendicular to rotateDirection.
					
					tmRenderer.setModelMatrix(tmRenderer.getModelMatrix().multiply(Matrix4f.rotate(rotateDirection
							.length() / 10f, rotationAxis.x, rotationAxis.y, rotationAxis.z)));
					
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
		
		masterRenderer.getRenderers().get(TexturedModelRender.class).setModel(Loader.loadModel(objFileParser, "cube"), 
				texture);
		
	}
	
	@Override
	public void onDeactive() {
		super.onDeactive();
		
		texture.delete();
		
	}
	
}

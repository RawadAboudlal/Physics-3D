package com.rawad.phys.client.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseInputCallback extends GLFWMouseButtonCallback {
	
	private InputBindings bindings;
	
	public MouseInputCallback(InputBindings bindings) {
		super();
		
		this.bindings = bindings;
		
	}
	
	@Override
	public void invoke(long window, int button, int action, int mods) {
		
		bindings.setAction(bindings.get(button, mods), action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT);
		
	}
	
}

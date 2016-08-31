package com.rawad.phys.client.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

public class KeyInputCallback implements GLFWKeyCallbackI {
	
	private InputBindings bindings;
	
	public KeyInputCallback (InputBindings bindings) {
		super();
		
		this.bindings = bindings;
		
	}
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		
		bindings.setAction(bindings.get(key, mods), action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT);
		
	}
	
}

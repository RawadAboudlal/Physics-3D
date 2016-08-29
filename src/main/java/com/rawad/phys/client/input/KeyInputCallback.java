package com.rawad.phys.client.input;

import java.util.HashMap;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

public class KeyInputCallback implements GLFWKeyCallbackI {
	
	private static KeyInputCallback instance;
	
	private InputBindings bindings = InputBindings.instance();
	
	private HashMap<InputAction, Boolean> actions = bindings.getActions();
	
	private KeyInputCallback () {}
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		
		actions.put(bindings.get(key, mods), action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT);
		
	}
	
	public static KeyInputCallback instance() {
		
		if(instance == null) instance = new KeyInputCallback();
		
		return instance;
		
	}
	
}

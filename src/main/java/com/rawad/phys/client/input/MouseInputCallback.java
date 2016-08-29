package com.rawad.phys.client.input;

import java.util.HashMap;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseInputCallback extends GLFWMouseButtonCallback {
	
	private static MouseInputCallback instance;
	
	private InputBindings bindings = InputBindings.instance();
	
	private HashMap<InputAction, Boolean> actions = bindings.getActions();
	
	private MouseInputCallback() {}
	
	@Override
	public void invoke(long window, int button, int action, int mods) {
		
		actions.put(bindings.get(button, mods), action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT);
		
	}
	
	public static MouseInputCallback instance() {
		
		if(instance == null) instance = new MouseInputCallback();
		
		return instance;
		
	}
	
}

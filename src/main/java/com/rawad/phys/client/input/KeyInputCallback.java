package com.rawad.phys.client.input;

import java.util.HashMap;

import org.lwjgl.glfw.GLFWKeyCallbackI;

public class KeyInputCallback implements GLFWKeyCallbackI {
	
	private static KeyInputCallback instance;
	
	private final HashMap<InputAction, Boolean> actions = new HashMap<InputAction, Boolean>();
	
	private KeyInputCallback () {}
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		
		
		
	}
	
	public static KeyInputCallback instance() {
		
		if(instance == null) instance = new KeyInputCallback();
		
		return instance;
		
	}
	
}

package com.rawad.phys.client.input;

import java.util.HashMap;

public class InputBindings {
	
	private HashMap<InputAction, Input> inputBindings = new HashMap<InputAction, Input>();
	
	public Input put(InputAction action, int button, int... modsArray) {
		
		int mods = 0;
		
		for(int mod: modsArray) {
			mods |= mod;// TODO: Check if works. www.glfw.org/docs/latest/group__mods.html
		}
		
		return inputBindings.put(action, new Input(button, mods));
		
	}
	
	public Input get(InputAction action) {
		return inputBindings.get(action);
	}
	
}

package com.rawad.phys.client.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class InputBindings {
	
	private HashMap<InputAction, Input> inputBindings = new HashMap<InputAction, Input>();
	
	private HashMap<InputAction, Boolean> actions = new HashMap<InputAction, Boolean>();
	
	public Input put(InputAction action, int button, int... modsArray) {
		
		int mods = 0;
		
		for(int mod: modsArray) mods |= mod;
		
		return inputBindings.put(action, new Input(button, mods));
		
	}
	
	public InputAction get(int button, int mods) {
		return this.get(new Input(button, mods));
	}
	
	public InputAction get(Input input) {
		
		ArrayList<InputAction> matchingKeys = new ArrayList<InputAction>();
		
		for(Entry<InputAction, Input> entry: inputBindings.entrySet()) {
			if(input.equals(entry.getValue())) matchingKeys.add(entry.getKey());
		}
		
		if(matchingKeys.isEmpty() || matchingKeys.size() > 1) return null;
		
		return matchingKeys.get(0);
		
	}
	
	public Input get(InputAction action) {
		return inputBindings.get(action);
	}
	
	public Boolean setAction(InputAction action, Boolean value) {
		return actions.put(action, value);
	}
	
	public Boolean isAction(InputAction action) {
		return actions.getOrDefault(action, false);
	}
	
}

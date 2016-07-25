package com.rawad.phys.client.states;

import com.rawad.phys.client.graphics.Window;
import com.rawad.phys.util.ClassMap;

public class StateManager extends State {
	
	private ClassMap<State> states;
	
	private State currentState;
	
	private Window window;
	
	public StateManager(Window window) {
		super();
		
		states = new ClassMap<State>();
		
		this.window = window;
		
	}
	
	public void setState(Class<? extends State> newStateId) {
		
		if(currentState != null) currentState.onDeactive();
		
		currentState = states.get(newStateId);
		currentState.onActive();
		
	}
	
	public void addState(State state) {
		if(state instanceof StateManager) return;// Don't add StateManager to StateManager...
		states.put(state);
		state.setStateManager(this);
	}
	
	@Override
	public void update() {
		currentState.update();
	}
	
	@Override
	public void render() {
		currentState.render();
	}
	
	@Override
	public void onActive() {}
	
	@Override
	public void onDeactive() {}
	
	public Window getWindow() {
		return window;
	}
	
}

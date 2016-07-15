package com.rawad.phys.client.states;

import com.rawad.phys.client.renderengine.MasterRenderer;

public abstract class State {
	
	protected StateManager sm;
	
	protected MasterRenderer masterRenderer;
	
	public State() {
		super();
		
		masterRenderer = new MasterRenderer();
		
	}
	
	public abstract void update();
	
	public void render() {
		masterRenderer.render();
	}
	
	public abstract void onActive();
	
	public void onDeactive() {
		masterRenderer.dispose();
	}
	
	protected void setStateManager(StateManager sm) {
		this.sm = sm;
	}
	
}

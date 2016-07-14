package com.rawad.phys.client.states;

import com.rawad.phys.client.renderengine.GuiRenderer;

public class MenuState extends State {
	
	// Define renderers up here, initialize and add them in constructor.
	
	public MenuState() {
		super();
		
		masterRenderer.getRenderers().put(new GuiRenderer());
		
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	public void onActive() {
	}
	
	@Override
	public void onDeactive() {
	}
	
}

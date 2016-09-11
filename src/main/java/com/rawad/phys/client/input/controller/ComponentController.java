package com.rawad.phys.client.input.controller;

import com.rawad.gamehelpers.game.entity.Component;
import com.rawad.phys.client.input.InputBindings;

public interface ComponentController {
	
	public void control(InputBindings bindings, Component comp);
	
	public Class<? extends Component> getComponentType();
	
}

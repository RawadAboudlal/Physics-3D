package com.rawad.phys.game;

import java.util.ArrayList;

import com.rawad.gamehelpers.game.GameSystem;
import com.rawad.gamehelpers.game.entity.Component;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.client.input.InputBindings;
import com.rawad.phys.client.input.controller.ComponentController;
import com.rawad.phys.client.input.controller.JumpingController;
import com.rawad.phys.client.input.controller.MovementController;
import com.rawad.phys.entity.ControllerComponent;

public class InputControlSystem extends GameSystem {
	
	private InputBindings inputBindings;
	
	private ArrayList<ComponentController> controllers;
	
	public InputControlSystem(InputBindings inputBindings) {
		super();
		
		this.inputBindings = inputBindings;
		
		compatibleComponentTypes.add(ControllerComponent.class);
		
		controllers = new ArrayList<ComponentController>();
		
		addComponentController(new MovementController()).addComponentController(new JumpingController());
		
	}
	
	@Override
	public void tick(Entity e) {
		
		for(ComponentController controller: controllers) {
			
			Component comp = e.getComponent(controller.getComponentType());
			
			if(comp != null) controller.control(inputBindings, comp);
			
		}
		
	}
	
	public InputControlSystem addComponentController(ComponentController controller) {
		
		controllers.add(controller);
		
		return this;
		
	}
	
}

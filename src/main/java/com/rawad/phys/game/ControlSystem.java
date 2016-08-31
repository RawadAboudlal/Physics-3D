package com.rawad.phys.game;

import com.rawad.gamehelpers.game.GameSystem;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.client.input.InputAction;
import com.rawad.phys.client.input.InputBindings;
import com.rawad.phys.entity.ControllerComponent;
import com.rawad.phys.entity.MovementComponent;

public class ControlSystem extends GameSystem {
	
	private InputBindings inputBindings;
	
	public ControlSystem(InputBindings inputBindings) {
		super();
		
		this.inputBindings = inputBindings;
		
		compatibleComponentTypes.add(ControllerComponent.class);
		
	}
	
	@Override
	public void tick(Entity e) {
		
		MovementComponent movementComp = e.getComponent(MovementComponent.class);
		
		if(movementComp != null) {
			// Check if proper buttons are being pressed in inputBindings' actions?
			
			movementComp.setForward(inputBindings.isAction(InputAction.FORWARD));
			movementComp.setBackward(inputBindings.isAction(InputAction.BACKWARD));
			movementComp.setRight(inputBindings.isAction(InputAction.RIGHT));
			movementComp.setLeft(inputBindings.isAction(InputAction.LEFT));
			
		}
		
	}
	
}

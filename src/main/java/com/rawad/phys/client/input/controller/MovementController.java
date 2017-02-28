package com.rawad.phys.client.input.controller;

import com.rawad.gamehelpers.game.entity.Component;
import com.rawad.phys.client.input.InputAction;
import com.rawad.phys.client.input.InputBindings;
import com.rawad.phys.entity.MovementComponent;

public class MovementController implements ComponentController {
	
	@Override
	public void control(InputBindings bindings, Component comp) {
		
		MovementComponent movementComp = (MovementComponent) comp;
		
		movementComp.setForward(bindings.isAction(InputAction.FORWARD));
		movementComp.setBackward(bindings.isAction(InputAction.BACKWARD));
		movementComp.setRight(bindings.isAction(InputAction.RIGHT));
		movementComp.setLeft(bindings.isAction(InputAction.LEFT));
		
	}
	
	@Override
	public Class<? extends Component> getComponentType() {
		return MovementComponent.class;
	}
	
}

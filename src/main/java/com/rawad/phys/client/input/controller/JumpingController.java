package com.rawad.phys.client.input.controller;

import com.rawad.gamehelpers.game.entity.Component;
import com.rawad.phys.client.input.InputAction;
import com.rawad.phys.client.input.InputBindings;
import com.rawad.phys.entity.JumpingComponent;


public class JumpingController implements ComponentController {
	
	@Override
	public void control(InputBindings bindings, Component comp) {
		
		JumpingComponent jumpingComp = (JumpingComponent) comp;
		
		jumpingComp.setJumpRequested(bindings.isAction(InputAction.JUMP));
		
	}
	
	@Override
	public Class<? extends Component> getComponentType() {
		return JumpingComponent.class;
	}
	
}

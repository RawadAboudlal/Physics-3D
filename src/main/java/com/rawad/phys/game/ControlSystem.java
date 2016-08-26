package com.rawad.phys.game;

import com.rawad.gamehelpers.game.GameSystem;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.entity.ControllerComponent;

public class ControlSystem extends GameSystem {
	
	public ControlSystem() {
		super();
		
		compatibleComponentTypes.add(ControllerComponent.class);
		
	}
	
	@Override
	public void tick(Entity e) {
	}
	
}

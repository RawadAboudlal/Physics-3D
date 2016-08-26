package com.rawad.phys.game;

import com.rawad.gamehelpers.game.GameSystem;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.entity.MovementComponent;
import com.rawad.phys.entity.TransformComponent;

public class MovementSystem extends GameSystem {
	
	private float gravity = 1f;
	
	public MovementSystem() {
		super();
		
		compatibleComponentTypes.add(TransformComponent.class);
		compatibleComponentTypes.add(MovementComponent.class);
		
	}
	
	@Override
	public void tick(Entity e) {
		
		MovementComponent movementComp = e.getComponent(MovementComponent.class);
		
	}
	
}

package com.rawad.phys.game;

import com.rawad.gamehelpers.game.GameSystem;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.entity.MovementComponent;
import com.rawad.phys.entity.TransformComponent;

public class PhysicsSystem extends GameSystem {
	
//	private float gravity = 1f;// For use later.
	
	public PhysicsSystem() {
		super();
		
		compatibleComponentTypes.add(TransformComponent.class);
		compatibleComponentTypes.add(MovementComponent.class);
		
	}
	
	@Override
	public void tick(Entity e) {
		
		TransformComponent transformComp = e.getComponent(TransformComponent.class);
		MovementComponent movementComp = e.getComponent(MovementComponent.class);
		
		if(movementComp.isForward()) {
			// TODO: Get direction model is facing, make velcity in that direction and add to translate later.
		} else if(movementComp.isBackward()) {
			
		}
		
	}
	
}

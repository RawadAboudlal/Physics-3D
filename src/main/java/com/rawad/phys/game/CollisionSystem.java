package com.rawad.phys.game;

import com.rawad.gamehelpers.game.GameSystem;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.gamehelpers.game.event.Event;
import com.rawad.phys.entity.CollisionComponent;
import com.rawad.phys.entity.MovementComponent;
import com.rawad.phys.entity.TransformComponent;
import com.rawad.phys.game.event.EventType;

public class CollisionSystem extends GameSystem {
	
	public CollisionSystem() {
		super();
		
		compatibleComponentTypes.add(TransformComponent.class);
		compatibleComponentTypes.add(CollisionComponent.class);
		
	}
	
	@Override
	public void tick(Entity e) {
		
		// Get a list of all entities e can possibly, physically, collide with in the world... from somewhere...
		
		MovementComponent movementComp = e.getComponent(MovementComponent.class);
		
		if(movementComp == null) return;
		
		TransformComponent transformComp = e.getComponent(TransformComponent.class);
		CollisionComponent collisionComp = e.getComponent(CollisionComponent.class);
		
		if(transformComp.getPosition().y <= 0f) {
			
			boolean prevOnGround = collisionComp.isOnGround();
			
			collisionComp.setOnGround(true);
			
			// If not already on ground, submit an event that the entity has now hit the ground.
			if(!prevOnGround) gameEngine.submitEvent(new Event(EventType.COLLISION_GROUND, e));
			
		} else {
			collisionComp.setOnGround(false);
		}
		
	}
	
}

package com.rawad.phys.game;

import com.rawad.gamehelpers.game.GameSystem;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.gamehelpers.game.event.Event;
import com.rawad.gamehelpers.game.event.Listener;
import com.rawad.phys.entity.CollisionComponent;
import com.rawad.phys.entity.JumpingComponent;
import com.rawad.phys.entity.MovementComponent;
import com.rawad.phys.entity.TransformComponent;
import com.rawad.phys.game.event.EventType;
import com.rawad.phys.math.Vector3f;

public class JumpingSystem extends GameSystem implements Listener {
	
	private static final float JUMP_SPEED = MovementSystem.GRAVITY * 2;
	
	private static final float MAX_JUMP_SPEED = JUMP_SPEED * 5f;
	private static final float MIN_JUMP_SPEED = JUMP_SPEED / 2f;
	
	private static final float MAX_AIR_TIME = 5f;
	
	public JumpingSystem() {
		super();
		
		compatibleComponentTypes.add(TransformComponent.class);
		compatibleComponentTypes.add(MovementComponent.class);
		compatibleComponentTypes.add(JumpingComponent.class);
		
		gameEngine.registerListener(EventType.COLLISION_GROUND, this);
		
	}
	
	@Override
	public void tick(Entity e) {
		
		MovementComponent movementComp = e.getComponent(MovementComponent.class);
		JumpingComponent jumpingComp = e.getComponent(JumpingComponent.class);
		
		Vector3f velocity = movementComp.getVelocity();
		float y = velocity.y;
		
		float airTime = jumpingComp.getAirTime();
		
		if(jumpingComp.isJumpRequested() && jumpingComp.canJump() && airTime < MAX_AIR_TIME) {
			y += JUMP_SPEED;
			airTime += 1;
		}
		
//		if(!jumpingComp.canJump() && y >= MIN_JUMP) y -= JUMP_SPEED;
		
		if(airTime >= MAX_AIR_TIME){
			jumpingComp.setJump(false);
			airTime = 0;
		}
		
		y = MovementSystem.clampMovement(y, MIN_JUMP_SPEED, MAX_JUMP_SPEED);
		
		velocity.y = y;
		jumpingComp.setAirTime(airTime);
		
//		System.out.printf("Air Time: %s, Velcotiy y: %s, can jump: %s, on ground: %s\n", airTime, y, jumpingComp.canJump(),
//				e.getComponent(CollisionComponent.class).isOnGround());
		
	}
	
	@Override
	public void onEvent(Event ev) {
		
		EventType eventType = (EventType) ev.getEventType();
		
		Entity e = ev.getEntity();
		
		JumpingComponent jumpingComp = e.getComponent(JumpingComponent.class);
		
		switch(eventType) {
		
		case COLLISION_GROUND:
			CollisionComponent collisionComp = e.getComponent(CollisionComponent.class);
			
			// Once an entity hits the ground, after it was in the air, it can jump again.
			if(collisionComp.isOnGround()) jumpingComp.setJump(true);
			
		default:
			
		}
		
	}
	
}

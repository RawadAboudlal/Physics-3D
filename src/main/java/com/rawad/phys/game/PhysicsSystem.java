package com.rawad.phys.game;

import com.rawad.gamehelpers.game.GameSystem;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.entity.DirectionComponent;
import com.rawad.phys.entity.MovementComponent;
import com.rawad.phys.entity.TransformComponent;
import com.rawad.phys.math.Vector3f;

public class PhysicsSystem extends GameSystem {
	
	// TODO: Implement jumping -> MAX_JUMP_VELOCITY should turn off jumping until next time ground is hit (or something)
	
	public static final Vector3f FORWARD = new Vector3f(0, 0, -1f);
	public static final Vector3f UP = new Vector3f(0, 1f, 0);
	public static final Vector3f RIGHT = new Vector3f(1f, 0, 0);
	
	public static final Vector3f BACKWARD = FORWARD.negate();
	public static final Vector3f DOWN = UP.negate();
	public static final Vector3f LEFT = RIGHT.negate();
//	private Vector3f gravity = new Vector3f(0, -1f, 0);// For use later.// Use DOWN vector, have a scalar gravity value.
	
//	private static final float SPEED_MOVEMENT = 0.5f;
	
	public PhysicsSystem() {
		super();
		
		compatibleComponentTypes.add(TransformComponent.class);
		compatibleComponentTypes.add(MovementComponent.class);
		compatibleComponentTypes.add(DirectionComponent.class);
		
	}
	
	@Override
	public void tick(Entity e) {
		
		TransformComponent transformComp = e.getComponent(TransformComponent.class);
		MovementComponent movementComp = e.getComponent(MovementComponent.class);
		DirectionComponent directionComp = e.getComponent(DirectionComponent.class);
		
//		Vector3f velocity = new Vector3f();//movementComp.getVelocity();
		
		float x = 0f;
		float z = 0f;
		
		if(movementComp.isForward()) {
			
			z = FORWARD.z;
			
		} else if(movementComp.isBackward()) {
			
			z = BACKWARD.z;
			
		}
		
		if(movementComp.isRight()) {
			
			x = RIGHT.x;
			
		} else if(movementComp.isLeft()) {
			
			x = LEFT.x;
			
		}
		
		Vector3f forward = directionComp.getForward();
		Vector3f right = UP.cross(forward);
		
		Vector3f velocity = movementComp.getVelocity();
		
		velocity.x = x;
		velocity.z = z;
		
		velocity.set(velocity.dot(right), velocity.y, velocity.dot(forward));
		
		movementComp.setVelocity(velocity);
		transformComp.setPosition(transformComp.getPosition().add(velocity));
		
	}
	
}

package com.rawad.phys.game;

import com.rawad.gamehelpers.game.GameSystem;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.entity.MovementComponent;
import com.rawad.phys.entity.TransformComponent;
import com.rawad.phys.math.Matrix4f;
import com.rawad.phys.math.Vector3f;
import com.rawad.phys.math.Vector4f;

public class PhysicsSystem extends GameSystem {
	
	// TODO: Implement jumping -> MAX_JUMP_VELOCITY should turn off jumping until next time ground is hit (or something)
	
//	private Vector3f gravity = new Vector3f(0, -1f, 0);// For use later.
	private static final Vector3f FORWARD = new Vector3f(0, 0, 1f);
	
	private static final float SPEED_MOVEMENT = 0.5f;
	private static final float SPEED_TURN = 1.0f;
	
	public PhysicsSystem() {
		super();
		
		compatibleComponentTypes.add(TransformComponent.class);
		compatibleComponentTypes.add(MovementComponent.class);
		
	}
	
	@Override
	public void tick(Entity e) {
		
		TransformComponent transformComp = e.getComponent(TransformComponent.class);
		MovementComponent movementComp = e.getComponent(MovementComponent.class);
		
		Vector3f velocity = new Vector3f(0, 0, 0);
		
		float rotation = transformComp.getRotation();
		
		if(movementComp.isForward()) {
			velocity = new Vector3f(0, 0, SPEED_MOVEMENT);
		} else if(movementComp.isBackward()) {
			velocity = new Vector3f(0, 0, -SPEED_MOVEMENT);
		}
		
		Vector4f rotatedForward = Matrix4f.rotate(rotation, transformComp.getRotationAxis()).multiply(new 
				Vector4f(FORWARD.x, FORWARD.y, FORWARD.z, 1f));
		movementComp.setVelocity(new Vector3f(rotatedForward.x * velocity.x, rotatedForward.y * velocity.y, 
				rotatedForward.z * velocity.z));
		
		if(movementComp.isRight()) {
			transformComp.setRotation(rotation + SPEED_TURN);
		} else if(movementComp.isLeft()) {
			transformComp.setRotation(rotation - SPEED_TURN);
		}
		
		transformComp.setPosition(transformComp.getPosition().add(movementComp.getVelocity()));
		
	}
	
}

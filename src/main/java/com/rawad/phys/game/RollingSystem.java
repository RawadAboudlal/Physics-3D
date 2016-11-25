package com.rawad.phys.game;

import com.rawad.gamehelpers.game.GameSystem;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.entity.MovementComponent;
import com.rawad.phys.entity.RollingComponent;
import com.rawad.phys.entity.TransformComponent;
import com.rawad.phys.math.Quaternionf;
import com.rawad.phys.math.Vector3f;

public class RollingSystem extends GameSystem {
	
	public static final float SPEED_TO_ROLL = 15f;
	
	public RollingSystem() {
		super();
		
		compatibleComponentTypes.add(TransformComponent.class);
		compatibleComponentTypes.add(MovementComponent.class);
		compatibleComponentTypes.add(RollingComponent.class);
		
	}
	
	@Override
	public void tick(Entity e) {
		
		TransformComponent transformComp = e.getComponent(TransformComponent.class);
		MovementComponent movementComp = e.getComponent(MovementComponent.class);
		RollingComponent rollingComp = e.getComponent(RollingComponent.class);
		
		Vector3f velocity = movementComp.getVelocity();
		
		Vector3f right = PhysicsSystem.UP.cross(velocity.normalize());// Normalize to prevent weird jittering.
		
		float rollSpeed = velocity.length() * SPEED_TO_ROLL;
		float roll = rollSpeed;
		
		Quaternionf q = new Quaternionf(roll, right.x, right.y, right.z);
		Quaternionf rotation = transformComp.getRotation();
		
		rollingComp.setRoll(roll);
		transformComp.setRotation(q.multiply(rotation));// Strange things happen when other way around.
		
	}
	
}

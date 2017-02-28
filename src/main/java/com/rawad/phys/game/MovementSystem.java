package com.rawad.phys.game;

import com.rawad.gamehelpers.game.GameSystem;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.gamehelpers.game.event.Event;
import com.rawad.gamehelpers.game.event.Listener;
import com.rawad.phys.entity.CollisionComponent;
import com.rawad.phys.entity.DirectionComponent;
import com.rawad.phys.entity.MovementComponent;
import com.rawad.phys.entity.TransformComponent;
import com.rawad.phys.game.event.EventType;
import com.rawad.phys.math.Vector3f;

public class MovementSystem extends GameSystem implements Listener {
	
	public static final Vector3f FORWARD = new Vector3f(0, 0, -1f);
	public static final Vector3f UP = new Vector3f(0, 1f, 0);
	public static final Vector3f RIGHT = new Vector3f(1f, 0, 0);
	
//	public static final Vector3f BACKWARD = FORWARD.negate();
//	public static final Vector3f DOWN = UP.negate();// Use DOWN vector, *could* have a scalar gravity value.
//	public static final Vector3f LEFT = RIGHT.negate();
	
	public static final float GRAVITY = 0.8f;
	
	/** Acceleration with which entities change in velocity; &delta;v/&delta;t<sup>2</sup>. */
	private static final float ACCELERATION = 0.5f;
	
	/** Friction coefficient constant (mu) */
	private static final float FRICTION = ACCELERATION / 2f;
	
	/** F<sub>N</sub>=F<sub>g</sub> * &mu;*/
	private static final float FRICTION_FORCE = GRAVITY * FRICTION;
	
	private static final float SPEED_MAX = ACCELERATION * 3f;
	
	/** 
	 * Minimum speed of an entity before it is rounded down to 0. This should be less the frictional force to prevent 
	 * issueswith infinite jittering.
	 */
	private static final float SPEED_MIN = FRICTION_FORCE / 2;
	
	public MovementSystem() {
		super();
		
		compatibleComponentTypes.add(TransformComponent.class);
		compatibleComponentTypes.add(MovementComponent.class);
		compatibleComponentTypes.add(DirectionComponent.class);
		
		gameEngine.registerListener(EventType.COLLISION_ENTITY, this);
		gameEngine.registerListener(EventType.COLLISION_GROUND, this);
		
	}
	
	@Override
	public void tick(Entity e) {
		
		TransformComponent transformComp = e.getComponent(TransformComponent.class);
		MovementComponent movementComp = e.getComponent(MovementComponent.class);
		DirectionComponent directionComp = e.getComponent(DirectionComponent.class);
		
//		Vector3f velocity = movementComp.getAcceleration();
		Vector3f velocity = movementComp.getVelocity();
		
		CollisionComponent collisionComp = e.getComponent(CollisionComponent.class);
		
		Vector3f acceleration = new Vector3f(0f, 0f, 0f);
		
		if(movementComp.isRight()) acceleration = acceleration.add(RIGHT.scale(ACCELERATION));
		if(movementComp.isLeft()) acceleration = acceleration.subtract(RIGHT.scale(ACCELERATION));
		
		if(movementComp.isForward()) acceleration = acceleration.add(FORWARD.scale(ACCELERATION));
		if(movementComp.isBackward()) acceleration = acceleration.subtract(FORWARD.scale(ACCELERATION));
		
		float accelMagnitude = acceleration.length();
		
		// Account for moving in multiple directions at same time without changing total acceleration.
		if(accelMagnitude != 0) acceleration.set((acceleration.x / accelMagnitude) /* acceleration.x*/, acceleration.y, 
				(acceleration.z / accelMagnitude) /* acceleration.z*/);
		
		Vector3f forward = directionComp.getForward();
		Vector3f right = UP.cross(forward);
		
		
		// Account for direction entity is facing.
		acceleration.set(acceleration.dot(right), acceleration.y, acceleration.dot(forward));
		
		velocity = velocity.add(acceleration);
		
		// "Only things that can collide with the ground can be affected by its friction".
		if(collisionComp != null) {
			
			if(collisionComp.isOnGround()) {
				
				if(acceleration.x == 0) velocity.x = MovementSystem.applyFriction(velocity.x);
				if(acceleration.z == 0) velocity.z = MovementSystem.applyFriction(velocity.z);
				
			} else {
				
				if(acceleration.y == 0) velocity = velocity.subtract(UP.scale(GRAVITY));
				
			}
			
		}
		
		/*	Since SPEED_MIN < FRICTION_FORCE, this is done after applying friction. That way, velocity won't be negative
		 *  which would make the object jump back right before reaching 0.
		 */
		velocity = MovementSystem.clampMovement(velocity, SPEED_MIN, SPEED_MAX);
		
		movementComp.setVelocity(velocity);
		transformComp.setPosition(transformComp.getPosition().add(velocity));
		
		/*/
		if(collisionComp != null) {
			
			if(collisionComp.isOnGround()) {
				
				velocity.add(velocity);
			
				if(Math.abs(velocity.x) >= SPEED_MIN) velocity.x = MovementSystem.applyFriction(velocity.x);
				if(Math.abs(velocity.z) >= SPEED_MIN) velocity.z = MovementSystem.applyFriction(velocity.z);
				
				if(velocity.y < 0) velocity.y = 0;
				
			} else {
				
//				acceleration.set(-GRAVITY, -GRAVITY, -GRAVITY);
				
				velocity.y = -GRAVITY;
			}
			
		}
		
		if(movementComp.isForward()) velocity = velocity.add(FORWARD);
		if(movementComp.isBackward()) velocity = velocity.subtract(FORWARD);
		
		if(!movementComp.isForward() && !movementComp.isBackward()) velocity.z = 0f;
		
		if(movementComp.isRight()) velocity = velocity.add(RIGHT);
		if(movementComp.isLeft()) velocity = velocity.subtract(RIGHT);
		
		if(!movementComp.isRight() && !movementComp.isLeft()) velocity.x = 0f;
		
		velocity.x = MovementSystem.clampMovement(velocity.x, ACCEL_MIN, ACCEL_MAX);
		velocity.z = MovementSystem.clampMovement(velocity.z, ACCEL_MIN, ACCEL_MAX);
		
		Vector3f forward = directionComp.getForward();
		Vector3f right = UP.cross(forward);
		
//		velocity = velocity.add(velocity);
		
		velocity.set(velocity.dot(right), velocity.y, velocity.dot(forward));
		
		velocity.x = MovementSystem.clampMovement(velocity.x, SPEED_MIN, SPEED_MAX);
		velocity.z = MovementSystem.clampMovement(velocity.z, SPEED_MIN, SPEED_MAX);
		
		movementComp.setAcceleration(velocity);
		movementComp.setVelocity(velocity);
		transformComp.setPosition(transformComp.getPosition().add(velocity));
		/**/
	}
	
	@Override
	public void onEvent(Event ev) {
		
		EventType type = (EventType) ev.getEventType();
		
		Entity e = ev.getEntity();
		
		MovementComponent movementComp = e.getComponent(MovementComponent.class);
		
		switch(type) {
		
		case COLLISION_GROUND:
			
			movementComp.getAcceleration().y = 0;
			movementComp.getVelocity().y = 0;
			
			break;
		
		default:
		
		}
		
	}
	
	public static Vector3f clampMovement(Vector3f vector, final float min, final float max) {
		return new Vector3f(MovementSystem.clampMovement(vector.x, min, max), MovementSystem.clampMovement(vector.y, 
				min, max), MovementSystem.clampMovement(vector.z, min, max));
	}
	
	public static float clampMovement(float vectorPart, final float min, final float max) {
		
		final float sign = Math.signum(vectorPart);
		
		vectorPart = Math.abs(vectorPart);
		
		if(vectorPart <= min) vectorPart = 0;
		
		vectorPart = Math.min(vectorPart, max);
		
		return sign * vectorPart;
		
	}
	
	private static float applyFriction(float vectorPart) {
		
		return vectorPart - Math.signum(vectorPart) * FRICTION_FORCE;
		
	}
	
}

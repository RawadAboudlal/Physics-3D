package com.rawad.phys.entity;

import com.rawad.gamehelpers.game.entity.Component;
import com.rawad.phys.math.Vector3f;

public class MovementComponent extends Component {
	
	private Vector3f velocity = new Vector3f();
	
	private boolean gravity = true;
	
	/**
	 * @return the velocity
	 */
	public Vector3f getVelocity() {
		return velocity;
	}
	
	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}
	
	/**
	 * @return the gravity
	 */
	public boolean hasGravity() {
		return gravity;
	}
	
	/**
	 * @param gravity the gravity to set
	 */
	public void setGravity(boolean gravity) {
		this.gravity = gravity;
	}
	
	@Override
	public Component copyData(Component comp) {
		
		if(comp instanceof MovementComponent) {
			
			MovementComponent movementComp = (MovementComponent) comp;
			
			movementComp.setVelocity(getVelocity().clone());
			movementComp.setGravity(hasGravity());
			
			return movementComp;
			
		}
		
		return comp;
	}
	
}

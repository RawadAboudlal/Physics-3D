package com.rawad.phys.entity;

import com.rawad.gamehelpers.game.entity.Component;
import com.rawad.phys.math.Vector3f;

public class MovementComponent extends Component {
	
	private Vector3f velocity = new Vector3f();
	
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
	
	@Override
	public Component copyData(Component comp) {
		
		if(comp instanceof MovementComponent) {
			
			MovementComponent movementComp = (MovementComponent) comp;
			
			movementComp.setVelocity(getVelocity().clone());
			
			return movementComp;
			
		}
		
		return comp;
	}
	
}

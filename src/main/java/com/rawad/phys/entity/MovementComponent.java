package com.rawad.phys.entity;

import com.rawad.gamehelpers.game.entity.Component;
import com.rawad.phys.math.Vector3f;

public class MovementComponent extends Component {
	
	private Vector3f velocity = new Vector3f();// With our movement system, + = forward, - = backwards. 
	
	private boolean forward = false;
	private boolean backward = false;
	private boolean right = false;
	private boolean left = false;
	
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
	 * @return the forward
	 */
	public boolean isForward() {
		return forward;
	}
	
	/**
	 * @param forward the forward to set
	 */
	public void setForward(boolean forward) {
		this.forward = forward;
	}
	
	/**
	 * @return the backward
	 */
	public boolean isBackward() {
		return backward;
	}
	
	/**
	 * @param backward the backward to set
	 */
	public void setBackward(boolean backward) {
		this.backward = backward;
	}
	
	/**
	 * @return the right
	 */
	public boolean isRight() {
		return right;
	}
	
	/**
	 * @param right the right to set
	 */
	public void setRight(boolean right) {
		this.right = right;
	}
	
	/**
	 * @return the left
	 */
	public boolean isLeft() {
		return left;
	}
	
	/**
	 * @param left the left to set
	 */
	public void setLeft(boolean left) {
		this.left = left;
	}
	
	/**
	 * @return the gravity
	 */
	public boolean hasGravity() {
		return gravity;
	}
	
	/**
	 * @param gravity True if this {@code Entity} should be affected by gravity, false otherwise.
	 */
	public void setGravity(boolean gravity) {
		this.gravity = gravity;
	}
	
	@Override
	public Component copyData(Component comp) {
		
		if(comp instanceof MovementComponent) {
			
			MovementComponent movementComp = (MovementComponent) comp;
			
			movementComp.setVelocity(getVelocity().clone());
			
			movementComp.setForward(isForward());
			movementComp.setBackward(isBackward());
			movementComp.setRight(isRight());
			movementComp.setLeft(isLeft());
			
			movementComp.setGravity(hasGravity());
			
		}
		
		return comp;
	}
	
}

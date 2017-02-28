package com.rawad.phys.entity;

import com.rawad.gamehelpers.game.entity.Component;


public class JumpingComponent extends Component {
	
	/** Shows if the {@code Entity} can jump */
	private boolean jump = true;
	private boolean jumping = false;
	private boolean jumpRequested = false;
	
	private float airTime = 0f;
	
	/**
	 * @return the jump
	 */
	public boolean canJump() {
		return jump;
	}
	
	/**
	 * @param jump the jump to set
	 */
	public void setJump(boolean jump) {
		this.jump = jump;
	}
	
	/**
	 * @return the jumpRequested
	 */
	public boolean isJumpRequested() {
		return jumpRequested;
	}
	
	/**
	 * @param jumpRequested the jumpRequested to set
	 */
	public void setJumpRequested(boolean jumpRequested) {
		this.jumpRequested = jumpRequested;
	}
	
	/**
	 * @return the jumping
	 */
	public boolean isJumping() {
		return jumping;
	}
	
	/**
	 * @param jumping the jumping to set
	 */
	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}
	
	/**
	 * @return the airTime
	 */
	public float getAirTime() {
		return airTime;
	}
	
	/**
	 * @param airTime the airTime to set
	 */
	public void setAirTime(float airTime) {
		this.airTime = airTime;
	}
	
	@Override
	public Component copyData(Component comp) {
		
		if(comp instanceof JumpingComponent) {
			
			JumpingComponent jumpingComp = (JumpingComponent) comp;
			
			jumpingComp.setJumping(isJumping());
			
		}
		
		return comp;
		
	}
	
}

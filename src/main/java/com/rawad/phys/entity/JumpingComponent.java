package com.rawad.phys.entity;

import com.rawad.gamehelpers.game.entity.Component;


public class JumpingComponent extends Component {
	
	private boolean jumping = false;
	
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
	
	@Override
	public Component copyData(Component comp) {
		
		if(comp instanceof JumpingComponent) {
			
			JumpingComponent jumpingComp = (JumpingComponent) comp;
			
			jumpingComp.setJumping(isJumping());
			
		}
		
		return comp;
		
	}
	
}

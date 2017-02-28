package com.rawad.phys.entity;

import com.rawad.gamehelpers.game.entity.Component;

public class CollisionComponent extends Component {
	
	private boolean onGround = true;
	
	/**
	 * @return the onGround
	 */
	public boolean isOnGround() {
		return onGround;
	}
	
	/**
	 * @param onGround the onGround to set
	 */
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	
	@Override
	public Component copyData(Component comp) {
		
		if(comp instanceof CollisionComponent) {
			
			CollisionComponent collisionComp = (CollisionComponent) comp;
			
			collisionComp.setOnGround(isOnGround());
			
		}
		
		return comp;
		
	}
	
}

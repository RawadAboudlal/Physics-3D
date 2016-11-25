package com.rawad.phys.entity;

import com.rawad.gamehelpers.game.entity.Component;
import com.rawad.phys.math.Vector3f;

public class DirectionComponent extends Component {
	
	private Vector3f forward = new Vector3f(0f, 0f, 1f);
	
	/**
	 * @return the forward
	 */
	public Vector3f getForward() {
		return forward;
	}
	
	/**
	 * @param forward the forward to set
	 */
	public void setForward(Vector3f forward) {
		this.forward = forward;
	}
	
	@Override
	public Component copyData(Component comp) {
		
		if(comp instanceof DirectionComponent) {
			
			DirectionComponent directionComp = (DirectionComponent) comp;
			
			directionComp.setForward(getForward().clone());
			
		}
		
		return comp;
		
	}
	
}

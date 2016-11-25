package com.rawad.phys.entity;

import com.rawad.gamehelpers.game.entity.Component;

public class RollingComponent extends Component {
	
	private float rollSpeed = 0f;
	
	private float roll = 0f;
	
	/**
	 * @return the rollSpeed
	 */
	public float getRollSpeed() {
		return rollSpeed;
	}
	
	/**
	 * @param rollSpeed the rollSpeed to set
	 */
	public void setRollSpeed(float rollSpeed) {
		this.rollSpeed = rollSpeed;
	}
	
	/**
	 * @return the roll
	 */
	public float getRoll() {
		return roll;
	}
	
	/**
	 * @param roll the roll to set
	 */
	public void setRoll(float roll) {
		this.roll = roll;
	}
	
	@Override
	public Component copyData(Component comp) {
		
		if(comp instanceof RollingComponent) {
			
			RollingComponent rollingComp = (RollingComponent) comp;
			
			rollingComp.setRoll(getRoll());
			
		}
		
		return comp;
		
	}
	
}

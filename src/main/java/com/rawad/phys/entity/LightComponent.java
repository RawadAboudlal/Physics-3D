package com.rawad.phys.entity;

import com.rawad.gamehelpers.game.entity.Component;
import com.rawad.phys.math.Vector3f;

public class LightComponent extends Component {
	
	private Vector3f color = new Vector3f(1f, 1f, 1f);// 0.0f - 1.0f
	
	/**
	 * @return the color
	 */
	public Vector3f getColor() {
		return color;
	}
	
	/**
	 * @param color the color to set
	 */
	public void setColor(Vector3f color) {
		this.color = color;
	}
	
	@Override
	public Component copyData(Component comp) {
		
		if(comp instanceof LightComponent) {
			
			LightComponent lightComp = (LightComponent) comp;
			
			lightComp.setColor(getColor().clone());
			
		}
		
		return comp;
		
	}
	
}

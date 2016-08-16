package com.rawad.phys.entity;

import com.rawad.gamehelpers.game.entity.Component;
import com.rawad.phys.math.Vector3f;

public class TransformComponent extends Component {
	
	private Vector3f position = new Vector3f();
	
	private Vector3f scale = new Vector3f();
	
	private Vector3f rotation = new Vector3f();
	
	/**
	 * @return the position
	 */
	public Vector3f getPosition() {
		return position;
	}
	
	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	/**
	 * @return the scale
	 */
	public Vector3f getScale() {
		return scale;
	}
	
	/**
	 * @param scale the scale to set
	 */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	/**
	 * @return the rotation
	 */
	public Vector3f getRotation() {
		return rotation;
	}
	
	/**
	 * @param rotation the rotation to set
	 */
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
	
	@Override
	public Component copyData(Component comp) {
		
		if(comp instanceof TransformComponent) {
			
			TransformComponent transformComp = (TransformComponent) comp;
			
			transformComp.setPosition(getPosition());
			transformComp.setScale(getScale());
			transformComp.setRotation(getRotation());
			
		}
		
		return comp;
	}
	
}

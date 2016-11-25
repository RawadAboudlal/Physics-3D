package com.rawad.phys.entity;

import com.rawad.gamehelpers.game.entity.Component;
import com.rawad.phys.math.Quaternionf;
import com.rawad.phys.math.Vector3f;

public class TransformComponent extends Component {
	
	private Vector3f position = new Vector3f();
	
	private Vector3f scale = new Vector3f(1f, 1f, 1f);
	
	private Quaternionf rotation = new Quaternionf(0f, 0f, 1f, 0f);
	
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
	public Quaternionf getRotation() {
		return rotation;
	}
	
	/**
	 * @param rotation the rotation to set
	 */
	public void setRotation(Quaternionf rotation) {
		this.rotation = rotation;
	}
	
	@Override
	public Component copyData(Component comp) {
		
		if(comp instanceof TransformComponent) {
			
			TransformComponent transformComp = (TransformComponent) comp;
			
			transformComp.setPosition(getPosition().clone());
			transformComp.setScale(getScale().clone());
			transformComp.setRotation(getRotation().clone());
			
		}
		
		return comp;
		
	}
	
}

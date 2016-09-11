package com.rawad.phys.entity;

import com.rawad.gamehelpers.game.entity.Component;
import com.rawad.phys.math.Matrix4f;
import com.rawad.phys.math.Vector3f;

public class TransformComponent extends Component {
	
	private Vector3f position = new Vector3f();
	
	private Vector3f scale = new Vector3f(1f, 1f, 1f);
	
	private Vector3f rotationAxis = new Vector3f(1f, 1f, 1f);
	
	private float rotation = 0f;
	
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
	 * @return the rotationAxis
	 */
	public Vector3f getRotationAxis() {
		return rotationAxis;
	}
	
	/**
	 * @param rotationAxis the rotationAxis to set
	 */
	public void setRotationAxis(Vector3f rotationAxis) {
		this.rotationAxis = rotationAxis;
	}
	
	/**
	 * @return the rotation
	 */
	public float getRotation() {
		return rotation;
	}
	
	/**
	 * @param rotation the rotation to set
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	@Override
	public Component copyData(Component comp) {
		
		if(comp instanceof TransformComponent) {
			
			TransformComponent transformComp = (TransformComponent) comp;
			
			transformComp.setPosition(getPosition().clone());
			transformComp.setScale(getScale().clone());
			transformComp.setRotationAxis(getRotationAxis().clone());
			transformComp.setRotation(getRotation());
			
		}
		
		return comp;
	}
	
	public Matrix4f toMatrix4f() {
		return Matrix4f.translate(position.x, position.y, position.z)
				.multiply(Matrix4f.rotate(rotation, rotationAxis))
				.multiply(Matrix4f.scale(scale.x, scale.y, scale.z));
	}
	
}

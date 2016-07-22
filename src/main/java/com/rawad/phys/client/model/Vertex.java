package com.rawad.phys.client.model;

import com.rawad.phys.math.Vector2f;
import com.rawad.phys.math.Vector3f;

/**
 * Holds indices for each set of data.
 * 
 * @author Rawad
 *
 */
public class Vertex {
	
	public static final int SIZE = Vector3f.SIZE + Vector3f.SIZE + Vector2f.SIZE;
	
	private final int position;
	private final int normal;
	private final int textureCoord;
	
	public Vertex(int position, int normal, int textureCoord) {
		super();
		
		this.position = position;
		this.normal = normal;
		this.textureCoord = textureCoord;
		
	}
	
	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}
	
	/**
	 * @return the normal
	 */
	public int getNormal() {
		return normal;
	}
	
	/**
	 * @return the textureCoord
	 */
	public int getTextureCoord() {
		return textureCoord;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof Vertex) {
			
			Vertex vertex = (Vertex) obj;
			
			return this.position == vertex.position && this.normal == vertex.normal && this.textureCoord == vertex
					.textureCoord;
			
		}
		
		return false;
		
	}
	
}

package com.rawad.phys.client.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Model {
	
	private final IntBuffer indices;
	
	private final FloatBuffer data;
	
	private final int vertexCount;
	
	public Model(IntBuffer indices, FloatBuffer data, int vertexCount) {
		super();
		
		this.indices = indices;
		
		this.data = data;
		
		this.vertexCount = vertexCount;
		
	}
	
	/**
	 * @return the indices
	 */
	public IntBuffer getIndices() {
		return indices;
	}
	
	/**
	 * @return the data
	 */
	public FloatBuffer getData() {
		return data;
	}
	
	/**
	 * @return the vertexCount
	 */
	public int getVertexCount() {
		return vertexCount;
	}
	
}

package com.rawad.phys.client.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

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
	
	public Model() {
		this(BufferUtils.createIntBuffer(0), BufferUtils.createFloatBuffer(0), 0);
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

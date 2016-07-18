package com.rawad.phys.client.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Model {
	
	private final FloatBuffer vertices;
	private final IntBuffer indices;
	private FloatBuffer textureCoords;
	
	private int vertexCount;
	
	public Model(FloatBuffer vertices, IntBuffer indices) {
		super();
		
		this.vertices = vertices;
		this.indices = indices;
		
	}
	
	public FloatBuffer getVertices() {
		return vertices;
	}
	
	public IntBuffer getIndices() {
		return indices;
	}
	
}

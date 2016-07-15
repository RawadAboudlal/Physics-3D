package com.rawad.phys.client.model;

import java.nio.FloatBuffer;

public class Model {
	
	private final FloatBuffer vertices;
	private FloatBuffer textureCoords;
	
	private int vertexCount;
	
	public Model(FloatBuffer vertices) {
		super();
		
		this.vertices = vertices;
		
	}
	
	public FloatBuffer getVertices() {
		return vertices;
	}
	
}

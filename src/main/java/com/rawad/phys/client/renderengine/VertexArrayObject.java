package com.rawad.phys.client.renderengine;

import org.lwjgl.opengl.GL30;

public class VertexArrayObject {
	
	private final int id;
	
	public VertexArrayObject() {
		super();
		
		id = GL30.glGenVertexArrays();
		
	}
	
	public void bind() {
		GL30.glBindVertexArray(id);
	}
	
	public void delete() {
		GL30.glDeleteVertexArrays(id);
	}
	
	public int getId() {
		return id;
	}
	
}

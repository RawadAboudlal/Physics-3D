package com.rawad.phys.client.renderengine.buffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL15;

public class VertexBufferObject {
	
	private final int id;
	
	public VertexBufferObject() {
		super();
		
		id = GL15.glGenBuffers();
		
	}
	
	/**
	 * 
	 * @param target Will normally be {@code GL15.GL_ARRAY_BUFFER}.
	 */
	public void bind(int target) {
		GL15.glBindBuffer(target, id);
	}
	
	/**
	 * Uploads vertex data to this vbo.
	 * 
	 * @param target Will normally be {@code GL15.GL_ARRAY_BUFFER}.
	 * @param data
	 * @param usage Will normally be {@code GL15.GL_STATIC_DRAW}.
	 */
	public void uploadData(int target, FloatBuffer data, int usage) {
		GL15.glBufferData(target, data, usage);
	}
	
	/**
	 * Uploads {@code null} data.
	 * 
	 * @param target
	 * @param size
	 * @param usage
	 */
	public void uploadData(int target, long size, int usage) {
		GL15.glBufferData(target, size, usage);
	}
	
	public void uploadSubData(int target, long offset, FloatBuffer data) {
		GL15.glBufferSubData(target, offset, data);
	}
	
	/**
	 * Upload element data to this EBO.
	 * 
	 * @param target
	 * @param data
	 * @param usage
	 */
	public void uploadData(int target, IntBuffer data, int usage) {
		GL15.glBufferData(target, data, usage);
	}
	
	public void delete() {
		GL15.glDeleteBuffers(id);
	}
	
	public int getId() {
		return id;
	}
	
}

package com.rawad.phys.client.renderengine.buffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL15;

public abstract class BufferObject {
	
	private final int id;
	
	private final int target;
	
	public BufferObject(int target) {
		super();
		
		this.id = GL15.glGenBuffers();
		
		this.target = target;
		
	}
	
	public void bind() {
		GL15.glBindBuffer(target, id);
	}
	
	/**
	 * Uploads vertex data to this vbo.
	 * 
	 * @param data
	 * @param usage Will normally be {@code GL15.GL_STATIC_DRAW}.
	 */
	public void uploadData(FloatBuffer data, int usage) {
		GL15.glBufferData(target, data, usage);
	}
	
	/**
	 * Upload element data to this EBO.
	 * 
	 * @param target
	 * @param data
	 * @param usage
	 */
	public void uploadData(IntBuffer data, int usage) {
		GL15.glBufferData(target, data, usage);
	}
	
	/**
	 * Uploads {@code null} data.
	 * 
	 * @param target
	 * @param size
	 * @param usage
	 */
	public void uploadData(long size, int usage) {
		GL15.glBufferData(target, size, usage);
	}
	
	public void uploadSubData(long offset, FloatBuffer data) {
		GL15.glBufferSubData(target, offset, data);
	}
	
	public void delete() {
		GL15.glDeleteBuffers(id);
	}
	
	public int getId() {
		return id;
	}
	
}

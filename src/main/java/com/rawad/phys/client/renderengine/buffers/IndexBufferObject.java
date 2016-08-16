package com.rawad.phys.client.renderengine.buffers;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL15;

public class IndexBufferObject {
	
	private final int id;
	
	public IndexBufferObject() {
		super();
		
		id = GL15.glGenBuffers();
		
//		int indexArraybuffer = GL15.glGenBuffers();
//		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexArraybuffer);
//		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, FloatBuffer.allocate(1), GL15.GL_STATIC_DRAW);
//		GL11.glDrawElements(GL11.GL_TRIANGLES, 10, GL11.GL_UNSIGNED_INT, 0);
		
	}
	
	public void bind(int target) {
		GL15.glBindBuffer(target, id);
	}
	
	public void uploadIndices(int target, FloatBuffer indices, int usage) {
		GL15.glBufferData(target, indices, usage);
	}
	
	public void delete() {
		GL15.glDeleteBuffers(id);
	}
	
	public int getId() {
		return id;
	}
	
}

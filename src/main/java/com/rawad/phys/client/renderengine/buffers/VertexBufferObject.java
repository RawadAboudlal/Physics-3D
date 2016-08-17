package com.rawad.phys.client.renderengine.buffers;

import org.lwjgl.opengl.GL15;

public class VertexBufferObject extends BufferObject {
	
	public VertexBufferObject() {
		super(GL15.GL_ARRAY_BUFFER);
	}
	
}

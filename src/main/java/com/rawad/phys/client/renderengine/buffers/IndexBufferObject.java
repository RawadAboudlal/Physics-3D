package com.rawad.phys.client.renderengine.buffers;

import org.lwjgl.opengl.GL15;

public class IndexBufferObject extends BufferObject {
	
	public IndexBufferObject() {
		super(GL15.GL_ELEMENT_ARRAY_BUFFER);
	}
	
}

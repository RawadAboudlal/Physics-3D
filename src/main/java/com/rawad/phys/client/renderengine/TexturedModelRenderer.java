package com.rawad.phys.client.renderengine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.rawad.phys.client.graphics.Renderer;
import com.rawad.phys.client.graphics.Texture;
import com.rawad.phys.client.graphics.VertexBufferObject;
import com.rawad.phys.client.model.Model;
import com.rawad.phys.client.renderengine.shaders.TexturedModelShader;
import com.rawad.phys.math.Matrix4f;

public class TexturedModelRenderer extends Renderer {
	
	private Texture texture;
	
	private TexturedModelShader program;
	
	private VertexBufferObject positions;
	private VertexBufferObject indices;
	
	private IntBuffer indexBuffer;
	private FloatBuffer dataBuffer;
	
	private Matrix4f modelMatrix;
	
	public TexturedModelRenderer() {
		super();
		
		program = new TexturedModelShader();
		
//		int texAttrib = program.getAttributeLocation("texCoord");
//		program.enableVertexAttribute(texAttrib);
//		program.pointVertexAttribute(texAttrib, 2, 7 * Float.BYTES, 2 * Float.BYTES);
		
		positions = new VertexBufferObject();
		indices = new VertexBufferObject();
		
		program.use();
		
		vao.bind();
		positions.bind(GL15.GL_ARRAY_BUFFER);
		int posAttrib = program.getAttributeLocation("position");
		program.enableVertexAttribute(posAttrib);
		program.pointVertexAttribute(posAttrib, 3, 3 * Float.BYTES, 0);
		
		indices.bind(GL15.GL_ELEMENT_ARRAY_BUFFER);
		
		long window = GLFW.glfwGetCurrentContext();
		IntBuffer widthBuff = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuff = BufferUtils.createIntBuffer(1);
		
		GLFW.glfwGetWindowSize(window, widthBuff, heightBuff);
		
		int textureUniform = program.getUniformLocation("tex");// Uniform variables.
		program.setUniform(textureUniform, 0);
		
		modelMatrix = new Matrix4f().multiply(Matrix4f.translate(0, 0, -3.5f));
		int modelUniform = program.getUniformLocation("model");
		program.setUniform(modelUniform, modelMatrix);
		
		Matrix4f view = new Matrix4f();
		int viewUniform = program.getUniformLocation("view");
		program.setUniform(viewUniform, view);
		
		int width = widthBuff.get();
		int height = heightBuff.get();
		
//		Matrix4f projection = Matrix4f.orthographic(-width / height, width / height, -1f, 1f, -1f, 1f);
		Matrix4f projection = Matrix4f.perspective(90, width / height, 0.1f, 100f);
		int projectionUniform = program.getUniformLocation("projection");
		program.setUniform(projectionUniform, projection);
		
		indexBuffer = BufferUtils.createIntBuffer(1);
		dataBuffer = BufferUtils.createFloatBuffer(1);
		
		indexBuffer.flip();
		dataBuffer.flip();
		
	}
	
	@Override
	public void render() {
		
		clear();
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		vao.bind();
		program.use();
		
		modelMatrix.multiply(Matrix4f.rotate(1f/5f, 1f, 1, 0));
		
		int modelUniform = program.getUniformLocation("model");
		program.setUniform(modelUniform, modelMatrix);
		
		positions.uploadData(GL15.GL_ARRAY_BUFFER, dataBuffer, GL15.GL_STATIC_DRAW);
		
		indices.uploadData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);
		
//		GL11.glDrawElements(GL11.GL_LINES, dataBuffer.capacity(), GL11.GL_UNSIGNED_SHORT, 0);
//		GL11.glDrawElements(GL11.GL_TRIANGLES, dataBuffer.capacity(), GL11.GL_UNSIGNED_SHORT, 0);
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, dataBuffer.capacity());
		
	}
	
	/** Should prepare shaders and upload data. */
	private void prepareModel(Model model) {
		
	}
	
	public void setModel(Model model) {
		
		if(model != null) {
			indexBuffer = model.getIndices();
			dataBuffer = model.getVertices();
		}
		
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		program.delete();
		positions.delete();
		
	}
	
}

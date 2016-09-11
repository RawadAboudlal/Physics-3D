package com.rawad.phys.client.renderengine;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;

import com.rawad.gamehelpers.client.renderengine.LayerRender;
import com.rawad.phys.client.model.Model;
import com.rawad.phys.client.renderengine.buffers.IndexBufferObject;
import com.rawad.phys.client.renderengine.buffers.VertexBufferObject;
import com.rawad.phys.client.renderengine.shaders.TexturedModelShader;
import com.rawad.phys.math.Matrix4f;

public class TexturedModelRender extends LayerRender {
	
	private TexturedModelShader program;
	
	private VertexArrayObject vao;
	
	private IndexBufferObject ibo;
	private VertexBufferObject vbo;
	
	private Model model;
	private Texture texture;
	
	private Matrix4f modelMatrix;
	
	public TexturedModelRender() {
		super();
		
		program = new TexturedModelShader();
		
		vao = new VertexArrayObject();
		
		ibo = new IndexBufferObject();
		vbo = new VertexBufferObject();
		
		program.use();
		
		vao.bind();
		
		ibo.bind();
		vbo.bind();
		
		program.initVertexAttributes();
		
		long window = GLFW.glfwGetCurrentContext();
		IntBuffer widthBuff = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuff = BufferUtils.createIntBuffer(1);
		
		GLFW.glfwGetWindowSize(window, widthBuff, heightBuff);
		
		modelMatrix = new Matrix4f().multiply(Matrix4f.translate(0, 0, -3.5f));//.multiply(Matrix4f.rotate(0, 0, 1f, 0));
		program.setUniform("model", modelMatrix);// Not really necessary.
		
		Matrix4f viewMatrix = new Matrix4f();
		program.setUniform("view", viewMatrix);
		
		int width = widthBuff.get();
		int height = heightBuff.get();
		
//		Matrix4f projection = Matrix4f.orthographic(-2f, 2f, -2f, 2f, 0.1f, 100f);
		Matrix4f projection = Matrix4f.perspective(90, width / height, 0.1f, 100f);
		program.setUniform("projection", projection);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);// Note: GL_TEXTURE# -> # has to equal value given to setUniform
		program.setUniform("modelTexture", 0);
		
	}
	
	@Override
	public void render() {
		
		vao.bind();
		
		program.use();
		
		texture.bind();
		
		program.setUniform("model", modelMatrix);
		
		if(model != null) {
			ibo.uploadData(model.getIndices(), GL15.GL_STATIC_DRAW);
			vbo.uploadData(model.getData(), GL15.GL_STATIC_DRAW);
			
			GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		
	}
	
	public void setModel(Model model, Texture texture) {
		
		this.model = model;
		this.texture = texture;
		
	}
	
	public void setModelMatrix(Matrix4f modelMatrix) {
		this.modelMatrix = modelMatrix;
	}
	
	public Matrix4f getModelMatrix() {
		return modelMatrix;
	}
	
	public void dispose() {// TODO: Use this somewhere meaningful.
		
		program.delete();
		
		vbo.delete();
		ibo.delete();
		
	}
	
}

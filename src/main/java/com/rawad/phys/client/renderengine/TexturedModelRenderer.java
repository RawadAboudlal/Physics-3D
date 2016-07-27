package com.rawad.phys.client.renderengine;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;

import com.rawad.phys.client.graphics.Renderer;
import com.rawad.phys.client.graphics.Texture;
import com.rawad.phys.client.graphics.VertexBufferObject;
import com.rawad.phys.client.model.Model;
import com.rawad.phys.client.renderengine.shaders.TexturedModelShader;
import com.rawad.phys.math.Matrix4f;

public class TexturedModelRenderer extends Renderer {
	
	private TexturedModelShader program;
	
	private VertexBufferObject ibo;
	private VertexBufferObject vbo;
	
	private Model model;
	private Texture texture;
	
	private Matrix4f modelMatrix;
	
	public TexturedModelRenderer() {
		super();
		
		program = new TexturedModelShader();
		
		ibo = new VertexBufferObject();
		vbo = new VertexBufferObject();
		
		program.use();
		
		vao.bind();
		
		ibo.bind(GL15.GL_ELEMENT_ARRAY_BUFFER);
		vbo.bind(GL15.GL_ARRAY_BUFFER);
		
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
		
		clear();
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		vao.bind();
		ibo.bind(GL15.GL_ELEMENT_ARRAY_BUFFER);
		vbo.bind(GL15.GL_ARRAY_BUFFER);
		
		program.use();
		
		texture.bind();
		
		program.setUniform("model", modelMatrix);
		
		if(model != null) {
			ibo.uploadData(GL15.GL_ELEMENT_ARRAY_BUFFER, model.getIndices(), GL15.GL_STATIC_DRAW);
			vbo.uploadData(GL15.GL_ARRAY_BUFFER, model.getData(), GL15.GL_STATIC_DRAW);
			
			GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		
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
	
	@Override
	public void dispose() {
		super.dispose();
		
		program.delete();
		
		vbo.delete();
		ibo.delete();
		
	}
	
}

package com.rawad.phys.client.renderengine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import com.rawad.phys.client.graphics.Renderer;
import com.rawad.phys.client.graphics.Shader;
import com.rawad.phys.client.graphics.Texture;
import com.rawad.phys.client.graphics.VertexBufferObject;
import com.rawad.phys.client.model.Model;
import com.rawad.phys.math.Matrix4f;

public class TexturedModelRenderer extends Renderer {
	
	private Model model;
	private Texture texture;
	
	private VertexBufferObject vertices;
	
	private FloatBuffer vertexBuffer;
	
	public TexturedModelRenderer() {
		super();
		
		program.attachShader(vert);
		program.attachShader(frag);
		
		vertices = new VertexBufferObject();
		vao.bind();
		
		program.bindFragmentDataLocation(0, "fragColor");
		
		int posAttrib = program.getAttributeLocation("position");// Vertex attributes.
		program.enableVertexAttribute(posAttrib);
		program.pointVertexAttribute(posAttrib, 3, 7 * Float.BYTES, 0);
		
//		int texAttrib = program.getAttributeLocation("texCoord");
//		program.enableVertexAttribute(texAttrib);
//		program.pointVertexAttribute(texAttrib, 2, 7 * Float.BYTES, 2 * Float.BYTES);

		long window = GLFW.glfwGetCurrentContext();
		IntBuffer widthBuff = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuff = BufferUtils.createIntBuffer(1);
		
		GLFW.glfwGetWindowSize(window, widthBuff, heightBuff);
		
		int textureUniform = program.getUniformLocation("tex");// Uniform variables.
		program.setUniform(textureUniform, 0);
		
		Matrix4f model = new Matrix4f();
		int modelUniform = program.getUniformLocation("model");
		program.setUniform(modelUniform, model);
		
		Matrix4f view = new Matrix4f();
		int viewUniform = program.getUniformLocation("view");
		program.setUniform(viewUniform, view);
		
		int width = widthBuff.get();
		int height = heightBuff.get();
		
		Matrix4f projection = Matrix4f.perspective(90, width / height, 0.1f, 100.0f);
		int projectionUniform = program.getUniformLocation("projection");
		program.setUniform(projectionUniform, projection);
		
		vertexBuffer = FloatBuffer.allocate(3 * 3);
		vertexBuffer.put(0.0f).put(0.0f).put(0);
		vertexBuffer.put(1.0f).put(1.0f).put(0);
		vertexBuffer.put(-1.0f).put(0.0f).put(0);
		
	}
	
	@Override
	public void render() {
		
		clear();
		
		if(model != null) {
			
			program.link();
			program.use();
			
			vao.bind();
			vertices.bind(GL15.GL_ARRAY_BUFFER);
			
			vertices.uploadSubData(GL15.GL_ARRAY_BUFFER, 0, vertexBuffer);
			
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexBuffer.capacity());
			
		}
		
	}
	
	private void prepareModel(Model model) {
		// Should prepare shaders and upload data.
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		vertices.delete();
		
	}
	
	@Override
	protected String getShaderName() {
		return "texturedModel";
	}
	
}

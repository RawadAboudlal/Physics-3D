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
import com.rawad.phys.math.Matrix4f;

public class TexturedModelRenderer extends Renderer {
	
	private Model model;
	private Texture texture;
	
	private VertexBufferObject vertices;
	
	private float[] data = {
			-1.0f, -1.0f, 0.0f,
			1.0f, -1.0f, 0.0f,
			0.0f,  1.0f, 0.0f,
	};
	private FloatBuffer dataBuffer;
	
	public TexturedModelRenderer() {
		super();
		
		program.attachShader(vert);
		program.attachShader(frag);
		
		program.bindFragmentDataLocation(0, "fragColor");
		program.link();
		program.use();
		
//		int texAttrib = program.getAttributeLocation("texCoord");
//		program.enableVertexAttribute(texAttrib);
//		program.pointVertexAttribute(texAttrib, 2, 7 * Float.BYTES, 2 * Float.BYTES);

		long window = GLFW.glfwGetCurrentContext();
		IntBuffer widthBuff = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuff = BufferUtils.createIntBuffer(1);
		
		GLFW.glfwGetWindowSize(window, widthBuff, heightBuff);
		
		int textureUniform = program.getUniformLocation("tex");// Uniform variables.
		program.setUniform(textureUniform, 0);
		
		Matrix4f model = new Matrix4f().multiply(Matrix4f.scale(0.5f, 0.5f, 0.5f)).multiply(Matrix4f.rotate(10, 0, 0, 1))
				.multiply(Matrix4f.translate(0, 0, -2.5f));
		int modelUniform = program.getUniformLocation("model");
		program.setUniform(modelUniform, model);
		
		Matrix4f view = new Matrix4f();
		int viewUniform = program.getUniformLocation("view");
		program.setUniform(viewUniform, view);
		
		int width = widthBuff.get();
		int height = heightBuff.get();
		
//		Matrix4f projection = Matrix4f.orthographic(-width / height, width / height, -1f, 1f, -1f, 1f);
		Matrix4f projection = Matrix4f.perspective(90, width / height, 0.1f, 100f);
		int projectionUniform = program.getUniformLocation("projection");
		program.setUniform(projectionUniform, projection);
		
		vao.bind();
		
		vertices = new VertexBufferObject();
		
		dataBuffer = BufferUtils.createFloatBuffer(data.length);
		dataBuffer.put(data);
		dataBuffer.flip();
		
	}
	
	@Override
	public void render() {
		
		clear();
		
		if(model != null) {
			
			vao.bind();
			program.use();
			
			int posAttrib = program.getAttributeLocation("position");// Vertex attributes.
			program.enableVertexAttribute(posAttrib);
			program.pointVertexAttribute(posAttrib, 3, 3 * Float.BYTES, 0);
			
			vertices.bind(GL15.GL_ARRAY_BUFFER);
			vertices.uploadData(GL15.GL_ARRAY_BUFFER, dataBuffer, GL15.GL_STATIC_DRAW);
			
			// Cube might not be showing up because -z is forward (into screen)?
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, dataBuffer.capacity());
			
		}
		
	}
	
	/** Should prepare shaders and upload data. */
	private void prepareModel(Model model) {
		
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

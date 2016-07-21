package com.rawad.phys.client.renderengine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.rawad.phys.client.graphics.Renderer;
import com.rawad.phys.client.graphics.VertexBufferObject;
import com.rawad.phys.client.model.Model;
import com.rawad.phys.client.renderengine.shaders.TexturedModelShader;
import com.rawad.phys.math.Matrix4f;
import com.rawad.phys.math.Vector2f;
import com.rawad.phys.math.Vector3f;

public class TexturedModelRenderer extends Renderer {
	
	private TexturedModelShader program;
	
	private VertexBufferObject positions;
	private VertexBufferObject textureCoords;
	private VertexBufferObject indices;
	
	private IntBuffer indexBuffer;
	private FloatBuffer positionBuffer;
	
	private Matrix4f modelMatrix;
	
	public TexturedModelRenderer() {
		super();
		
		program = new TexturedModelShader();
		
//		int texAttrib = program.getAttributeLocation("texCoord");
//		program.enableVertexAttribute(texAttrib);
//		program.pointVertexAttribute(texAttrib, 2, 7 * Float.BYTES, 2 * Float.BYTES);
		
		indices = new VertexBufferObject();
		positions = new VertexBufferObject();
		textureCoords = new VertexBufferObject();
		
		program.use();
		
		vao.bind();
		
		indices.bind(GL15.GL_ELEMENT_ARRAY_BUFFER);
		positions.bind(GL15.GL_ARRAY_BUFFER);
		
		int location_position = program.getAttributeLocation("position");
		program.enableVertexAttribute(location_position);
		program.pointVertexAttribute(location_position, Vector3f.SIZE, 0, 0);
		
		textureCoords.bind(GL15.GL_ARRAY_BUFFER);
		
		int location_textureCoords = program.getAttributeLocation("textureCoords");
		program.enableVertexAttribute(location_textureCoords);
		program.pointVertexAttribute(location_textureCoords, Vector2f.SIZE, 0, 0);
		
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
		
//		Matrix4f projection = Matrix4f.orthographic(-2f, 2f, -2f, 2f, 0.1f, 100f);
		Matrix4f projection = Matrix4f.perspective(90, width / height, 0.1f, 100f);
		int projectionUniform = program.getUniformLocation("projection");
		program.setUniform(projectionUniform, projection);
		
		indexBuffer = BufferUtils.createIntBuffer(1);
		positionBuffer = BufferUtils.createFloatBuffer(1);
		
		indexBuffer.flip();
		positionBuffer.flip();
		
	}
	
	@Override
	public void render() {
		
		clear();
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		vao.bind();
		positions.bind(GL15.GL_ARRAY_BUFFER);
		indices.bind(GL15.GL_ELEMENT_ARRAY_BUFFER);
		program.use();
		
		modelMatrix.multiply(Matrix4f.rotate(1f/5f, 1f, 1f, 0f));
		
		int modelUniform = program.getUniformLocation("model");
		program.setUniform(modelUniform, modelMatrix);
		
		indices.uploadData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);
		
		positions.uploadData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);
		textureCoords.uploadData(GL15.GL_ARRAY_BUFFER, model, usage);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, indexBuffer.capacity(), GL11.GL_UNSIGNED_INT, 0);
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		
	}
	
	public void setModel(Model model) {
		
		if(model != null) {
			indexBuffer = model.getIndices();
			
			positionBuffer = model.getVertices();
			
		}
		
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		program.delete();
		positions.delete();
		indices.delete();
		
	}
	
}

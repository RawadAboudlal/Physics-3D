package com.rawad.phys.client.renderengine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import com.rawad.phys.client.graphics.Renderer;
import com.rawad.phys.client.graphics.Texture;
import com.rawad.phys.client.graphics.VertexBufferObject;
import com.rawad.phys.client.model.Model;
import com.rawad.phys.client.model.Vertex;
import com.rawad.phys.client.renderengine.shaders.TexturedModelShader;
import com.rawad.phys.math.Matrix4f;
import com.rawad.phys.math.Vector2f;
import com.rawad.phys.math.Vector3f;

public class TexturedModelRenderer extends Renderer {
	
	private TexturedModelShader program;
	
	private VertexBufferObject ibo;
	private VertexBufferObject vbo;
	
	private IntBuffer indexBuffer;
	private FloatBuffer dataBuffer;
	
	private Texture texture;
	
	private Matrix4f modelMatrix;
	
	private int vertexCount;
	
	private float angle;
	
	public TexturedModelRenderer() {
		super();
		
		program = new TexturedModelShader();
		
		ibo = new VertexBufferObject();
		vbo = new VertexBufferObject();
		
		program.use();
		
		vao.bind();
		
		ibo.bind(GL15.GL_ELEMENT_ARRAY_BUFFER);
		vbo.bind(GL15.GL_ARRAY_BUFFER);
		
		int stride = Vertex.SIZE * Float.BYTES;
		
		int location_position = program.getAttributeLocation("position");
		program.enableVertexAttribute(location_position);
		GL20.glVertexAttribPointer(location_position, Vector3f.SIZE, GL11.GL_FLOAT, false, stride, 0);
		
		int location_normal = program.getAttributeLocation("normal");
		program.enableVertexAttribute(location_normal);
		program.pointVertexAttribute(location_normal, Vector3f.SIZE, stride, Vector3f.SIZE * Float.BYTES);
		
		int location_textureCoords = program.getAttributeLocation("vertexTextureCoords");
		program.enableVertexAttribute(location_textureCoords);
		program.pointVertexAttribute(location_textureCoords, Vector2f.SIZE, stride, (Vector3f.SIZE + Vector3f.SIZE) * 
				Float.BYTES);
		
		long window = GLFW.glfwGetCurrentContext();
		IntBuffer widthBuff = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuff = BufferUtils.createIntBuffer(1);
		
		GLFW.glfwGetWindowSize(window, widthBuff, heightBuff);
		
		modelMatrix = new Matrix4f().multiply(Matrix4f.translate(0, 0, -3.5f));//.multiply(Matrix4f.rotate(0, 0, 1f, 0));
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
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		int texture = program.getUniformLocation("modelTexture");
		program.setUniform(texture, 0);
		
		indexBuffer = BufferUtils.createIntBuffer(1);
		dataBuffer = BufferUtils.createFloatBuffer(1);
		
		indexBuffer.flip();
		dataBuffer.flip();
		
		vertexCount = 0;
		
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
		
		angle += 1f/5f % 360;
		
		program.setUniform(program.getUniformLocation("model"), modelMatrix.multiply(Matrix4f.rotate(angle, 0f, 1f, 1f)));
		
		ibo.uploadData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);
		vbo.uploadData(GL15.GL_ARRAY_BUFFER, dataBuffer, GL15.GL_STATIC_DRAW);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCount, GL11.GL_UNSIGNED_INT, 0);
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		
	}
	
	public void setModel(Model model, Texture texture) {
		
		if(model != null) {
			indexBuffer = model.getIndices();
			dataBuffer = model.getData();
			vertexCount = model.getVertexCount();
		}
		
		this.texture = texture;
		
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		program.delete();
		
		vbo.delete();
		ibo.delete();
		
	}
	
}

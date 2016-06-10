package com.rawad.phys.client.graphics;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL15;

import com.rawad.phys.client.text.Font;

public class Renderer {
	
	private VertexArrayObject vao;
	private VertexBufferObject vbo;
	
	private ShaderProgram shaderProgram;
	
	private Shader vertexShader;
	private Shader fragmentShader;
	
	private Font font;
	private Font debugFont;
	
	private boolean defaultContext;
	
	/**
	 * For testing.
	 */
	private FloatBuffer vertices;
	private int vertexCount;
	private boolean drawing;
	
	public void init(boolean defaultContext) {
		this.defaultContext = defaultContext;
		
		vao = new VertexArrayObject();
		vao.bind();
		
		vbo = new VertexBufferObject();
		vbo.bind(GL_ARRAY_BUFFER);
		
		vertices = BufferUtils.createFloatBuffer((int) Math.pow(2, 12));
		
		long size = vertices.capacity() * Float.BYTES;
		vbo.uploadData(GL_ARRAY_BUFFER, size, GL15.GL_DYNAMIC_DRAW);
		
		vertexCount = 0;
		drawing = false;
		
		vertexShader = Shader.loadShader(GL_VERTEX_SHADER, getClass(), "shader");
		fragmentShader = Shader.loadShader(GL_FRAGMENT_SHADER, getClass(), "shader");
		
		shaderProgram = new ShaderProgram();
		shaderProgram.attachShader(vertexShader);
		shaderProgram.attachShader(fragmentShader);
		shaderProgram.bindFragmentDataLocation(0, "fragColor");
		shaderProgram.link();
		shaderProgram.use();
		
		long window = GLFW.glfwGetCurrentContext();
		IntBuffer widthBuff = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuff = BufferUtils.createIntBuffer(1);
		
		GLFW.glfwGetWindowSize(window, widthBuff, heightBuff);
		
		int width = widthBuff.get();
		int height = heightBuff.get();
		
		specifyVertexAttributes();
		
	}
	
	private void specifyVertexAttributes() {
		
//		int posAttrib
		
	}
	
}

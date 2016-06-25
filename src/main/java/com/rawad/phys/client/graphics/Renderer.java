package com.rawad.phys.client.graphics;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.rawad.phys.client.text.Font;
import com.rawad.phys.math.Matrix4f;

public class Renderer {
	
	private VertexArrayObject vao;
	private VertexBufferObject vbo;
	
	private ShaderProgram shaderProgram;
	
	private Shader vertexShader;
	private Shader fragmentShader;
	
	private Font font;
	private Font debugFont;
	
	/**
	 * For testing.
	 */
	private FloatBuffer vertices;
	private int vertexCount;
	private boolean drawing;
	
	public void init() {
		
		vao = new VertexArrayObject();
		vao.bind();
		
		vbo = new VertexBufferObject();
		vbo.bind(GL_ARRAY_BUFFER);
		
		vertices = BufferUtils.createFloatBuffer((int) Math.pow(2, 12));
		
		long size = vertices.capacity() * Float.BYTES;
		vbo.uploadData(GL_ARRAY_BUFFER, size, GL15.GL_DYNAMIC_DRAW);
		
		vertexCount = 0;
		drawing = false;
		
		vertexShader = Shader.loadShader(GL_VERTEX_SHADER, getClass(), "shader.vert");
		fragmentShader = Shader.loadShader(GL_FRAGMENT_SHADER, getClass(), "shader.frag");
		
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
		
		specifyVertexAttributes();
		
		int textureUniform = shaderProgram.getUniformLocation("tex");
		shaderProgram.setUniform(textureUniform, 0);
		
		Matrix4f model = new Matrix4f();
		int modelUniform = shaderProgram.getUniformLocation("model");
		shaderProgram.setUniform(modelUniform, model);
		
		Matrix4f view = new Matrix4f();
		int viewUniform = shaderProgram.getUniformLocation("view");
		shaderProgram.setUniform(viewUniform, view);
		
		int width = widthBuff.get();
		int height = heightBuff.get();
		
		Matrix4f projection = Matrix4f.orthographic(0, width, 0, height, 0f, 1f);
		int projectionUniform = shaderProgram.getUniformLocation("projection");
		shaderProgram.setUniform(projectionUniform, projection);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		font = new Font();
		debugFont = new Font(false);
		
	}
	
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void begin() {
		if(drawing) throw new IllegalStateException("Renderer already drawing.");
		
		drawing = true;
		vertexCount = 0;
		
	}
	
	public void end() {
		if(!drawing) throw new IllegalStateException("Renderer not drawing.");
		
		drawing = false;
		flush();
		
	}
	
	/**
	 * Flushes data to GPU for rendering.
	 */
	public void flush() {
		
		if(vertexCount <= 0) return;
		
		vertices.flip();
		
		vao.bind();
		shaderProgram.use();
		
		vbo.bind(GL_ARRAY_BUFFER);
		vbo.uploadSubData(GL_ARRAY_BUFFER, 0, vertices);
		
		glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
		
		vertices.clear();
		vertexCount = 0;
		
	}
	
	private void specifyVertexAttributes() {
		
		int posAttrib = shaderProgram.getAttributeLocation("position");
		shaderProgram.enableVertexAttribute(posAttrib);
		shaderProgram.pointVertexAttribute(posAttrib, 2, 7 * Float.BYTES, 0);
		
		int colAttrib = shaderProgram.getAttributeLocation("color");
		shaderProgram.enableVertexAttribute(colAttrib);
		shaderProgram.pointVertexAttribute(colAttrib, 3, 7 * Float.BYTES, 2 * Float.BYTES);
		
		int texAttrib = shaderProgram.getAttributeLocation("texCoord");
		shaderProgram.enableVertexAttribute(texAttrib);
		shaderProgram.pointVertexAttribute(texAttrib, 2, 7 * Float.BYTES, 5 * Float.BYTES);
		
	}
	
	public void drawText(CharSequence text, float x, float y) {
		font.drawText(this, text, x, y);
	}
	
	public void drawTexture(Texture texture, float x, float y, Color c) {
		drawTextureRegion(texture, x, y, 0, 0, texture.getWidth(), texture.getHeight(), c);
	}
	
	public void drawTextureRegion(Texture texture, float x, float y, float regX, float regY, float regWidth, 
			float regHeight, Color c) {
		
		// Vertex positions.
		float x1 = x;
		float y1 = y;
		float x2 = x + regWidth;
		float y2 = y + regHeight;
		
		// Texture coordinates.
		float s1 = regX / texture.getWidth();
		float t1 = regY / texture.getHeight();
		float s2 = (regX + regWidth) / texture.getWidth();
		float t2 = (regY + regHeight) / texture.getHeight();
		
		drawTextureRegion(x1, y1, x2, y2, s1, t1, s2, t2, c);
		
	}
	
	public void drawTextureRegion(float x1, float y1, float x2, float y2, float s1, float t1, float s2, float t2, 
			Color c) {
		
		if(vertices.remaining() < 7 * 6) flush();// Need more vbo space.
		
		float r = c.getRed();
		float g = c.getGreen();
		float b = c.getBlue();
		
		vertices.put(x1).put(y1).put(r).put(g).put(b).put(s1).put(t1);
		vertices.put(x1).put(y2).put(r).put(g).put(b).put(s1).put(t2);
		vertices.put(x2).put(y2).put(r).put(g).put(b).put(s2).put(t2);
		
		vertices.put(x1).put(y1).put(r).put(g).put(b).put(s1).put(t1);
		vertices.put(x2).put(y2).put(r).put(g).put(b).put(s2).put(t2);
		vertices.put(x2).put(y1).put(r).put(g).put(b).put(s2).put(t1);
		
		vertexCount += 6;// Two triangles.
		
	}
	
	public void dispose() {
		vao.delete();
		vbo.delete();
		
		vertexShader.delete();
		fragmentShader.delete();
		
		shaderProgram.delete();
		
		font.dispose();
		debugFont.dispose();
		
	}
	
}

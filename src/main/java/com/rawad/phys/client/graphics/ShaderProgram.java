package com.rawad.phys.client.graphics;

import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glShaderSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.rawad.phys.math.Matrix2f;
import com.rawad.phys.math.Matrix3f;
import com.rawad.phys.math.Matrix4f;
import com.rawad.phys.math.Vector2f;
import com.rawad.phys.math.Vector3f;
import com.rawad.phys.math.Vector4f;
import com.rawad.phys.util.Util;

public abstract class ShaderProgram {
	
	private static final String EXTENSION_VERTEX_SHADER = ".vert";
	private static final String EXTENSION_FRAGMENT_SHADER = ".frag";
	
	protected final int id;
	
	protected final int vertexShader;
	protected final int fragmentShader;
	
	public ShaderProgram() {
		super();
		
		id = GL20.glCreateProgram();
		
		vertexShader = this.loadShader(GL20.GL_VERTEX_SHADER);
		fragmentShader = this.loadShader(GL20.GL_FRAGMENT_SHADER);
		
		attachShader(vertexShader);
		attachShader(fragmentShader);
		
		link();
		
	}
	
	/**
	 * {@code VertexArrayObject} and any other {@code Buffer} objects should be bound before calling this.
	 */
	public abstract void initVertexAttributes();
	
	public void attachShader(int shader) {
		GL20.glAttachShader(id, shader);
	}
	
	public void bindAttribLocation(int number, CharSequence name) {
		GL20.glBindAttribLocation(id, number, name);
	}
	
	public void link() {
		GL20.glLinkProgram(id);
		
		checkStatus();
	}
	
	public int getAttributeLocation(CharSequence name) {
		return GL20.glGetAttribLocation(id, name);
	}
	
	/**
	 * Enables a vertex attribute array.
	 * 
	 * @param location
	 */
	public void enableVertexAttribute(int location) {
		GL20.glEnableVertexAttribArray(location);
	}
	
	public void disableVertexAttribute(int location) {
		GL20.glDisableVertexAttribArray(location);
	}
	
	public void pointVertexAttribute(int location, int size, int stride, int offset) {
		GL20.glVertexAttribPointer(location, size, GL11.GL_FLOAT, false, stride, offset);
	}
	
	protected int getUniformLocation(CharSequence name) {
		return GL20.glGetUniformLocation(id, name);
	}
	
	public void setUniform(CharSequence name, int value) {
		int location = getUniformLocation(name);
		GL20.glUniform1i(location, value);
	}
	
	public void setUniform(CharSequence name, Vector2f value) {
		int location = getUniformLocation(name);
		GL20.glUniform2fv(location, value.getBuffer());
	}
	
	public void setUniform(CharSequence name, Vector3f value) {
		int location = getUniformLocation(name);
		GL20.glUniform3fv(location, value.getBuffer());
	}
	
	public void setUniform(CharSequence name, Vector4f value) {
		int location = getUniformLocation(name);
		GL20.glUniform4fv(location, value.getBuffer());
	}
	
	public void setUniform(CharSequence name, Matrix2f value) {
		int location = getUniformLocation(name);
		GL20.glUniformMatrix2fv(location, false, value.getBuffer());
	}
	
	public void setUniform(CharSequence name, Matrix3f value) {
		int location = getUniformLocation(name);
		GL20.glUniformMatrix3fv(location, false, value.getBuffer());
	}
	
	public void setUniform(CharSequence name, Matrix4f value) {
		int location = getUniformLocation(name);
		GL20.glUniformMatrix4fv(location, false, value.getBuffer());
	}
	
	public void use() {
		GL20.glUseProgram(id);
	}
	
	public void delete() {
		GL20.glDeleteProgram(id);
	}
	
	private void checkStatus() {
		int status = GL20.glGetProgrami(id, GL20.GL_LINK_STATUS);
		if(status != GL11.GL_TRUE) throw new RuntimeException(GL20.glGetProgramInfoLog(id));
	}
	
	public final int getId() {
		return id;
	}
	
	protected final int loadShader(int type) {
		
		StringBuilder builder = new StringBuilder();
		
		String name = getShaderName() + getExtensionFromType(type);
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(name)))) {
			
			String line = null;
			
			while((line = reader.readLine()) != null) {
				builder.append(line).append(Util.NL);
			}
			
		} catch(Exception ex) {
			throw new RuntimeException("Failed to load shader file." + System.lineSeparator() + ex.getMessage());
		}
		
		CharSequence source = builder.toString();
		
		int id = glCreateShader(type);
		glShaderSource(id, source);
		glCompileShader(id);
		
		return id;
		
	}
	
	private static final String getExtensionFromType(int type) {
		
		switch(type) {
		
		case GL20.GL_VERTEX_SHADER:
			return EXTENSION_VERTEX_SHADER;
		
		case GL20.GL_FRAGMENT_SHADER:
			return EXTENSION_FRAGMENT_SHADER;
		
		default:
			return EXTENSION_VERTEX_SHADER;
			
		}
		
	}
	
	/**
	 * Base name of this {@code ShaderProgram}.
	 * @return
	 */
	protected abstract String getShaderName();
	
}

package com.rawad.phys.client.graphics;

import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.rawad.phys.math.Matrix2f;
import com.rawad.phys.math.Matrix3f;
import com.rawad.phys.math.Matrix4f;
import com.rawad.phys.math.Vector2f;
import com.rawad.phys.math.Vector3f;
import com.rawad.phys.math.Vector4f;

public class ShaderProgram {
	
	private final int id;
	
	public ShaderProgram() {
		id = glCreateProgram();
	}
	
	public void attachShader(Shader shader) {
		glAttachShader(id, shader.getId());
	}
	
	/**
	 * Binds fragment shader's output color location.
	 * 
	 * @param number Color number to be bound.
	 * @param name Name of variable to bind {@code number} to.
	 */
	public void bindFragmentDataLocation(int number, CharSequence name) {
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
	
	public void disableVertexAttrivute(int location) {
		GL20.glDisableVertexAttribArray(location);
	}
	
	public void pointVertexAttribute(int location, int size, int stride, int offset) {
		GL20.glVertexAttribPointer(location, size, GL11.GL_FLOAT, false, stride, offset);
	}
	
	public int getUniformLocation(CharSequence name) {
		return GL20.glGetUniformLocation(id, name);
	}
	
	public void setUniform(int location, int value) {
		GL20.glUniform1i(id, value);
	}
	
	public void setUniform(int location, Vector2f value) {
		GL20.glUniform2fv(location, value.getBuffer());
	}
	
	public void setUniform(int location, Vector3f value) {
		GL20.glUniform3fv(location, value.getBuffer());
	}
	
	public void setUniform(int location, Vector4f value) {
		GL20.glUniform4fv(location, value.getBuffer());
	}
	
	public void setUniform(int location, Matrix2f value) {
		GL20.glUniformMatrix2fv(location, false, value.getBuffer());
	}
	
	public void setUniform(int location, Matrix3f value) {
		GL20.glUniformMatrix3fv(location, false, value.getBuffer());
	}
	
	public void setUniform(int location, Matrix4f value) {
		GL20.glUniformMatrix4fv(location, false, value.getBuffer());
	}
	
	public void use() {
		GL20.glUseProgram(id);
	}
	
	public void delete() {
		GL20.glDeleteProgram(id);
	}
	
	private void checkStatus() {
		int status = GL20.glGetShaderi(id, GL20.GL_LINK_STATUS);
		if(status != GL11.GL_TRUE) throw new RuntimeException(GL20.glGetProgramInfoLog(id));
	}
	
	public int getId() {
		return id;
	}
	
}

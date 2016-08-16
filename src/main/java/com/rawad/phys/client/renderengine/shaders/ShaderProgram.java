package com.rawad.phys.client.renderengine.shaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.rawad.gamehelpers.utils.Util;
import com.rawad.phys.loader.Loader;
import com.rawad.phys.math.Matrix2f;
import com.rawad.phys.math.Matrix3f;
import com.rawad.phys.math.Matrix4f;
import com.rawad.phys.math.Vector2f;
import com.rawad.phys.math.Vector3f;
import com.rawad.phys.math.Vector4f;

public abstract class ShaderProgram {
	
	protected final int id;
	
	protected final int vertexShader;
	protected final int fragmentShader;
	
	public ShaderProgram() {
		super();
		
		id = GL20.glCreateProgram();
		
		vertexShader = this.loadShader(GL20.GL_VERTEX_SHADER);
		fragmentShader = this.loadShader(GL20.GL_FRAGMENT_SHADER);
		
		this.attachShader(vertexShader);
		this.attachShader(fragmentShader);
		
		this.link();
		
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
		if(status != GL11.GL_TRUE)
			throw new RuntimeException(Util.getStringFromLines(Util.NL, false, GL20.glGetShaderInfoLog(vertexShader), 
					GL20.glGetShaderInfoLog(fragmentShader)));
	}
	
	public final int getId() {
		return id;
	}
	
	protected final int loadShader(int type) {
		
		CharSequence source = Loader.loadShaderSource(getClass(), this.getShaderName(), type);
		
		int id = GL20.glCreateShader(type);
		GL20.glShaderSource(id, source);
		GL20.glCompileShader(id);
		
		return id;
		
	}
	
	/**
	 * Base name of this {@code ShaderProgram}.
	 * @return
	 */
	protected abstract String getShaderName();
	
}

package com.rawad.phys.client.graphics;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glShaderSource;

public class Shader {
	
	private final int id;
	
	public Shader(int type, CharSequence source) {
		super();
		
		id = glCreateShader(type);
		glShaderSource(id, source);
		glCompileShader(id);
		
		checkStatus();
		
	}
	
	private void checkStatus() {
		int status = glGetShaderi(id, GL_COMPILE_STATUS);
		if(status != GL_TRUE) throw new RuntimeException(glGetShaderInfoLog(id));
	}
	
	public void delete() {
		glDeleteShader(id);
	}
	
	public int getId() {
		return id;
	}
	
}

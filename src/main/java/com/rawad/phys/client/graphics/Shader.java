package com.rawad.phys.client.graphics;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glShaderSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Shader {
	
	private final int id;
	
	public Shader(int type, CharSequence source) {
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
	
	public static Shader loadShader(int type, Class<? extends Object> resLoader, String name) {
		
		StringBuilder builder = new StringBuilder();
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(resLoader.getResourceAsStream(name)))) {
			
			String line = null;
			
			while((line = reader.readLine()) != null) {
				builder.append(line).append("\n");// TODO: Could make "\n" a global constant.
			}
			
		} catch(Exception ex) {
			throw new RuntimeException("Failed to load shader file." + System.lineSeparator() + ex.getMessage());
		}
		
		CharSequence source = builder.toString();
		
		return new Shader(type, source);
		
	}
	
}

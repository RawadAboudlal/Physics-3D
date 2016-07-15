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

import org.lwjgl.opengl.GL20;

import com.rawad.phys.util.Util;

public class Shader {
	
	private static final String EXTENSION_VERTEX_SHADER =".vert";
	private static final String EXTENSION_FRAGMENT_SHADER = ".frag";
	
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
	
	public static Shader loadShader(int type, Class<? extends Object> resLoader, String name) {
		
		StringBuilder builder = new StringBuilder();
		
		name = name + getExtensionFromType(type);
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(resLoader.getResourceAsStream(name)))) {
			
			String line = null;
			
			while((line = reader.readLine()) != null) {
				builder.append(line).append(Util.NL);
			}
			
		} catch(Exception ex) {
			throw new RuntimeException("Failed to load shader file." + System.lineSeparator() + ex.getMessage());
		}
		
		CharSequence source = builder.toString();
		
		return new Shader(type, source);
		
	}
	
	private static String getExtensionFromType(int type) {
		
		switch(type) {
		
		case GL20.GL_VERTEX_SHADER:
			return EXTENSION_VERTEX_SHADER;
		
		case GL20.GL_FRAGMENT_SHADER:
			return EXTENSION_FRAGMENT_SHADER;
		
		default:
			return EXTENSION_VERTEX_SHADER;
			
		}
		
	}
	
}

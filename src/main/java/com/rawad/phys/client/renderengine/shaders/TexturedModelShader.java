package com.rawad.phys.client.renderengine.shaders;

import org.lwjgl.opengl.GL20;

import com.rawad.phys.client.graphics.ShaderProgram;

public class TexturedModelShader extends ShaderProgram {
	
	private final int vertexShader;
	private final int fragmentShader;
	
	public TexturedModelShader() {
		super();
		
		vertexShader = loadShader(GL20.GL_VERTEX_SHADER);
		fragmentShader = loadShader(GL20.GL_FRAGMENT_SHADER);
		
		attachShader(vertexShader);
		attachShader(fragmentShader);
		
	}
	
	@Override
	protected String getShaderName() {
		return "texturedModel";
	}
	
}

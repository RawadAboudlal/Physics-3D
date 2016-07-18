package com.rawad.phys.client.renderengine.shaders;

import com.rawad.phys.client.graphics.ShaderProgram;

public class TexturedModelShader extends ShaderProgram {
	
	public TexturedModelShader() {
		super();
		
	}
	
	@Override
	protected String getShaderName() {
		return "texturedModel";
	}
	
}

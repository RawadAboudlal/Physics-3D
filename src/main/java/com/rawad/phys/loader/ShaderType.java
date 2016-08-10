package com.rawad.phys.loader;

import org.lwjgl.opengl.GL20;

public enum ShaderType {
	
	DEFAULT("txt"),
	VERTEX("vert"),
	FRAGMENT("frag");
	
	private final String extensionName;
	
	private ShaderType(String extensionName) {
		this.extensionName = extensionName;
	}
	
	public String getExtensionName() {
		return extensionName;
	}
	
	public static ShaderType getByInt(int type) {
		
		switch(type) {
		
		case GL20.GL_VERTEX_SHADER:
			return VERTEX;
		
		case GL20.GL_FRAGMENT_SHADER:
			return FRAGMENT;
		
		default:
			return DEFAULT;
			
		}
		
	}
	
}

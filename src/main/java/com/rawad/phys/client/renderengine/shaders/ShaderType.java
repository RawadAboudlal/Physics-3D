package com.rawad.phys.client.renderengine.shaders;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

public enum ShaderType {
	
	DEFAULT("txt", -1),
	VERTEX("vert", GL20.GL_VERTEX_SHADER),
	GEOMETRY("geom", GL32.GL_GEOMETRY_SHADER),
	FRAGMENT("frag", GL20.GL_FRAGMENT_SHADER);
	
	private final String extensionName;
	private final int type;
	
	private ShaderType(String extensionName, int type) {
		this.extensionName = extensionName;
		this.type = type;
	}
	
	public String getExtensionName() {
		return extensionName;
	}
	
	public int getType() {
		return type;
	}
	
	public static ShaderType getByInt(int type) {
		
		for(ShaderType shaderType: ShaderType.values()) {
			if(shaderType.getType() == type) return shaderType;
		}
		
		return DEFAULT;
		
	}
	
}

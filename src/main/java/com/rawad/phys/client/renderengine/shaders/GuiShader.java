package com.rawad.phys.client.renderengine.shaders;

public class GuiShader extends ShaderProgram {
	
	private static final String[] SHADER_NAMES = {"gui", "gui", "gui"};
	private static final ShaderType[] SHADER_TYPES = {ShaderType.VERTEX, ShaderType.GEOMETRY, ShaderType.FRAGMENT};
	
	public GuiShader() {
		super(SHADER_NAMES, SHADER_TYPES);
	}
	
	@Override
	public void initVertexAttributes() {
		
		// uniforms: ...
		//		Shared among all points.
		// attributes: width, height (or just size as vec2), centre position on screen (position of point, vec2)
		//		Each point (gui container thing) has these individually.
		
	}
	
}

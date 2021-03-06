package com.rawad.phys.client.renderengine.shaders;

import com.rawad.phys.client.model.Vertex;
import com.rawad.phys.math.Vector2f;
import com.rawad.phys.math.Vector3f;

public class TexturedModelShader extends ShaderProgram {

	private static final String[] SHADER_NAMES = {"texturedModel", "texturedModel"};
	private static final ShaderType[] SHADER_TYPES = {ShaderType.VERTEX, ShaderType.FRAGMENT};
	
	public TexturedModelShader() {
		super(SHADER_NAMES, SHADER_TYPES);
	}
	
	@Override
	public void initVertexAttributes() {
		
		int stride = Vertex.SIZE * Float.BYTES;
		
		int location_position = getAttributeLocation("position");
		enableVertexAttribute(location_position);
		pointVertexAttribute(location_position, Vector3f.SIZE, stride, 0);
		
		int location_normal = getAttributeLocation("normal");
		enableVertexAttribute(location_normal);
		pointVertexAttribute(location_normal, Vector3f.SIZE, stride, Vector3f.SIZE * Float.BYTES);
		
		int location_textureCoords = getAttributeLocation("vertexTextureCoords");
		enableVertexAttribute(location_textureCoords);
		pointVertexAttribute(location_textureCoords, Vector2f.SIZE, stride, (Vector3f.SIZE + Vector3f.SIZE) * 
				Float.BYTES);
		
	}
	
}

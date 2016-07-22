#version 330 core

layout (location = 0) in vec3 position;// Matches glEnableVertexAttribArray(0).
layout (location = 1) in vec3 normal;
layout (location = 2) in vec2 vertexTextureCoords;

out vec2 textureCoords;

out vec3 vertexColor;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
	
	textureCoords = vertexTextureCoords;
	
	vertexColor = position * vec3(1.0, 1.0, 1.0);
	
	mat4 mvp = projection * view * model;
	gl_Position = mvp * vec4(position, 1.0);
	
}

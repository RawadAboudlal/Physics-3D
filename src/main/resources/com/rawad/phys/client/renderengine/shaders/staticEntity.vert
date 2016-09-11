#version 400 core

const vec3 lightPosition = vec3(0.0, 0.0, 0.0);

layout (location = 0) in vec3 position;// Matches glEnableVertexAttribArray(0).
layout (location = 1) in vec3 normal;
layout (location = 2) in vec2 vertexTextureCoords;

out vec2 textureCoords;
out vec3 vertexColor;
out vec3 normalInWorldSpace;
out vec3 lightToVertex;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
	
	vec4 positionInWorldSpace = model * vec4(position, 1.0);
	normalInWorldSpace = (model * vec4(normal, 0.0)).xyz;
	
	lightToVertex = lightPosition - positionInWorldSpace.xyz;// Direction matters.
	
	vertexColor = vec3(1.0, 1.0, 1.0);// Acts as light color, for now.
	
	textureCoords = vertexTextureCoords;
	
	gl_Position = projection * view * positionInWorldSpace;
	
}

#version 330 core

const vec3 lightPosition = vec3(1.0, 0, 0);

layout (location = 0) in vec3 position;// Matches glEnableVertexAttribArray(0).
layout (location = 1) in vec3 normal;
layout (location = 2) in vec2 vertexTextureCoords;

out vec2 textureCoords;
out vec3 vertexColor;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
	
	vec4 normalInModelSpace = model * vec4(normal, 1.0);
	mat4 mv = view * model;
	
	vec4 lightToVertex = vec4(position.x - lightPosition.x, position.y - lightPosition.y, position.z - lightPosition.z,
			0.0);
	
	float dot = dot(lightToVertex, normalInModelSpace);
	vertexColor = vec3(1.0, 1.0, 1.0) * dot;
	
	textureCoords = vertexTextureCoords;
	
	mat4 mvp = projection * mv;
	gl_Position = mvp * vec4(position, 1.0);
	
}

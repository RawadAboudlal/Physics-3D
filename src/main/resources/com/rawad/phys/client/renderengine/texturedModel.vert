#version 150 core

in vec3 position;
//in vec2 texCoord;

//out vec2 textureCoord;
out vec3 vertexColor;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
	//textureCoord = texCoord;

	vertexColor = vec3(1.0, 1.0, 1.0);

	mat4 mvp = projection * view * model;
	gl_Position = mvp * vec4(position, 1.0);

}

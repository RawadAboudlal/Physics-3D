#version 150 core

in vec2 position;
in vec3 color;
in vec2 texCoord;

out vec3 vertexColor;
out vec2 textureCoord;

uniform mat4 view;
uniform mat4 projection;

void main() {
	vertexColor = color;
	textureCoord = texCoord;

	mat4 vp = view * projection;

	gl_Position = vp * vec4(position, 0.0, 1.0);
}

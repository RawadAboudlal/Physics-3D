#version 330 core

in vec3 vertexColor;
in vec2 textureCoords;

out vec4 fragColor;

uniform sampler2D modelTexture;

void main() {
//	fragColor = vec4(vertexColor, 1.0);
	fragColor = texture(modelTexture, textureCoords) * vec4(vertexColor, 1.0);
}

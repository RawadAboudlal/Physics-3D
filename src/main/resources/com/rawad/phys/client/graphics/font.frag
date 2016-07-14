#version 150 core

in vec3 vertexColor;
in vec2 textureCoord;

out vec4 fragColor;

uniform sampler2D fontTexture;

void main() {
	fragColor = texture(fontTexture, textureCoord) * vec4(vertexColor, 1.0);
}

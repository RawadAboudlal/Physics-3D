#version 400 core

in vec3 vertexColor;
in vec2 textureCoords;
in vec3 normalInWorldSpace;
in vec3 lightToVertex;

out vec4 fragColor;

uniform sampler2D modelTexture;

void main() {
	
	float nDotL = max(dot(normalize(normalInWorldSpace), normalize(lightToVertex)), 0.0);
	
	vec3 diffuse = nDotL * vertexColor / pow(length(lightToVertex), 2);
	
	fragColor = texture(modelTexture, textureCoords) * vec4(diffuse, 1.0);
	
}

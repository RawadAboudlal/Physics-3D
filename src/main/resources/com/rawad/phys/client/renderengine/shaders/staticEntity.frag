#version 400 core

in vec3 vertexColor;
in vec2 textureCoords;
in vec3 normalInWorldSpace;
in vec3 lightToVertex;

out vec4 fragColor;

uniform sampler2D modelTexture;

const float ambient = 0.1;
const vec3 vec_ambient = vec3(ambient, ambient, ambient);
 
void main() {
	
	vec3 unitNormal = normalize(normalInWorldSpace);
	vec3 unitLightToVertex = normalize(lightToVertex);
	
	float nDotL = max(dot(unitNormal, unitLightToVertex), 0);
	
	vec3 diffuse = max(nDotL * vertexColor / pow(length(lightToVertex), 2), vec_ambient);
	
	fragColor = texture(modelTexture, textureCoords) * vec4(diffuse, 1.0);
	
}

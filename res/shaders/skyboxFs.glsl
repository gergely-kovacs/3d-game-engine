#version 330

uniform samplerCube texture_Diffuse;

uniform vec3 lightColour;
uniform vec3 ambientLight;

in vec3 textureCoords;

out vec4 out_Color;

void main() {
	vec4 totalLight = vec4((ambientLight + lightColour), 1.0);

	out_Color = texture(texture_Diffuse, textureCoords) * totalLight;
}

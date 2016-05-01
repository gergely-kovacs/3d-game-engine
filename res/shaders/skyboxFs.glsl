#version 330

uniform samplerCube texture_Diffuse;

uniform vec3 lightColour;

in vec3 textureCoords;

out vec4 out_Color;

void main() {
	out_Color = texture(texture_Diffuse, textureCoords) * vec4(lightColour, 1.0);
}

#version 330

uniform sampler2D texture_Diffuse;

uniform vec3 lightColour;

in vec2 pass_TextureCoords;

out vec4 out_Color;

void main() {
	out_Color = texture(texture_Diffuse, pass_TextureCoords) * vec4(2.0 * lightColour, 1.0);
}

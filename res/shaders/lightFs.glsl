#version 330

uniform sampler2D texture_Diffuse;

in vec2 pass_TextureCoords;

out vec4 out_Color;

void main() {
	out_Color = texture(texture_Diffuse, pass_TextureCoords);
}

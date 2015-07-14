#version 400

//uniform sampler2D texture_Diffuse;

//in vec2 pass_Texture;

out vec4 out_Color;

void main() {
	//out_Color = texture(texture_Diffuse, pass_Texture);
	out_Color = vec4(1.0, 0.0, 1.0, 1.0);
}

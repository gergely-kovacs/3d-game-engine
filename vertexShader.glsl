#version 150

in vec3 position;
in vec3 color;

out vec4 pass_Color;

void main() {
	gl_Position = vec4(position, 1.0f);
	pass_Color = vec4(color, 1.0f);
}
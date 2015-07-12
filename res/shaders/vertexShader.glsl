#version 400

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

in vec3 position;
in vec2 texture;

out vec4 pass_Color;
out vec2 pass_Texture;

void main() {
	gl_Position = vec4(position, 1.0f);
	gl_Position = projection * view * model * gl_Position;
	
	pass_Texture = texture;
}

#version 330

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

in vec3 vertexPosition;
in vec2 in_TextureCoords;

out vec2 pass_TextureCoords;

void main() {
	gl_Position = projection * view * model * vec4(vertexPosition, 1.0);
	
	pass_TextureCoords = in_TextureCoords;
}

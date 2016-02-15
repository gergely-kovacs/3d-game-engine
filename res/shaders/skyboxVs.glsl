#version 400

uniform mat4 projection;
uniform mat4 view;

in vec3 vertexPosition;

out vec3 textureCoords;

void main() {
	gl_Position = projection * view * vec4(vertexPosition, 1.0);
	
	textureCoords = vertexPosition;
}

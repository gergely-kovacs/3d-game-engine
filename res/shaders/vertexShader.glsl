#version 330

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

uniform vec3 lightPosition;

in vec3 vertexPosition;
in vec3 vertexNormal;
//in vec2 texture;

out vec3 surfaceNormal;
out vec3 lightDirection;
//out vec2 pass_Texture;

void main() {
	vec4 transPos = model * vec4(vertexPosition, 1.0);
	gl_Position = projection * view * transPos;
	
	surfaceNormal = (model * vec4(vertexNormal, 0.0)).xyz;
	
	lightDirection = lightPosition - transPos.xyz;
	
	//pass_Texture = texture;
}

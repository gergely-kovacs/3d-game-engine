#version 330

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

uniform vec3 lightPosition;

in vec3 vertexPosition;
in vec3 vertexNormal;
//in vec2 texture;

out vec3 surfaceNormal;
out vec3 toLight;
out vec3 toCamera;
//out vec2 pass_Texture;

void main() {
	vec4 worldPos = model * vec4(vertexPosition, 1.0);
	gl_Position = projection * view * worldPos;
	
	surfaceNormal = (model * vec4(vertexNormal, 0.0)).xyz;
	toLight = lightPosition - worldPos.xyz;
	toCamera = (inverse(view) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPos.xyz;
	
	//pass_Texture = texture;
}

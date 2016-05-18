#version 330

uniform sampler2D texture_Diffuse;

uniform vec3 lightColour;

in vec3 surfaceNormal;
in vec3 toLight;
in vec2 pass_Texture;

out vec4 out_Color;

void main() {
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitToLight = normalize(toLight);
	
	float brightness = dot(unitNormal, unitToLight);
	brightness = max(brightness, 0.0);
	vec3 diffuse = brightness * lightColour;
	
	out_Color = texture(texture_Diffuse, pass_Texture) * vec4(diffuse, 1.0);
}

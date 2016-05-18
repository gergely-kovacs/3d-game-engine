#version 330

uniform sampler2D texture_Diffuse;

uniform vec3 lightColour;
uniform vec3 ambientLight;

in vec3 surfaceNormal;
in vec3 toLight;
in vec2 pass_Texture;

out vec4 out_Color;

void main() {
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitToLight = normalize(toLight);
	
	float brightness = dot(unitNormal, unitToLight);
	brightness = max(brightness, 0.0);
	vec3 finalDiffuse = brightness * lightColour;
	
	vec4 totalLight = vec4((ambientLight + finalDiffuse), 1.0);
	
	out_Color = texture(texture_Diffuse, pass_Texture) * totalLight;
}

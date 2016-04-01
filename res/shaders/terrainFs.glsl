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
	
	float brightness = dot(unitNormal, unitToLight); // TODO: fix ambient light (if light colour is 0, then diffuse colour is 0, regardless of brightness)
	brightness = max(brightness, 0.1);
	vec3 diffuse = brightness * lightColour;

	vec2 textureCoord = pass_Texture * 10;
	out_Color = texture(texture_Diffuse, textureCoord) * vec4(diffuse, 1.0);
}

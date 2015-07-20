#version 330

uniform vec3 lightColour;
//uniform sampler2D texture_Diffuse;

in vec3 surfaceNormal;
in vec3 lightDirection;
//in vec2 pass_Texture;

out vec4 fragColour;

void main() {
	vec3 fragNormal = normalize(surfaceNormal);
	vec3 fragToLight = normalize(lightDirection);
	
	float nDotL = dot(fragNormal, fragToLight);
	float brightness = max(nDotL, 0.0);
	
	vec3 diffuse = brightness * lightColour;
	
	//fragColour = texture(texture_Diffuse, pass_Texture);
	fragColour = vec4(diffuse, 1.0) * vec4(0.5, 0.0, 0.8, 1.0);
}

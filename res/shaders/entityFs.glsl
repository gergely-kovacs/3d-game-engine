#version 330

uniform sampler2D texture_Diffuse;

uniform vec3 lightColour;
uniform vec3 ambientLight;

uniform float shineDamper;
uniform float reflectivity;

in vec3 surfaceNormal;
in vec3 toLight;
in vec3 toCamera;
in vec2 pass_Texture;

out vec4 fragColour;

void main() {
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitToLight = normalize(toLight);
	vec3 unitToCamera = normalize(toCamera);
	
	float brightness = dot(unitNormal, unitToLight);
	brightness = max(brightness, 0.1);
	vec3 diffuse = brightness * lightColour;
	
	vec3 lightDirection = -unitToLight;
	vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
	float specularFactor = dot(reflectedLightDirection, unitToCamera);
	specularFactor = max(specularFactor, 0.0);
	float dampedSpecular = pow(specularFactor, shineDamper);
	vec3 finalSpecular = dampedSpecular * reflectivity * lightColour;
	
	fragColour = vec4(2.0 * ambientLight, 1.0) + vec4(diffuse, 1.0) * texture(texture_Diffuse, pass_Texture) + vec4(finalSpecular, 1.0);
}

#version 120
#define PI 3.14159265358979323846

varying vec2 texCoord0;
uniform float GameTime;
uniform vec2 InSize;
uniform vec2 OutSize;

out vec4 fragColor;

uniform vec4 ColorModulator;

float hash(vec2 p) { return fract(1e4 * sin(17.0 * p.x + p.y * 0.1) * (0.1 + abs(sin(p.y * 13.0 + p.x)))); }
float noise(vec2 x) {
	vec2 i = floor(x);
	vec2 f = fract(x);
	float a = hash(i);
	float b = hash(i + vec2(1.0, 0.0));
	float c = hash(i + vec2(0.0, 1.0));
	float d = hash(i + vec2(1.0, 1.0));
	vec2 u = f * f * (3.0 - 2.0 * f);
	return mix(a, b, u.x) + (c - a) * u.y * (1.0 - u.x) + (d - b) * u.x * u.y;
}

#define octaves 5
float fbm (in vec2 p) {

    float value = 0.0;
    float freq = 1.0;
    float amp = 0.5;    

    for (int i = 0; i < octaves; i++) {
        value += amp * (noise((p - vec2(1.0)) * freq));
        freq *= 1.9;
        amp *= 0.6;
    }
    return value;
}

float pattern(in vec2 p) {
    vec2 offset = vec2(-0.5);

    vec2 aPos = vec2(sin(GameTime * 0.005), sin(GameTime * 0.01)) * 6.;
    vec2 aScale = vec2(3.0);
    float a = fbm(p * aScale + aPos);

    vec2 bPos = vec2(sin(GameTime * 0.01), sin(GameTime * 0.01)) * 1.;
    vec2 bScale = vec2(2.0);
    float b = fbm((p + a) * bScale + bPos);

    vec2 cPos = vec2(-0.6, -0.5) + vec2(-GameTime * 0.101, GameTime*0.02) * 2.;
    vec2 cScale = vec2(2.6);
    float c = fbm((p + b) * cScale + cPos);
    return c;
}

float fuck(in float val, in float in_min, in float in_max, in float out_min, in float out_max) {
	return out_min + (val-in_min)*(out_max-out_min)/(in_max-in_min);
}

vec3 palette(in float t) {
    vec3 a = vec3(0.0, 0.0, 0.0);
    vec3 b = vec3(1.0, 1.0, 0.9);
    vec3 c = vec3(fuck(sin(GameTime*0.2),-1.0,1.0,0.12,0.15), fuck(sin(GameTime*0.3),-1.0,1.0,0.17,0.2), fuck(cos(GameTime*0.3),-1.0,1.0,0.17,0.2));
    vec3 d = vec3(1.0, 1.0, 1.0);
    return a + b * cos(6.28318 * (c * t + d));
}

void main() {
    float size = min(OutSize.x, OutSize.y);
    vec2 mult = OutSize / size;
    vec2 offset = (1.0 - mult) / 2.0;
    vec2 p = texCoord0.xy * mult + offset;

    vec2 c = p - 0.5;
	float distanceFromCenter = length(c);
	
    // * 0.5 = frequency
	float sinArg = distanceFromCenter * 30.0 - GameTime * 6.0;
	float slope = cos(sinArg);
	
    // * 0.5 = tsuyoi
	p += normalize(c) * slope * 0.001;
	
    float value = pow(pattern(p), 2.2); // more "islands"
    vec3 color = palette(value);
    fragColor = vec4(1.0 - color.x, 1.0 - color.y, 1.0 - color.z, 1.0);
    //gl_FragColor = vec4(fract(texCoord0.x), fract(texCoord0.y), 0.0, 1.0);
}

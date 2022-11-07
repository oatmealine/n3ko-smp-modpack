#version 150

varying vec2 texCoord0;
uniform float GameTime;
uniform vec2 InSize;
uniform vec2 OutSize;

out vec4 fragColor;

const vec3 layer1Color = vec3(0.0, 0.0, 0.0);
const vec3 layer2Color = vec3(1.0, 1.0, 1.0);
const vec3 layer3Color = vec3(0.0, 12.0/255.0, 242.0/255.0);

#define scale 40.0
#define offset 10.0

#define passes 20.0
#define bloomSize 0.2

#define fisheye_amount -0.4

float hash(vec3 p3)
{
    p3.xy *= 400.0;
    p3  = fract(p3 * .1031);
    p3 += dot(p3, p3.yzx + 33.33);
    return fract((p3.x + p3.y) * p3.z);
}

float smoothHash(vec3 p3) {
    return mix(round(hash(vec3(p3.xy, floor(p3.z)))), round(hash(vec3(p3.xy, ceil(p3.z)))), fract(p3.z));
}

vec4 render(vec2 uv) {
    // fisheye

    uv -= 0.5;
    uv *= 1.0 - fisheye_amount / 2.0;

    float r = sqrt(dot(uv,uv));
    uv *= 1.0 + r * fisheye_amount;
    uv += 0.5;

    vec2 res = OutSize.xy;
    uv = clamp( uv, 1.0 / res, (res - 1.0) / res );

    // fisheye end

    uv.x *= OutSize.x / OutSize.y;

    vec3 col = layer1Color;

    vec2 uvPix = round(uv * scale) / scale;
    float hash1 = (smoothHash(vec3(uvPix, GameTime * 3.3 + uv.x * 2.63)));

    uv += offset / OutSize.xy;
    uvPix = round(uv * scale) / scale;
    float hash2 = (smoothHash(vec3(uvPix, GameTime * 5.3 + 90.0 + uvPix.y * 25.3 + uvPix.x * 24.53)));

    col = mix(col, layer2Color, hash1);
    col = mix(col, layer3Color, hash2);

    return vec4(col,1.0);
}

void main()
{
    vec2 uv = texCoord0/OutSize.xy;

    vec4 col = vec4(0.0);

    for (int i = 0; float(i) < passes; i++) {
        float progress = float(i) / passes;
        vec2 scaledUv = (uv - 0.5) * (1.0 + progress * bloomSize) + 0.5;
        vec4 rendered = render(scaledUv);

        float lumi = (rendered.r + rendered.g + rendered.b) / 3.0;

        col += rendered * pow(1.0 - progress, 3.0) * pow(lumi, 2.0);
    }

    fragColor = col;
}
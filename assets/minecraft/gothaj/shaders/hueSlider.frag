#version 120

#ifdef GL_ES
precision mediump float;
#endif

uniform vec2 u_resolution;

vec3 HSBtoRGB(float hue, float saturation, float brightness)
{
    float chroma = brightness * saturation;
    float huePrime = hue * 6.0;
    float x = chroma * (1.0 - abs(mod(huePrime, 2.0) - 1.0));
    vec3 rgbColor;

    if (0.0 <= huePrime && huePrime < 1.0)
        rgbColor = vec3(chroma, x, 0.0);
    else if (1.0 <= huePrime && huePrime < 2.0)
        rgbColor = vec3(x, chroma, 0.0);
    else if (2.0 <= huePrime && huePrime < 3.0)
        rgbColor = vec3(0.0, chroma, x);
    else if (3.0 <= huePrime && huePrime < 4.0)
        rgbColor = vec3(0.0, x, chroma);
    else if (4.0 <= huePrime && huePrime < 5.0)
        rgbColor = vec3(x, 0.0, chroma);
    else if (5.0 <= huePrime && huePrime < 6.0)
        rgbColor = vec3(chroma, 0.0, x);
    else
        rgbColor = vec3(0.0);

    float m = brightness - chroma;
    return rgbColor + vec3(m);
}

void main() {
    vec2 cords =  gl_TexCoord[0].st;
    gl_FragColor=vec4(HSBtoRGB(cords.x,1.,1.0),1.);
}
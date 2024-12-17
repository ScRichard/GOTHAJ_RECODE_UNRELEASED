#version 120

uniform sampler2D u_texture1;
uniform sampler2D u_texture2;

uniform vec2 u_direction;
uniform vec2 u_texel_size;

uniform float u_radius;
uniform float u_saturation;
uniform float u_kernel[256];

void main(void) {

    vec2 uv = gl_TexCoord[0].st;

    if(u_direction.x == 0.0) {
        float alpha = texture2D(u_texture2, uv).a;
        if (alpha > 0.0) discard;
    }

    vec4 color = texture2D(u_texture1, uv);
    color.a *= u_kernel[0];
    color.rgb *= color.a;
    for(float r = 1.0; r <= u_radius; r++) {
        vec2 offset = r * u_texel_size * u_direction;
	vec4 left = texture2D(u_texture1, uv - offset); 
    	vec4 right = texture2D(u_texture1, uv + offset); 
        color.a += left.a * u_kernel[int(r)];
        color.a += right.a * u_kernel[int(r)];
	color.rgb += (left.rgb*left.a + right.rgb*right.a) * u_kernel[int(r)];
    }

    gl_FragColor = vec4(color.rgb / color.a, mix(color.a, 1.0 - exp(-color.a * u_saturation), step(0., u_direction.y)));
}
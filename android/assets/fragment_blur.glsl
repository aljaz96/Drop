#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

void main() {
    float cifra = 0.005f;
    // Grouping texcoord variables in order to make it work in the GMA 950. See post #13
    // in this thread:
    // <a href="http://www.idevgames.com/forums/thread-3467.html" target="_blank" rel="nofollow">http://www.idevgames.com/forums/thread-3467.html</a>
    vec2 tc0 = v_texCoords.st + vec2(-cifra, -cifra);
    vec2 tc1 = v_texCoords.st + vec2(         0.0, -cifra);
    vec2 tc2 = v_texCoords.st + vec2(+cifra, -cifra);
    vec2 tc3 = v_texCoords.st + vec2(-cifra,          0.0);
    vec2 tc4 = v_texCoords.st + vec2(         0.0,          0.0);
    vec2 tc5 = v_texCoords.st + vec2(+cifra,          0.0);
    vec2 tc6 = v_texCoords.st + vec2(-cifra, +cifra);
    vec2 tc7 = v_texCoords.st + vec2(         0.0, +cifra);
    vec2 tc8 = v_texCoords.st + vec2(+cifra, +cifra);

    vec4 col0 = texture2D(u_texture, tc0);
    vec4 col1 = texture2D(u_texture, tc1);
    vec4 col2 = texture2D(u_texture, tc2);
    vec4 col3 = texture2D(u_texture, tc3);
    vec4 col4 = texture2D(u_texture, tc4);
    vec4 col5 = texture2D(u_texture, tc5);
    vec4 col6 = texture2D(u_texture, tc6);
    vec4 col7 = texture2D(u_texture, tc7);
    vec4 col8 = texture2D(u_texture, tc8);

    vec4 sum = (1.0 * col0 + 2.0 * col1 + 1.0 * col2 +
                2.0 * col3 + 4.0 * col4 + 2.0 * col5 +
                1.0 * col6 + 2.0 * col7 + 1.0 * col8) / 16.0;
    if(sum.a > 0.2f){
        gl_FragColor = vec4(sum.rgb, 1.0) * v_color;
    }
    else{
        gl_FragColor = vec4(sum.rgb, 0) * v_color;
    }
}

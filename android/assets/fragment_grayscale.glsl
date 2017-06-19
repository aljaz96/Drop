#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

void main() {
        vec4 color = texture2D(u_texture, v_texCoords);
        float gray = (0.3 * color.r + 0.59 * color.g + 0.11 * color.b);
        vec3 grayscale = vec3(gray);

        gl_FragColor = vec4(grayscale, color.a);
}
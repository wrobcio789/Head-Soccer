package utility;

public class vec2 {
    public float x, y;

    public vec2(float x, float y){
        this.x = x;
        this.y = y;
    }

    public vec2 add(vec2 other){
        return new vec2(x + other.x, y + other.y);
    }

    public vec2 sub(vec2 other) {
        return new vec2(x - other.x, y - other.y);
    }

    public vec2 mul(float factor) {
        return new vec2(x * factor, y * factor);
    }

    public vec2 div(float divisor) {
        return new vec2(x/divisor, y/divisor);
    }

    public float dotProduct(vec2 other) {
        return x * other.x + y * other.y;
    }

    public vec2 getPerpendicularNormalized(){
        return new vec2(y, -x).normalize();
    }

    public float distance(vec2 other){
        return (this.sub(other)).length();
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public vec2 normalize() {
        float length = length();
        return new vec2(x/length, y/length);
    }
}

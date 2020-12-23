package evolution.move;

import java.util.Objects;

public class Vector2d {

    public final int x;
    public final int y;


    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public boolean precedes(Vector2d other) {
        return (other.x >= this.x && other.y >= this.y);
    }


    public boolean follows(Vector2d other) {
        return (other.x <= this.x && other.y <= this.y);
    }


    public Vector2d upperRight(Vector2d other) {
        int x, y;
        if (other.x >= this.x) x = other.x;
        else x = this.x;

        if (other.y >= this.y) y = other.y;
        else y = this.y;

        return new Vector2d(x,y);
    }


    public Vector2d lowerLeft(Vector2d other) {
        int x, y;
        if (other.x <= this.x) x = other.x;
        else x = this.x;

        if (other.y <= this.y) y = other.y;
        else y = this.y;

        return new Vector2d(x,y);
    }


    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }


    public Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }


    public Vector2d opposite() {
        return new Vector2d(-this.x, -this.y);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x &&
                y == vector2d.y;
    }


    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


    @Override
    public String toString() {
        return "(" + x +
                "," + y +
                ')';
    }
}
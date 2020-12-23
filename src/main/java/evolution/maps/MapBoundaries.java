package evolution.maps;

import evolution.move.Vector2d;
import java.lang.Math;

import java.util.Vector;

public class MapBoundaries {
    Vector2d lowerLeft;
    Vector2d upperRight;
    private final static int LOWER = 0;

    public MapBoundaries(Vector2d lowerLeft, Vector2d upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    public MapBoundaries(int height, int width) {
        this(new Vector2d(LOWER, LOWER), new Vector2d(width, height));
    }

    public int getDimensionValue(int oldValue, double ratio) {
        return (int) (Math.sqrt(ratio) * oldValue);
    }

    public int getStartingPoint(int dim1, int dim2) {
        return (dim1-dim2)/2;
    }

    public MapBoundaries(int height, int width, double jungleRatio) {
        int jungleWidth = getDimensionValue(width,jungleRatio);
        int jungleHeight = getDimensionValue(height,jungleRatio);

        int xStart = getStartingPoint(width,jungleWidth);
        int yStart = getStartingPoint(height,jungleHeight);

        this.lowerLeft = new Vector2d(xStart,yStart);
        this.upperRight = new Vector2d(xStart + width - 1, yStart + height -1);
    }

    public boolean insideBoundaries(Vector2d position) {
        return position.x <= upperRight.x && position.x >= lowerLeft.x && position.y <= upperRight.y && position.y >= lowerLeft.y;
    }

    public Vector2d getLowerLeft() {
        return lowerLeft;
    }

    public Vector2d getUpperRight() {
        return upperRight;
    }
}
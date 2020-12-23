package evolution.move;

import java.util.Map;

public enum MapDirection {
    NORTH, NORTHEAST, EAST,  SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST;

    public MapDirection changeDirection(int i) {
        return MapDirection.values()[(this.ordinal() + i) % MapDirection.values().length];
    }



    public Vector2d toUnitVector() {
        switch (this) {
            case NORTH : return new Vector2d(0,1);
            case NORTHEAST: return new Vector2d(1,1);
            case EAST : return new Vector2d(1,0);
            case SOUTHEAST: return new Vector2d(1,-1);
            case SOUTH: return new Vector2d(0,-1);
            case SOUTHWEST: return new Vector2d(-1,-1);
            case WEST: return new Vector2d(-1,0);
            case NORTHWEST: return new Vector2d(-1,1);
        };
        throw new IllegalArgumentException("invalid direction");
    }
}

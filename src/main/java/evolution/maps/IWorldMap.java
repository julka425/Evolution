package evolution.maps;

import evolution.elements.Animal;
import evolution.move.Vector2d;
import java.util.Optional;


public interface IWorldMap {

    boolean place(Animal animal);

    Vector2d loopedPosition(int x, int y);

    boolean isOccupied(Vector2d position);

    Optional<Object> objectAt(Vector2d position);
}

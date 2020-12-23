package evolution.observers;

import evolution.elements.Animal;
import evolution.elements.IWorldMapElement;
import evolution.maps.IWorldMap;
import evolution.move.Vector2d;

public interface IPositionChangedObserver {
    void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition);
    //void objectRemoved(IWorldMapElement element);
}

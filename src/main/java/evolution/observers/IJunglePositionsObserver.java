package evolution.observers;

import evolution.elements.IWorldMapElement;
import evolution.maps.IWorldMap;
import evolution.move.Vector2d;

public interface IJunglePositionsObserver {
    void objectRemoved(IWorldMapElement element, Vector2d position);

    void JunglePositionChanged(IWorldMapElement element, Vector2d oldPosition, Vector2d newPosition);

    void objectAdded(IWorldMapElement element, Vector2d position);

}

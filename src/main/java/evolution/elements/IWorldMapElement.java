package evolution.elements;
import evolution.move.Vector2d;
import evolution.observers.IPositionChangedObserver;
import evolution.observers.IPositionChangedPublisher;

public interface IWorldMapElement {

    Vector2d getPosition();
    int getEnergy();
    boolean canMove();

}

package evolution.observers;

public interface IPositionChangedPublisher {

    void addObserver(IPositionChangedObserver observer);

    void removeObserver(IPositionChangedObserver observer);
}

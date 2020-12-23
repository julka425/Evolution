package evolution.observers;

public interface IJunglePositionPublisher {

    void addJungleObserver(IJunglePositionsObserver observer);

    void removeJungleObserver(IJunglePositionsObserver observer);
}

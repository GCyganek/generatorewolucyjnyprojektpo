package map;

public interface IPositionChangedPublisher {
    void addObserver(IPositionChangeObserver observer);

    void removeObserver(IPositionChangeObserver observer);
}

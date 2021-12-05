package simulation.util;

public interface IAnimalPlacedPublisher {
    void addObserver(IAnimalPlacedObserver observer);

    void removeObserver(IAnimalPlacedObserver observer);
}

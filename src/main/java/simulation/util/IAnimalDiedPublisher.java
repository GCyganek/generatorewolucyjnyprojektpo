package simulation.util;

public interface IAnimalDiedPublisher {
    void addObserver(IAnimalDiedObserver observer);

    void removeObserver(IAnimalDiedObserver observer);
}

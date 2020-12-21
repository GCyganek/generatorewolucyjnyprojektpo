package Interfaces.Publishers;

import Interfaces.Observers.IAnimalPlacedObserver;

public interface IAnimalPlacedPublisher {
    void addObserver(IAnimalPlacedObserver observer);

    void removeObserver(IAnimalPlacedObserver observer);
}

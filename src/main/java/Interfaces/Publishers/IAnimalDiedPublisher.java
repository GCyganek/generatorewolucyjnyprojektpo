package Interfaces.Publishers;

import Interfaces.Observers.IAnimalDiedObserver;

public interface IAnimalDiedPublisher {
    void addObserver(IAnimalDiedObserver observer);

    void removeObserver(IAnimalDiedObserver observer);
}

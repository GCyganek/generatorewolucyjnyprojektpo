package Interfaces.Publishers;

import Interfaces.Observers.IPositionChangeObserver;

public interface IPositionChangedPublisher {
    void addObserver(IPositionChangeObserver observer);

    void removeObserver(IPositionChangeObserver observer);
}

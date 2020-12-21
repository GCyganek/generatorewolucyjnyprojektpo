package Interfaces.Publishers;

import Interfaces.Observers.ITimePassedObserver;

public interface ITimePassedPublisher {
    void addObserver(ITimePassedObserver observer, long day);

    void removeObserver(ITimePassedObserver observer);
}

package simulation.util;

public interface ITimePassedPublisher {
    void addObserver(ITimePassedObserver observer, long day);

    void removeObserver(ITimePassedObserver observer);
}

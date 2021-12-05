package visualisation.mapStatus.map;

public interface IFieldClickedPublisher {
    void addObserver(IFieldClickedObserver observer);

    void removeObserver(IFieldClickedObserver observer);
}

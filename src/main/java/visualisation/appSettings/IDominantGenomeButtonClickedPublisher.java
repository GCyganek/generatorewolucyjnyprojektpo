package visualisation.appSettings;

public interface IDominantGenomeButtonClickedPublisher {
    void addObserver(IDominantGenomeButtonClickedObserver observer);

    void removeObserver(IDominantGenomeButtonClickedObserver observer);
}

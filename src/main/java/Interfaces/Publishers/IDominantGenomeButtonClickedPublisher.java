package Interfaces.Publishers;

import Interfaces.Observers.IDominantGenomeButtonClickedObserver;

public interface IDominantGenomeButtonClickedPublisher {
    void addObserver(IDominantGenomeButtonClickedObserver observer);

    void removeObserver(IDominantGenomeButtonClickedObserver observer);
}

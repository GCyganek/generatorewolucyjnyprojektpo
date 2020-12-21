package Interfaces.Publishers;

import Interfaces.Observers.IFieldClickedObserver;

public interface IFieldClickedPublisher {
    void addObserver(IFieldClickedObserver observer);

    void removeObserver(IFieldClickedObserver observer);
}

package it.polimi.ingsw.observer;

public class Observable<T> {

    private transient Observer observer = null;

    public void addObserver(Observer<T> observer){
        this.observer = observer;
    }

    public void notify(T action){
        observer.update(action);
    }
}

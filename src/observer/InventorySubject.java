package observer;

import java.util.ArrayList;
import java.util.List;

public class InventorySubject {

    private static InventorySubject instance;
    private final List<Observer> observers = new ArrayList<>();

    private InventorySubject() {}

    public static InventorySubject getInstance() {
        if (instance == null) {
            instance = new InventorySubject();
        }
        return instance;
    }

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }

    public void bookUpdated(String bookName) {
        notifyObservers("📚 '" + bookName + "' kitabının envanteri güncellendi.");
    }
}

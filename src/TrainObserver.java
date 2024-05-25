import java.util.ArrayList;
import java.util.List;

class Train {
    private String name;
    private int availableSeats;
    private List<Observer> observers = new ArrayList<>();

    public Train(String name, int availableSeats) {
        this.name = name;
        this.availableSeats = availableSeats;
    }

    public String getName() {
        return name;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
            notifyObservers("Seat booked on " + name);
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    private void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
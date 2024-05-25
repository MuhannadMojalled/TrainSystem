import java.util.ArrayList;
import java.util.List;

class Train {
    private String name;
    private String number;
    private int availableSeats;
    private List<Observer> observers = new ArrayList<>();

    public Train(String name, String number, int availableSeats) {
        this.name = name;
        this.number = number;
        this.availableSeats = availableSeats;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {return number;}

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

class Train {
    private String name;
    private String number;
    private String dist;
    private int availableSeats;
    private Observer observer;

    public Train(String name, String number,String dist, int availableSeats) {
        this.name = name;
        this.number = number;
        this.dist = dist;
        this.availableSeats = availableSeats;
    }

    public String getDist() {return dist;}

    public String getName() {
        return name;
    }

    public String getNumber() {return number;}

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void bookSeat() {
        if (availableSeats > 0) {
            --availableSeats;
            notifyObservers("Seat booked on " + name + " | Distention: " + dist + " | Available seats after your booking: " + availableSeats + " seats");
        }
    }

    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    private void notifyObservers(String message) {
        observer.update(message);
    }
}
import java.util.ArrayList;
import java.util.List;

public class ReservationSystemSingleton {
    private static ReservationSystemSingleton instance;
    private List<Train> trains = new ArrayList<>();

    private ReservationSystemSingleton() {
        //Initialize with some trains
        trains.add(new Train("Train1", 100));
        trains.add(new Train("Train2", 150));
    }

    public static synchronized ReservationSystemSingleton getInstance() {
        if (instance == null) {
            instance = new ReservationSystemSingleton();
        }
        return instance;
    }

    public List<Train> getTrains() {
        return trains;
    }

    public Ticket bookTicket(String trainName, String ticketType) {
        for (Train train : trains) {
            if (train.getName().equals(trainName) && train.getAvailableSeats() > 0) {
                Ticket ticket = TicketFactory.createTicket(ticketType);
                train.bookSeat();
                return ticket;
            }
        }
        return null;
    }
}
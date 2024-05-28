import java.util.ArrayList;
import java.util.List;

public class ReservationSystemSingleton {
    private static ReservationSystemSingleton instance;
    private List<Train> trains = new ArrayList<>();

    private ReservationSystemSingleton() {
        //Initialize with some trains
        trains.add(new Train("Train1","1"," Jeddah ---> Riyadh", 50));
        trains.add(new Train("Train2","2"," Riyadh ---> Jeddah", 80));
        trains.add(new Train("Train3","3"," Mecca  ---> Jeddah",80));
        trains.add(new Train("Train4","4"," Jeddah ---> Mecca",80));
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

    public Ticket bookTicket(String trainNum, String ticketType) {
        for (Train train : trains) {
            if (train.getNumber().equalsIgnoreCase(trainNum)) {
                Ticket ticket = TicketFactory.createTicket(ticketType);
                train.bookSeat();
                return ticket;
            }
        }
        return null;
    }
}
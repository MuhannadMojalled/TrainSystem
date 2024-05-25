import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Singleton Pattern
class ReservationSystem {
    private static ReservationSystem instance;
    private List<Train> trains = new ArrayList<>();

    private ReservationSystem() {
        // Initialize with some trains
        trains.add(new Train("Train1", 100));
        trains.add(new Train("Train2", 150));
    }

    public static synchronized ReservationSystem getInstance() {
        if (instance == null) {
            instance = new ReservationSystem();
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

// Factory Pattern
class TicketFactory {
    public static Ticket createTicket(String type) {
        switch (type) {
            case "Standard":
                return new StandardTicket();
            case "FirstClass":
                return new FirstClassTicket();
            default:
                return null;
        }
    }
}

interface Ticket {
    String getDescription();
    double getCost();
}

class StandardTicket implements Ticket {
    @Override
    public String getDescription() {
        return "Standard Ticket";
    }

    @Override
    public double getCost() {
        return 50.0;
    }
}

class FirstClassTicket implements Ticket {
    @Override
    public String getDescription() {
        return "First Class Ticket";
    }

    @Override
    public double getCost() {
        return 100.0;
    }
}

// Decorator Pattern
abstract class TicketDecorator implements Ticket {
    protected Ticket decoratedTicket;

    public TicketDecorator(Ticket ticket) {
        this.decoratedTicket = ticket;
    }

    @Override
    public String getDescription() {
        return decoratedTicket.getDescription();
    }

    @Override
    public double getCost() {
        return decoratedTicket.getCost();
    }
}

class MealDecorator extends TicketDecorator {
    public MealDecorator(Ticket ticket) {
        super(ticket);
    }

    @Override
    public String getDescription() {
        return decoratedTicket.getDescription() + ", with Meal";
    }

    @Override
    public double getCost() {
        return decoratedTicket.getCost() + 20.0;
    }
}

class LuggageDecorator extends TicketDecorator {
    public LuggageDecorator(Ticket ticket) {
        super(ticket);
    }

    @Override
    public String getDescription() {
        return decoratedTicket.getDescription() + ", with Extra Luggage";
    }

    @Override
    public double getCost() {
        return decoratedTicket.getCost() + 30.0;
    }
}

// Observer Pattern
interface Observer {
    void update(String message);
}

class User implements Observer {
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println("Notification for " + name + ": " + message);
    }
}

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

// Strategy Pattern
interface PricingStrategy {
    double calculatePrice(Ticket ticket);
}

class StandardPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(Ticket ticket) {
        return ticket.getCost();
    }
}

class DiscountPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(Ticket ticket) {
        return ticket.getCost() * 0.9; // 10% discount
    }
}

// Main class to demonstrate the system
public class App {
    public static void main(String[] args) {
        ReservationSystem system = ReservationSystem.getInstance();
        Scanner scanner = new Scanner(System.in);

        // Create users
        User user1 = new User("Alice");
        User user2 = new User("Bob");

        // Add observers
        for (Train train : system.getTrains()) {
            train.addObserver(user1);
            train.addObserver(user2);
        }

        while (true) {
            System.out.println("Welcome to Train Reservation System");
            System.out.println("Available Trains:");
            for (Train train : system.getTrains()) {
                System.out.println(train.getName() + " - Available Seats: " + train.getAvailableSeats());
            }

            System.out.print("Enter Train Name to book: ");
            String trainName = scanner.nextLine();

            System.out.print("Enter Ticket Type (Standard/FirstClass): ");
            String ticketType = scanner.nextLine();

            Ticket ticket = system.bookTicket(trainName, ticketType);
            if (ticket == null) {
                System.out.println("Booking failed! Either train not found or no available seats.");
                continue;
            }

            System.out.print("Add Meal (yes/no): ");
            String addMeal = scanner.nextLine();
            if (addMeal.equalsIgnoreCase("yes")) {
                ticket = new MealDecorator(ticket);
            }

            System.out.print("Add Extra Luggage (yes/no): ");
            String addLuggage = scanner.nextLine();
            if (addLuggage.equalsIgnoreCase("yes")) {
                ticket = new LuggageDecorator(ticket);
            }

            System.out.println("Ticket booked: " + ticket.getDescription() + " - Total Cost: $" + ticket.getCost());

            // Apply pricing strategy
            PricingStrategy discountStrategy = new DiscountPricingStrategy();
            System.out.println("Discounted price: $" + discountStrategy.calculatePrice(ticket));

            System.out.print("Do you want to book another ticket? (yes/no): ");
            String anotherBooking = scanner.nextLine();
            if (anotherBooking.equalsIgnoreCase("no")) {
                break;
            }
        }

        scanner.close();
        System.out.println("Thank you for using the Train Reservation System.");
    }
}
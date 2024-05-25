import java.util.Scanner;

public class TrainReservationMainApp {
    public static void main(String[] args) {
        ReservationSystemSingleton system = ReservationSystemSingleton.getInstance();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String userName = scanner.nextLine();
        User user = new User(userName);

        //Add observer
        for (Train train : system.getTrains()) {
            train.addObserver(user);
        }

        while (true) {
            System.out.println("Welcome to Train Reservation System");
            System.out.println("Available Trains:");
            for (Train train : system.getTrains()) {
                System.out.println(train.getNumber() +": "+train.getDist() +", currently has: " + train.getAvailableSeats() +" seats available");
            }

            BookingContext context = new BookingContext();
            NewBookingState newState = new NewBookingState();
            newState.handle(context);

            TicketBookingTemplate booking = new StandardBooking(system);
            booking.book();

            InProgressBookingState inProgressState = new InProgressBookingState();
            inProgressState.handle(context);

            CompletedBookingState completedState = new CompletedBookingState();
            completedState.handle(context);

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
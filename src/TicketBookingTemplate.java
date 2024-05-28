import java.util.List;
import java.util.Scanner;

abstract class TicketBookingTemplate {
    protected Ticket ticket;

    public final void book() {
        selectTrain();
        selectTicketType();
        addFeatures();
        applyPricingStrategy();
    }

    protected abstract void selectTrain();
    protected abstract void selectTicketType();
    protected abstract void addFeatures();
    protected abstract void applyPricingStrategy();
}

class StandardBooking extends TicketBookingTemplate {
    private ReservationSystemSingleton system;
    private String trainNum;
    Scanner scanner = new Scanner(System.in);
    List<Train> trains;


    public StandardBooking(ReservationSystemSingleton system) {
        this.system = system;
        trains = this.system.getTrains();
    }

    @Override
    protected void selectTrain() {
        System.out.print("Enter the train number you want to book. Train NUM: ");
        trainNum = scanner.nextLine();
        System.out.println(trains.get(Integer.valueOf(trainNum)).getAvailableSeats());
        while (true) {
            if ((trainNum.equals("1") || trainNum.equals("2") || trainNum.equals("3") || trainNum.equals("4"))) {
                if (trains.get(Integer.valueOf(trainNum)-1).getAvailableSeats() > 0) {
                    break;
                }
            }
            System.out.print("Please enter a valid train number with avalible seats! (1/2/3/4) If all seats are full, write 'full' to exit: ");
            trainNum = scanner.nextLine();
            if (trainNum.equals("full")) {
                System.out.println("Thank you for using our train reservation system!");
                System.exit(0);
            }
        }
    }

    @Override
    protected void selectTicketType() {
        System.out.print("Enter ticket type (Standard/FirstClass): ");
        String ticketType = scanner.nextLine();
        while (!(ticketType.equalsIgnoreCase("Standard") || ticketType.equalsIgnoreCase("FirstClass"))) {
            System.out.print("Please enter a valid ticket type! (Standard/FirstClass): ");
            ticketType = scanner.nextLine();
        }
        Command bookTicket = new BookTicketCommand(system, trainNum, ticketType);
        bookTicket.execute();
        ticket = ((BookTicketCommand) bookTicket).getTicket();
    }

    @Override
    protected void addFeatures() {
        System.out.print("Add meal? (yes/no): ");
        String addMeal = scanner.nextLine();
        while (!(addMeal.equalsIgnoreCase("yes") || addMeal.equalsIgnoreCase("no"))) {
            System.out.print("Please enter a valid answer! Add meal? (yes/no): ");
            addMeal = scanner.nextLine();
        }
        if (addMeal.equalsIgnoreCase("yes")) {
            Command addMealCommand = new AddFeatureCommand(ticket, "meal");
            addMealCommand.execute();
            ticket = ((AddFeatureCommand) addMealCommand).getTicket();
        }

        System.out.print("Add extra luggage? (yes/no): ");
        String addLuggage = scanner.nextLine();
        while (!(addLuggage.equalsIgnoreCase("yes") || addLuggage.equalsIgnoreCase("no"))) {
            System.out.print("Please enter a valid answer! Add extra Luggage? (yes/no): ");
            addLuggage = scanner.nextLine();
        }
        if (addLuggage.equalsIgnoreCase("yes")) {
            Command addLuggageCommand = new AddFeatureCommand(ticket, "luggage");
            addLuggageCommand.execute();
            ticket = ((AddFeatureCommand) addLuggageCommand).getTicket();
        }
    }

    @Override
    protected void applyPricingStrategy() {
        PricingStrategy discountStrategy = new DiscountPricingStrategy();
        System.out.println("Discounted price: $" + discountStrategy.calculatePrice(ticket));
    }
}
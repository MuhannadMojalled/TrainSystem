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
    private String trainName;
    Scanner scanner = new Scanner(System.in);


    public StandardBooking(ReservationSystemSingleton system) {
        this.system = system;
    }

    @Override
    protected void selectTrain() {
        //Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Train Name to book: ");
        trainName = scanner.nextLine();
    }

    @Override
    protected void selectTicketType() {
        //Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Ticket Type (Standard/FirstClass): ");
        String ticketType = scanner.nextLine();
        Command bookTicket = new BookTicketCommand(system, trainName, ticketType);
        bookTicket.execute();
        ticket = ((BookTicketCommand) bookTicket).getTicket();
    }

    @Override
    protected void addFeatures() {
        //Scanner scanner = new Scanner(System.in);
        System.out.print("Add Meal (yes/no): ");
        String addMeal = scanner.nextLine();
        if (addMeal.equalsIgnoreCase("yes")) {
            Command addMealCommand = new AddFeatureCommand(ticket, "meal");
            addMealCommand.execute();
            ticket = ((AddFeatureCommand) addMealCommand).getTicket();
        }

        System.out.print("Add Extra Luggage (yes/no): ");
        String addLuggage = scanner.nextLine();
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
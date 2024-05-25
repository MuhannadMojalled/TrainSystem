interface Command {
    void execute();
}

class BookTicketCommand implements Command {
    private ReservationSystemSingleton system;
    private String trainName;
    private String ticketType;
    private Ticket ticket;

    public BookTicketCommand(ReservationSystemSingleton system, String trainName, String ticketType) {
        this.system = system;
        this.trainName = trainName;
        this.ticketType = ticketType;
    }

    @Override
    public void execute() {
        ticket = system.bookTicket(trainName, ticketType);
        if (ticket == null) {
            System.out.println("Booking failed! Either train not found or no available seats.");
        } else {
            System.out.println("Ticket booked: " + ticket.getDescription() + " - Cost: $" + ticket.getCost());
        }
    }

    public Ticket getTicket() {
        return ticket;
    }
}

class AddFeatureCommand implements Command {
    private Ticket ticket;
    private String feature;

    public AddFeatureCommand(Ticket ticket, String feature) {
        this.ticket = ticket;
        this.feature = feature;
    }

    @Override
    public void execute() {
        if (feature.equalsIgnoreCase("meal")) {
            ticket = new MealDecorator(ticket);
        } else if (feature.equalsIgnoreCase("luggage")) {
            ticket = new LuggageDecorator(ticket);
        }
        System.out.println("Updated ticket: " + ticket.getDescription() + " - Cost: $" + ticket.getCost());
    }

    public Ticket getTicket() {
        return ticket;
    }
}
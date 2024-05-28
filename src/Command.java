interface Command {
    void execute();
}

class BookTicketCommand implements Command {
    private ReservationSystemSingleton system;
    private String trainNum;
    private String ticketType;
    private Ticket ticket;

    public BookTicketCommand(ReservationSystemSingleton system, String trainNum, String ticketType) {
        this.system = system;
        this.trainNum = trainNum;
        this.ticketType = ticketType;
    }

    @Override
    public void execute() {
        ticket = system.bookTicket(trainNum, ticketType);
        System.out.println("Ticket booked: " + ticket.getDescription() + " - Cost: $" + ticket.getCost());
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
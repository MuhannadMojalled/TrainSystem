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
public class TicketFactory {
    public static Ticket createTicket(String type) {
        switch (type.toUpperCase()) {
            case "STANDARD":
                return new StandardTicket();
            case "FIRSTCLASS":
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
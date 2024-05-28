interface BookingState {
    void handle(BookingContext context);
}

class NewBookingState implements BookingState {
    @Override
    public void handle(BookingContext context) {
        System.out.println("Starting a new booking...");
        context.setState(this);
    }

    @Override
    public String toString() {
        return "New Booking State";
    }
}

class InProgressBookingState implements BookingState {
    @Override
    public void handle(BookingContext context) {
        System.out.println("Booking is in progress...");
        context.setState(this);
    }

    @Override
    public String toString() {
        return "In Progress Booking State";
    }
}

class CompletedBookingState implements BookingState {
    @Override
    public void handle(BookingContext context) {
        System.out.println("Booking is completed.");
        context.setState(this);
    }

    @Override
    public String toString() {
        return "Completed Booking State";
    }
}

class BookingContext {
    private BookingState state;

    public BookingContext() {
        state = null;
    }

    public void setState(BookingState state) {
        this.state = state;
    }

    public BookingState getState() {
        return state;
    }
}
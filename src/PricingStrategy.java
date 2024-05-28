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
        return ticket.getCost() * 0.9; //10% discount
    }
}
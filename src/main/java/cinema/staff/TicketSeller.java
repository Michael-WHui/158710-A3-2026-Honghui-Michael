package cinema.staff;

public class TicketSeller extends Staff{

    public TicketSeller(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRole() {
        return "Ticket Seller";
    }

    @Override
    public boolean canManageMovies() {
        return false;
    }

    @Override
    public boolean canSellTickets() {
        return true;
    }
}

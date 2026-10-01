package cinema.staff;

public class Manager extends Staff{

    public Manager(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRole() {
        return "Manager";
    }

    @Override
    public boolean canManageMovies() {
        return true;
    }

    @Override
    public boolean canSellTickets() {
        return true;
    }
}

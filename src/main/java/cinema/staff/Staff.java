package cinema.staff;

public abstract class Staff {

    private String username;
    private String password;

    public Staff(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public abstract String getRole();

    public boolean canManageMovies() {
        return false;
    }

    public boolean canSellTickets() {
        return true;
    }
}

package cinema.model;

public class ActionMovie extends Movie {
    private String stuntLevel;

    public ActionMovie(String movieId,
                       String title,
                       String director,
                       int duration,
                       double price,
                       String showTime,
                       String stuntLevel,
                       int availableTickets) {

        super(movieId, title, director, duration, price,
                showTime, availableTickets);

        this.stuntLevel = stuntLevel;
    }

    public String getStuntLevel() {
        return stuntLevel;
    }

    public void setStuntLevel(String stuntLevel) {
        this.stuntLevel = stuntLevel;
    }

    @Override
    public String getCategory() {
        return "Action";
    }

    @Override
    public String getExtraAttribute() {
        return stuntLevel;
    }
}

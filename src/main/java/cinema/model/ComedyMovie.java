package cinema.model;

public class ComedyMovie extends  Movie{

    public ComedyMovie(String movieId,
                       String title,
                       String director,
                       int duration,
                       double price,
                       String showTime,
                       int availableTickets) {

        super(movieId, title, director, duration, price,
                showTime, availableTickets);
    }

    @Override
    public String getCategory() {
        return "Comedy";
    }

    @Override
    public String getExtraAttribute() {
        return "-";
    }
}

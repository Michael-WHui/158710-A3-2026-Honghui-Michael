package cinema.model;

public class ScienceFictionMovie extends Movie{

    private String specialFormat;

    public ScienceFictionMovie(String movieId,
                               String title,
                               String director,
                               int duration,
                               double price,
                               String showTime,
                               String specialFormat,
                               int availableTickets) {

        super(movieId, title, director, duration, price,
                showTime, availableTickets);

        this.specialFormat = specialFormat;
    }

    public String getSpecialFormat() {
        return specialFormat;
    }

    public void setSpecialFormat(String specialFormat) {
        this.specialFormat = specialFormat;
    }

    @Override
    public String getCategory() {
        return "ScienceFiction";
    }

    @Override
    public String getExtraAttribute() {
        return specialFormat;
    }
}

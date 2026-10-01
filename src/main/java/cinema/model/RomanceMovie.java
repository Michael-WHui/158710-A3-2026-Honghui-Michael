package cinema.model;

public class RomanceMovie extends Movie{

    private String ageRestriction;

    public RomanceMovie(String movieId,
                        String title,
                        String director,
                        int duration,
                        double price,
                        String showTime,
                        String ageRestriction,
                        int availableTickets) {

        super(movieId, title, director, duration, price,
                showTime, availableTickets);

        this.ageRestriction = ageRestriction;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(String ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    @Override
    public String getCategory() {
        return "Romance";
    }

    @Override
    public String getExtraAttribute() {
        return ageRestriction;
    }
}

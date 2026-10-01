package cinema.model;

public abstract class Movie {

    private String movieId;
    private String title;
    private String director;
    private int duration;
    private double price;
    private String showTime;
    private int availableTickets;

    public Movie(String movieId,
                 String title,
                 String director,
                 int duration,
                 double price,
                 String showTime,
                 int availableTickets) {

        this.movieId = movieId;
        this.title = title;
        this.director = director;
        this.duration = duration;
        this.price = price;
        this.showTime = showTime;
        this.availableTickets = availableTickets;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    public abstract String getCategory();
    public abstract String getExtraAttribute();

    public boolean bookTicket() {
        if (availableTickets <= 0) {
            return false;
        }

        availableTickets--;
        return true;
    }

    public String toFileString() {
        return String.format(
                "%s, %s, %s, %s, %d, %.1f, %s, %s, %d",
                getCategory(),
                movieId,
                title,
                director,
                duration,
                price,
                showTime,
                getExtraAttribute(),
                availableTickets
        );
    }

    @Override
    public String toString() {
        return movieId + " - " + title;
    }
}

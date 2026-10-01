package cinema.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {

    @Test
    void actionMovieShouldStoreCorrectInformation() {

        ActionMovie movie = new ActionMovie(
                "A001",
                "Fast & Furious 9",
                "Justin Lin",
                143,
                15.5,
                "18:30",
                "High",
                50
        );

        assertEquals("A001", movie.getMovieId());
        assertEquals("Fast & Furious 9", movie.getTitle());
        assertEquals("Justin Lin", movie.getDirector());
        assertEquals(143, movie.getDuration());
        assertEquals(15.5, movie.getPrice());
        assertEquals("18:30", movie.getShowTime());
        assertEquals("High", movie.getStuntLevel());
        assertEquals(50, movie.getAvailableTickets());
        assertEquals("Action", movie.getCategory());
    }

    @Test
    void bookingShouldReduceAvailableTickets() {
        ComedyMovie movie = new ComedyMovie(
                "C001",
                "Mr. Bean's Holiday",
                "Steve Bendelack",
                90,
                12.0,
                "14:00",
                50
        );

        assertTrue(movie.bookTicket());
        assertEquals(49, movie.getAvailableTickets());
    }

    @Test
    void bookingShouldFailWhenNoTicketsAreAvailable() {

        ComedyMovie movie = new ComedyMovie(
                "C001",
                "Mr. Bean's Holiday",
                "Steve Bendelack",
                90,
                12.0,
                "14:00",
                0
        );

        assertFalse(movie.bookTicket());
        assertEquals(0, movie.getAvailableTickets());
    }

    @Test
    void toFileStringShouldUseCorrectFormat() {

        RomanceMovie movie = new RomanceMovie(
                "R001",
                "Titanic",
                "James Cameron",
                195,
                18.0,
                "20:00",
                "R13",
                50
        );

        assertEquals(
                "Romance, R001, Titanic, James Cameron, 195, 18.0, 20:00, R13, 50",
                movie.toFileString()
        );
    }
}

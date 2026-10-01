package cinema.service;

import cinema.exception.DuplicateMovieException;
import cinema.exception.MovieNotFoundException;
import cinema.model.ActionMovie;
import cinema.model.ComedyMovie;
import cinema.model.Movie;
import cinema.model.RomanceMovie;
import cinema.model.ScienceFictionMovie;
import cinema.service.MovieManager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieManagerTest {

    @Test
    void shouldAddMovie() throws DuplicateMovieException {

        MovieManager manager = new MovieManager();

        Movie movie = new ActionMovie(
                "A100",
                "Test Action Movie",
                "Test Director",
                120,
                15.0,
                "18:00",
                "High",
                50
        );

        manager.addMovie(movie);

        assertEquals(1, manager.getMovies().size());
        assertNotNull(manager.findMovieById("A100"));
        assertEquals("Test Action Movie",
                manager.findMovieById("A100").getTitle());
    }

    @Test
    void shouldRejectDuplicateMovieId() throws DuplicateMovieException {

        MovieManager manager = new MovieManager();

        Movie movie1 = new ComedyMovie(
                "C100",
                "Comedy One",
                "Director One",
                100,
                12.0,
                "15:00",
                50
        );

        Movie movie2 = new ComedyMovie(
                "C100",
                "Comedy Two",
                "Director Two",
                110,
                13.0,
                "17:00",
                50
        );

        manager.addMovie(movie1);

        assertThrows(
                DuplicateMovieException.class,
                () -> manager.addMovie(movie2)
        );

        assertEquals(1, manager.getMovies().size());
    }

    @Test
    void shouldFindMovieById() throws DuplicateMovieException {

        MovieManager manager = new MovieManager();

        Movie movie = new RomanceMovie(
                "R100",
                "Test Romance",
                "Test Director",
                120,
                14.0,
                "19:00",
                "PG",
                50
        );

        manager.addMovie(movie);

        Movie result = manager.findMovieById("R100");

        assertNotNull(result);
        assertEquals("R100", result.getMovieId());
        assertEquals("Test Romance", result.getTitle());
    }

    @Test
    void shouldSearchMoviesByTitle() throws DuplicateMovieException {

        MovieManager manager = new MovieManager();

        Movie movie1 = new ActionMovie(
                "A101",
                "Fast Test",
                "Director One",
                120,
                150,
                "18:00",
                "High",
                50
        );

        Movie movie2 = new ComedyMovie(
                "C101",
                "Funny Test",
                "Director Two",
                100,
                12.0,
                "16:00",
                50
        );

        manager.addMovie(movie1);
        manager.addMovie(movie2);

        List<Movie> results =
                manager.searchByTitle("Fast");

        assertEquals(1, results.size());
        assertEquals("A101", results.get(0).getMovieId());
    }

    @Test
    void shouldSearchMoviesByCategory()
        throws DuplicateMovieException {

        MovieManager manager = new MovieManager();

        manager.addMovie(new ActionMovie(
                "A102",
                "Action One",
                "Director",
                120,
                15.0,
                "18:00",
                "Extreme",
                50
        ));

        manager.addMovie(new ActionMovie(
                "A103",
                "Action Two",
                "Director",
                130,
                16.0,
                "20:00",
                "Extreme",
                50
        ));

        manager.addMovie(new ComedyMovie(
                "C102",
                "Comedy One",
                "Director",
                100,
                12.0,
                "15:00",
                50
        ));

        List<Movie> results =
                manager.searchByCategory("Action");

        assertEquals(2, results.size());

        for (Movie movie : results) {
            assertEquals("Action", movie.getCategory());
        }
    }

    @Test
    void shouldUpdateMovieWithoutChangingMovieId()
        throws DuplicateMovieException, MovieNotFoundException {

        MovieManager manager = new MovieManager();

        Movie original = new ActionMovie(
                "A104",
                "Original Title",
                "Original Director",
                120,
                15.0,
                "18:00",
                "Medium",
                50
        );

        manager.addMovie(original);

        Movie updated = new ActionMovie(
                "DIFFERENT-ID",
                "Updated Title",
                "Updated Director",
                140,
                18.0,
                "20:00",
                "High",
                40
        );

        manager.updateMovie("A104", updated);

        Movie result = manager.findMovieById("A104");

        assertNotNull(result);

        // Movie ID must not change during update
        assertEquals("A104", result.getMovieId());

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Director", result.getDirector());
        assertEquals(140, result.getDuration());
        assertEquals(18.0, result.getPrice());
        assertEquals("20:00",result.getShowTime());
        assertEquals(40, result.getAvailableTickets());
    }

    @Test
    void shouldDeleteMovie()
        throws DuplicateMovieException, MovieNotFoundException {

        MovieManager manager = new MovieManager();

        Movie movie = new ComedyMovie(
                "C103",
                "Delete Me",
                "Director",
                100,
                12.0,
                "15：00",
                50
        );

        manager.addMovie(movie);

        assertNotNull(manager.findMovieById("C103"));

        manager.deleteMovie("C103");

        assertNull(manager.findMovieById("C103"));
        assertEquals(0, manager.getMovies().size());
    }

    @Test
    void shouldThrowExceptionWhenDeleteingUnknownMovie() {

        MovieManager manager = new MovieManager();

        assertThrows(
                MovieNotFoundException.class,
                () -> manager.deleteMovie("UNKNOWN")
        );
    }

    @Test
    void shouldBookTicket() throws DuplicateMovieException,
            MovieNotFoundException {

        MovieManager manager = new MovieManager();

        Movie movie = new ComedyMovie(
                "C104",
                "Booking Test",
                "Director",
                100,
                12.0,
                "15:00",
                50
        );

        manager.addMovie(movie);

        boolean result = manager.bookTicket("C104");

        assertTrue(result);

        assertEquals(49,
                manager.findMovieById("C104").getAvailableTickets());
    }

    @Test
    void shouldFailBookingWhenNoTicketsAreAvailable()
        throws DuplicateMovieException, MovieNotFoundException {

        MovieManager manager = new MovieManager();

        Movie movie = new ComedyMovie(
                "C105",
                "Sold Out Movie",
                "Director",
                100,
                12.0,
                "15:00",
                0
        );

        manager.addMovie(movie);

        boolean result = manager.bookTicket("C105");

        assertFalse(result);
        assertEquals(0,
                manager.findMovieById("C105").getAvailableTickets());
    }

    @Test
    void shouldExportMovies()
        throws DuplicateMovieException, IOException {
        MovieManager manager = new MovieManager();

        Movie movie = new RomanceMovie(
                "R101",
                "Export Test",
                "Director",
                120,
                15.0,
                "19:00",
                "PG",
                49
        );

        manager.addMovie(movie);

        Path tempFile =
                Files.createTempFile("movies-test", ".txt");

        try {
            manager.exportMovies(tempFile.toString());

            List<String> lines =
                    Files.readAllLines(tempFile);

            assertEquals(1, lines.size());

            assertEquals(
                    "Romance, R101, Export Test, Director, 120, 15.0, 19:00, PG, 49",
                    lines.get(0)
            );
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    void testUpdateActionMovieExtra() throws Exception {

        MovieManager manager = new MovieManager();

        ActionMovie original = new ActionMovie(
                "A100",
                "Test Action",
                "Test Director",
                120,
                15.0,
                "18:00",
                "High",
                50
        );

        manager.addMovie(original);

        ActionMovie updated = new ActionMovie(
                "A100",
                "Test Action Updated",
                "Test Director",
                120,
                15.0,
                "18:00",
                "Extreme",
                50
        );

        manager.updateMovie("A100", updated);

        ActionMovie result =
                (ActionMovie) manager.findMovieById("A100");

        assertEquals("Extreme", result.getStuntLevel());
    }

    @Test
    void testUpdateRomanceMovieExtra() throws Exception {

        MovieManager manager = new MovieManager();

        RomanceMovie original = new RomanceMovie(
                "R100",
                "Test Romance",
                "Test Director",
                120,
                15.0,
                "18:00",
                "PG",
                50
        );

        manager.addMovie(original);

        RomanceMovie updated = new RomanceMovie(
                "R100",
                "Test Romance Updated",
                "Test Director",
                120,
                15.0,
                "18:00",
                "R13",
                50
        );

        manager.updateMovie("R100", updated);

        RomanceMovie result =
                (RomanceMovie) manager.findMovieById("R100");

        assertEquals("R13", result.getAgeRestriction());
    }

    @Test
    void testUpdateScienceFictionMovieExtra() throws Exception {

        MovieManager manager = new MovieManager();

        ScienceFictionMovie original =
                new ScienceFictionMovie(
                        "S100",
                        "Test Sci-Fi",
                        "Test Director",
                        120,
                        18.0,
                        "20:00",
                        "IMAX",
                        50
                );

        manager.addMovie(original);

        ScienceFictionMovie updated =
                new ScienceFictionMovie(
                        "S100",
                        "Test Sci-Fi Updated",
                        "Test Director",
                        120,
                        18.0,
                        "20:00",
                        "3D",
                        50
                );

        manager.updateMovie("S100", updated);

        ScienceFictionMovie result =
                (ScienceFictionMovie) manager.findMovieById("S100");

        assertEquals("3D", result.getSpecialFormat());
    }
}

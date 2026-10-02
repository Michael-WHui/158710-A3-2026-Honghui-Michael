package cinema.service;

import cinema.model.ActionMovie;
import cinema.model.ComedyMovie;
import cinema.model.Movie;
import cinema.model.RomanceMovie;
import cinema.model.ScienceFictionMovie;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieFileReaderTest {

    @Test
    void shouldReadAllMovies() throws IOException {

        MovieFileReader reader = new MovieFileReader();

        List<Movie> movies = reader.readMovies("movies.txt");

        assertEquals(32, movies.size());
    }

    @Test
    void shouldReadActionMovieCorrectly() throws IOException {

        MovieFileReader reader = new MovieFileReader();

        List<Movie> movies = reader.readMovies("movies.txt");

        Movie movie = movies.get(0);

        assertTrue(movie instanceof ActionMovie);
        assertEquals("A001", movie.getMovieId());
        assertEquals("Fast & Furious 9", movie.getTitle());
        assertEquals("Justin Lin", movie.getDirector());
        assertEquals(143, movie.getDuration());
        assertEquals(15.5, movie.getPrice());
        assertEquals("18:30", ((ActionMovie) movie).getShowTime());
        assertEquals(50, movie.getAvailableTickets());

        ActionMovie actionMovie = (ActionMovie) movie;

        assertEquals("High", actionMovie.getStuntLevel());
    }

    @Test
    void shouldReadComedy() throws IOException {

        MovieFileReader reader = new MovieFileReader();

        List<Movie> movies = reader.readMovies("movies.txt");

        Movie movie = movies.get(8);

        assertTrue(movie instanceof ComedyMovie);
        assertEquals("C001", movie.getMovieId());
        assertEquals("Mr. Bean's Holiday", movie.getTitle());
        assertEquals("Steve Bendelack", movie.getDirector());
        assertEquals(90, movie.getDuration());
        assertEquals(12.0, movie.getPrice());
        assertEquals("14:00", movie.getShowTime());
        assertEquals(50, movie.getAvailableTickets());

        assertEquals("Comedy", movie.getCategory());
    }

    @Test
    void shouldReadRomanceMovieCorrectly() throws IOException {

        MovieFileReader reader = new MovieFileReader();

        List<Movie> movies = reader.readMovies("movies.txt");

        Movie movie = movies.get(16);

        assertTrue(movie instanceof RomanceMovie);
        assertEquals("R001", movie.getMovieId());
        assertEquals("Titanic", movie.getTitle());
        assertEquals("James Cameron", movie.getDirector());
        assertEquals(195, movie.getDuration());
        assertEquals(18.0, movie.getPrice());
        assertEquals("20:00", movie.getShowTime());
        assertEquals(50, movie.getAvailableTickets());

        RomanceMovie romanceMovie = (RomanceMovie) movie;

        assertEquals("R13", romanceMovie.getAgeRestriction());
    }

    @Test
    void shouldReadScienceFictionMovieCorrectly() throws IOException {

        MovieFileReader reader = new MovieFileReader();

        List<Movie> movies = reader.readMovies("movies.txt");

        Movie movie = movies.get(24);

        assertTrue(movie instanceof ScienceFictionMovie);
        assertEquals("S001", movie.getMovieId());
        assertEquals("Avatar", movie.getTitle());
        assertEquals("James Cameron", movie.getDirector());
        assertEquals(162, movie.getDuration());
        assertEquals(20.0, movie.getPrice());
        assertEquals("21:00", movie.getShowTime());
        assertEquals(50, movie.getAvailableTickets());

        ScienceFictionMovie scienceFictionMovie =
                (ScienceFictionMovie) movie;

        assertEquals("IMAX", scienceFictionMovie.getSpecialFormat());
    }
}

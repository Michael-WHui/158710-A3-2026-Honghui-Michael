package cinema.service;

import cinema.exception.DuplicateMovieException;
import cinema.exception.MovieNotFoundException;
import cinema.model.Movie;
import cinema.model.ActionMovie;
import cinema.model.RomanceMovie;
import cinema.model.ScienceFictionMovie;

import cinema.service.MovieFileReader;
import cinema.service.MovieManager;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MovieManager {

    private final List<Movie> movies;

    public MovieManager() {
        movies = new ArrayList<>();
    }

    public MovieManager(List<Movie> movies) {
        this.movies = new ArrayList<>(movies);
    }

    public List<Movie> getMovies() {
        return new ArrayList<>(movies);
    }

    public void addMovie(Movie movie) throws DuplicateMovieException {

        if (findMovieById(movie.getMovieId()) != null) {
            throw new DuplicateMovieException(
                    "Movie ID already exists: " + movie.getMovieId()
            );
        }

        movies.add(movie);
    }

    public void updateMovie(String movieId, Movie updateMovie)
        throws MovieNotFoundException {

        Movie existingMovie = findMovieById(movieId);

        if (existingMovie == null) {
            throw new MovieNotFoundException(
                    "Movie not found: " + movieId
            );
        }

        // Movie ID cannot be changed
        existingMovie.setTitle(updateMovie.getTitle());
        existingMovie.setDirector(updateMovie.getDirector());
        existingMovie.setDuration(updateMovie.getDuration());
        existingMovie.setPrice(updateMovie.getPrice());
        existingMovie.setShowTime(updateMovie.getShowTime());
        existingMovie.setAvailableTickets(
                updateMovie.getAvailableTickets()
        );

        // Update Action-specific attributes
        if (existingMovie instanceof ActionMovie
            && updateMovie instanceof ActionMovie) {

            ActionMovie existingAction =
                    (ActionMovie) existingMovie;

            ActionMovie updatedAction =
                    (ActionMovie) updateMovie;

            existingAction.setStuntLevel(
                    updatedAction.getStuntLevel()
            );
        }

        // Update Romance-specific attribute
        else if (existingMovie instanceof RomanceMovie
            && updateMovie instanceof RomanceMovie) {

            RomanceMovie existingRomance =
                    (RomanceMovie) existingMovie;

            RomanceMovie updatedRomance =
                    (RomanceMovie) updateMovie;

            existingRomance.setAgeRestriction(
                    updatedRomance.getAgeRestriction()
            );
        }

        // Update Science Fiction-specific attribute
        else if (existingMovie instanceof ScienceFictionMovie
            && updateMovie instanceof ScienceFictionMovie) {

            ScienceFictionMovie existingScienceFiction =
                    (ScienceFictionMovie) existingMovie;

            ScienceFictionMovie updatedScienceFiction =
                    (ScienceFictionMovie) updateMovie;

            existingScienceFiction.setSpecialFormat(
                    updatedScienceFiction.getSpecialFormat()
            );
        }
    }

    public void deleteMovie(String movieId)
        throws MovieNotFoundException {

        Movie movie = findMovieById(movieId);

        if (movie == null) {
            throw new MovieNotFoundException(
                    "Movie not found: " + movieId
            );
        }

        movies.remove(movie);
    }

    public Movie findMovieById(String movieId) {

        for (Movie movie : movies) {
            if (movie.getMovieId().equalsIgnoreCase(movieId)) {
                return movie;
            }
        }

        return null;
    }

    public List<Movie> searchByTitle(String title) {

        if (title == null || title.trim().isEmpty()) {
            return getMovies();
        }
        String searchText = title.trim().toLowerCase();

        return movies.stream()
                .filter(movie ->
                        movie.getTitle()
                                .toLowerCase()
                                .contains(searchText))
                .collect(Collectors.toList());
    }

    public List<Movie> searchByCategory(String category) {

        if (category == null || category.trim().isEmpty()) {
            return getMovies();
        }

        String searchCategory = category.trim();

        return movies.stream()
                .filter(movie ->
                            movie.getCategory()
                                    .equalsIgnoreCase(searchCategory))
                .collect(Collectors.toList());
    }

    public boolean bookTicket(String movieId)
        throws MovieNotFoundException {

        Movie movie = findMovieById(movieId);

        if (movie == null) {
            throw new MovieNotFoundException(
                    "Movie not found: " + movieId
            );
        }

        return movie.bookTicket();
    }

    public void exportMovies(String fileName)
        throws IOException {

        try (PrintWriter writer =
                new PrintWriter(new FileWriter(fileName))) {

            for (Movie movie : movies) {
                writer.println(movie.toFileString());
            }
        }
    }

}

package cinema.service;

import cinema.model.ActionMovie;
import cinema.model.ComedyMovie;
import cinema.model.Movie;
import cinema.model.RomanceMovie;
import cinema.model.ScienceFictionMovie;

import cinema.service.MovieFileReader;
import cinema.service.MovieManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieFileReader {

    public List<Movie> readMovies(String fileName) throws IOException {
        List<Movie> movies = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length != 9) {
                    continue;
                }

                String category = parts[0].trim();
                String movieId = parts[1].trim();
                String title = parts[2].trim();
                String director = parts[3].trim();
                int duration = Integer.parseInt(parts[4].trim());
                double price = Double.parseDouble(parts[5].trim());
                String showTime = parts[6].trim();
                String extraAttribute = parts[7].trim();
                int availableTickets = Integer.parseInt(parts[8].trim());

                Movie movie;

                switch (category) {

                    case "Action":
                        movie = new ActionMovie(
                                movieId,
                                title,
                                director,
                                duration,
                                price,
                                showTime,
                                extraAttribute,
                                availableTickets
                        );
                        break;

                    case "Comedy":
                        movie = new ComedyMovie(
                                movieId,
                                title,
                                director,
                                duration,
                                price,
                                showTime,
                                availableTickets
                        );
                        break;

                    case "Romance":
                        movie = new RomanceMovie(
                                movieId,
                                title,
                                director,
                                duration,
                                price,
                                showTime,
                                extraAttribute,
                                availableTickets
                        );
                        break;

                    case "ScienceFiction":
                    case "Science Fiction":
                        movie = new ScienceFictionMovie(
                                movieId,
                                title,
                                director,
                                duration,
                                price,
                                showTime,
                                extraAttribute,
                                availableTickets
                        );
                        break;

                    default:
                        continue;
                }

                movies.add(movie);
            }
        }

        return movies;
    }
}

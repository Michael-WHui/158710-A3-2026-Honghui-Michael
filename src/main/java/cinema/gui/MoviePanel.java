package cinema.gui;

import cinema.exception.DuplicateMovieException;
import cinema.exception.MovieNotFoundException;
import cinema.model.ActionMovie;
import cinema.model.ComedyMovie;
import cinema.model.Movie;
import cinema.model.RomanceMovie;
import cinema.model.ScienceFictionMovie;
import cinema.service.MovieFileReader;
import cinema.service.MovieManager;
import cinema.staff.Staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class MoviePanel extends JPanel {

    private final Staff loggedInStaff;
    private final MovieManager movieManager;

    // Search components
    private JTextField searchField;
    private JComboBox<String> searchCategoryComboBox;

    // Movie detail components
    private JComboBox<String> movieCategoryComboBox;
    private JTextField movieIdField;
    private JTextField titleField;
    private JTextField directorField;
    private JTextField durationField;
    private JTextField priceField;
    private JTextField showTimeField;
    private JTextField extraField;
    private JTextField ticketsField;

    // Table
    private JTable movieTable;
    private DefaultTableModel tableModel;

    // Buttons
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton bookButton;
    private JButton exportButton;
    private JButton clearButton;
    private JButton searchButton;

    public MoviePanel(Staff staff) {

        this.loggedInStaff = staff;
        this.movieManager = new MovieManager();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        loadMovies();

        JPanel searchPanel = createSearchPanel();
        add(searchPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        createTable();

        centerPanel.add(new JScrollPane(movieTable), BorderLayout.CENTER);

        JPanel detailsPanel = createDetailsPanel();
        centerPanel.add(detailsPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        enableButtonsByRole();

        refreshTable();
    }

    // =========================================================
    // Load movies
    // =========================================================

    private void loadMovies() {

        try {

            MovieFileReader reader = new MovieFileReader();

            List<Movie> movies = reader.readMovies("movies.txt");

            for (Movie movie : movies) {

                try {
                    movieManager.addMovie(movie);
                } catch (DuplicateMovieException e) {
                    System.out.println("Duplicate movie ignored: "
                            + movie.getMovieId());
                }
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not load movies.txt\n" + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // Search panel
    // =========================================================

    private JPanel createSearchPanel() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel searchLabel = new JLabel("Search Title:");

        searchField = new JTextField(20);

        JLabel categoryLabel = new JLabel("Category:");

        searchCategoryComboBox = new JComboBox<>();

        searchCategoryComboBox.addItem("All");
        searchCategoryComboBox.addItem("Action");
        searchCategoryComboBox.addItem("Comedy");
        searchCategoryComboBox.addItem("Romance");
        searchCategoryComboBox.addItem("ScienceFiction");

        searchButton = new JButton("Search");

        clearButton = new JButton("Clear");

        exportButton = new JButton("Export");

        panel.add(searchLabel);
        panel.add(searchField);

        panel.add(categoryLabel);
        panel.add(searchCategoryComboBox);

        panel.add(searchButton);
        panel.add(clearButton);
        panel.add(exportButton);

        searchButton.addActionListener(e -> searchMovies());

        clearButton.addActionListener(e -> clearFields());

        exportButton.addActionListener(e -> exportMovies());

        return panel;
    }

    // =========================================================
    // Movie table
    // =========================================================

    private void createTable() {

        String[] columns = {
                "Category",
                "Movie ID",
                "Title",
                "Director",
                "Duration",
                "Price",
                "Show Time",
                "Extra",
                "Available"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        movieTable = new JTable(tableModel);

        movieTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        movieTable.getSelectionModel().addListSelectionListener(
                e -> {

                    if (!e.getValueIsAdjusting()) {
                        loadSelectedMovie();
                    }
                }
        );
    }

    // =========================================================
    // Details panel
    // =========================================================

    private JPanel createDetailsPanel() {

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;

        // Category

        gbc.gridx = 0;
        gbc.gridy = 0;

        panel.add(new JLabel("Category:"), gbc);

        movieCategoryComboBox = new JComboBox<>();

        movieCategoryComboBox.addItem("Action");
        movieCategoryComboBox.addItem("Comedy");
        movieCategoryComboBox.addItem("Romance");
        movieCategoryComboBox.addItem("ScienceFiction");

        gbc.gridx = 1;

        panel.add(movieCategoryComboBox, gbc);

        // Movie ID

        gbc.gridx = 2;

        panel.add(new JLabel("Movie ID:"), gbc);

        movieIdField = new JTextField(15);

        gbc.gridx = 3;

        panel.add(movieIdField, gbc);

        // Title

        gbc.gridx = 0;
        gbc.gridy = 1;

        panel.add(new JLabel("Title:"), gbc);

        titleField = new JTextField(15);

        gbc.gridx = 1;

        panel.add(titleField, gbc);

        // Director

        gbc.gridx = 2;

        panel.add(new JLabel("Director:"), gbc);

        directorField = new JTextField(15);

        gbc.gridx = 3;

        panel.add(directorField, gbc);

        // Duration

        gbc.gridx = 0;
        gbc.gridy = 2;

        panel.add(new JLabel("Duration:"), gbc);

        durationField = new JTextField();

        gbc.gridx = 1;

        panel.add(durationField, gbc);

        // Price

        gbc.gridx = 2;

        panel.add(new JLabel("Price:"), gbc);

        priceField = new JTextField(15);

        gbc.gridx = 3;

        panel.add(priceField, gbc);

        // Show time

        gbc.gridx = 0;
        gbc.gridy = 3;

        panel.add(new JLabel("Show Time:"), gbc);

        showTimeField = new JTextField(15);

        gbc.gridx = 1;

        panel.add(showTimeField, gbc);

        // Extra

        gbc.gridx = 2;

        panel.add(new JLabel("Extra:"), gbc);

        extraField = new JTextField(15);

        gbc.gridx = 3;

        panel.add(extraField, gbc);

        // Tickets

        gbc.gridx = 0;
        gbc.gridy = 4;

        panel.add(new JLabel("Available Tickets:"), gbc);

        ticketsField = new JTextField(15);

        gbc.gridx = 1;

        panel.add(ticketsField, gbc);

        // Buttons

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT)
        );

        addButton = new JButton("Add");

        updateButton = new JButton("Update");

        deleteButton = new JButton("Delete");

        bookButton = new JButton("Book Ticket");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(bookButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;

        panel.add(buttonPanel, gbc);

        // Button actions

        addButton.addActionListener(e -> addMovie());

        updateButton.addActionListener(e -> updateMovie());

        deleteButton.addActionListener(e -> deleteMovie());

        bookButton.addActionListener(e -> bookTicket());

        return panel;
    }

    // =========================================================
    // Enable buttons according to role
    // =========================================================

    private void enableButtonsByRole() {

        boolean manager = loggedInStaff.canManageMovies();

        addButton.setEnabled(manager);
        updateButton.setEnabled(manager);
        deleteButton.setEnabled(manager);

        bookButton.setEnabled(loggedInStaff.canSellTickets());

        exportButton.setEnabled(true);
    }

    // =========================================================
    // Refresh table
    // =========================================================

    private void refreshTable() {

        tableModel.setRowCount(0);

        for (Movie movie : movieManager.getMovies()) {

            Object[] row = {

                    movie.getCategory(),

                    movie.getMovieId(),

                    movie.getTitle(),

                    movie.getDirector(),

                    movie.getDuration(),

                    String.format("%.2f", movie.getPrice()),

                    movie.getShowTime(),

                    movie.getExtraAttribute(),

                    movie.getAvailableTickets()
            };

            tableModel.addRow(row);
        }
    }

    // =========================================================
    // Search
    // =========================================================

    private void searchMovies() {

        String title = searchField.getText().trim();

        String category =
                (String) searchCategoryComboBox.getSelectedItem();

        List<Movie> results;

        if (!title.isEmpty()) {

            results = movieManager.searchByTitle(title);

        } else if (category != null
                && !category.equals("All")) {

            results = movieManager.searchByCategory(category);

        } else {

            results = movieManager.getMovies();
        }

        tableModel.setRowCount(0);

        for (Movie movie : results) {

            Object[] row = {

                    movie.getCategory(),

                    movie.getMovieId(),

                    movie.getTitle(),

                    movie.getDirector(),

                    movie.getDuration(),

                    String.format("%.2f", movie.getPrice()),

                    movie.getShowTime(),

                    movie.getExtraAttribute(),

                    movie.getAvailableTickets()
            };

            tableModel.addRow(row);
        }
    }

    // =========================================================
    // Load selected movie
    // =========================================================

    private void loadSelectedMovie() {

        int row = movieTable.getSelectedRow();

        if (row < 0) {
            return;
        }

        String category =
                tableModel.getValueAt(row, 0).toString();

        String movieId =
                tableModel.getValueAt(row, 1).toString();

        String title =
                tableModel.getValueAt(row, 2).toString();

        String director =
                tableModel.getValueAt(row, 3).toString();

        String duration =
                tableModel.getValueAt(row, 4).toString();

        String price =
                tableModel.getValueAt(row, 5).toString();

        String showTime =
                tableModel.getValueAt(row, 6).toString();

        String extra =
                tableModel.getValueAt(row, 7).toString();

        String tickets =
                tableModel.getValueAt(row, 8).toString();

        movieCategoryComboBox.setSelectedItem(category);

        movieIdField.setText(movieId);

        titleField.setText(title);

        directorField.setText(director);

        durationField.setText(duration);

        priceField.setText(price);

        showTimeField.setText(showTime);

        extraField.setText(extra);

        ticketsField.setText(tickets);
    }

    // =========================================================
    // Create movie from fields
    // =========================================================

    private Movie createMovieFromFields() {

        String category =
                (String) movieCategoryComboBox.getSelectedItem();

        String movieId =
                movieIdField.getText().trim();

        String title =
                titleField.getText().trim();

        String director =
                directorField.getText().trim();

        int duration =
                Integer.parseInt(durationField.getText().trim());

        double price =
                Double.parseDouble(priceField.getText().trim());

        String showTime =
                showTimeField.getText().trim();

        String extra =
                extraField.getText().trim();

        int tickets =
                Integer.parseInt(ticketsField.getText().trim());

        switch (category) {

            case "Action":

                return new ActionMovie(
                        movieId,
                        title,
                        director,
                        duration,
                        price,
                        showTime,
                        extra,
                        tickets
                );

            case "Comedy":

                return new ComedyMovie(
                        movieId,
                        title,
                        director,
                        duration,
                        price,
                        showTime,
                        tickets
                );

            case "Romance":

                return new RomanceMovie(
                        movieId,
                        title,
                        director,
                        duration,
                        price,
                        showTime,
                        extra,
                        tickets
                );

            case "ScienceFiction":

                return new ScienceFictionMovie(
                        movieId,
                        title,
                        director,
                        duration,
                        price,
                        showTime,
                        extra,
                        tickets
                );

            default:

                throw new IllegalArgumentException(
                        "Invalid movie category."
                );
        }
    }

    // =========================================================
    // Add movie
    // =========================================================

    private void addMovie() {

        try {

            Movie movie = createMovieFromFields();

            movieManager.addMovie(movie);

            refreshTable();

            clearFields();

            JOptionPane.showMessageDialog(
                    this,
                    "Movie added successfully."
            );

        } catch (DuplicateMovieException e) {

            showError(e.getMessage());

        } catch (NumberFormatException e) {

            showError(
                    "Duration, price and available tickets "
                            + "must be valid numbers."
            );

        } catch (IllegalArgumentException e) {

            showError(e.getMessage());
        }
    }

    // =========================================================
    // Update movie
    // =========================================================

    private void updateMovie() {

        String movieId = movieIdField.getText().trim();

        if (movieId.isEmpty()) {

            showError("Please select a movie first.");

            return;
        }

        try {

            Movie updatedMovie = createMovieFromFields();

            movieManager.updateMovie(
                    movieId,
                    updatedMovie
            );

            refreshTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Movie updated successfully."
            );

        } catch (MovieNotFoundException e) {

            showError(e.getMessage());

        } catch (NumberFormatException e) {

            showError(
                    "Duration, price and available tickets "
                            + "must be valid numbers."
            );
        }
    }

    // =========================================================
    // Delete movie
    // =========================================================

    private void deleteMovie() {

        String movieId = movieIdField.getText().trim();

        if (movieId.isEmpty()) {

            showError("Please select a movie first.");

            return;
        }

        int result = JOptionPane.showConfirmDialog(
                this,
                "Delete movie " + movieId + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (result != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            movieManager.deleteMovie(movieId);

            refreshTable();

            clearFields();

            JOptionPane.showMessageDialog(
                    this,
                    "Movie deleted successfully."
            );

        } catch (MovieNotFoundException e) {

            showError(e.getMessage());
        }
    }

    // =========================================================
    // Book ticket
    // =========================================================

    private void bookTicket() {

        String movieId = movieIdField.getText().trim();

        if (movieId.isEmpty()) {

            showError("Please select a movie first.");

            return;
        }

        try {

            boolean success =
                    movieManager.bookTicket(movieId);

            if (success) {

                refreshTable();

                loadSelectedMovie();

                JOptionPane.showMessageDialog(
                        this,
                        "Ticket booked successfully."
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No tickets are available for this movie.",
                        "Sold Out",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (MovieNotFoundException e) {

            showError(e.getMessage());
        }
    }

    // =========================================================
    // Export movies
    // =========================================================

    private void exportMovies() {

        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle(
                "Export Movie Data"
        );

        int result =
                fileChooser.showSaveDialog(this);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try {

            movieManager.exportMovies(
                    fileChooser
                            .getSelectedFile()
                            .getAbsolutePath()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Movies exported successfully."
            );

        } catch (IOException e) {

            showError(
                    "Could not export movies:\n"
                            + e.getMessage()
            );
        }
    }

    // =========================================================
    // Clear fields
    // =========================================================

    private void clearFields() {

        movieIdField.setText("");

        titleField.setText("");

        directorField.setText("");

        durationField.setText("");

        priceField.setText("");

        showTimeField.setText("");

        extraField.setText("");

        ticketsField.setText("");

        movieCategoryComboBox.setSelectedIndex(0);

        movieTable.clearSelection();
    }

    // =========================================================
    // Error dialog
    // =========================================================

    private void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void ClearFields() {

        movieIdField.setText("");
        ticketsField.setText("");
        directorField.setText("");
        durationField.setText("");
        priceField.setText("");
        showTimeField.setText("");
        extraField.setText("");
        ticketsField.setText("");

        movieCategoryComboBox.setSelectedIndex(0);

        movieCategoryComboBox.setEnabled(
                loggedInStaff.canManageMovies()
        );

        movieTable.clearSelection();
    }
}
# 158710 Assignment 3 - Cinema Ticket Management System

## Group Members

- Honghui Li [26028159]
- Hui Wang [26017658]

## GitHub Repository

https://github.com/Honghui832/158710-A3-2026-Honghui-Michael

The repository is private as required by the assignment.

---

## Project Overview

This project implements a Cinema Ticket Management System in Java using Java Swing and Maven.

The system supports two types of staff:
- Ticket Seller
- Manager

Ticket Sellers can:
- Log in to the system
- View movies and showtimes
- Search movies by category or title
- View movie details
- Sell tickets
- Export the current movie data

Managers can perform all Ticket Seller functions and can also:
- Add movies
- Update movies
- Delete movies

The system loads movie information from `movies.txt` when the application starts.

---

## Main Features

### Ticket Sellers
1. Login
2. View movies
3. Search by category
4. Search by title
5. View movie details
6. Book tickets
7. Handle sold-out movies
8. Export movie data

### Manager
Managers have all Ticket Seller functions plus:
1. Add movie
2. Update movie
3. Delete movie

| Username | Password | Role |
| --- |----------| --- |
| s1 | s1       | Ticket Seller |
| s2 | s2 | Ticket Seller |
| s3 | s3 | Ticket Seller |

### Managers
| Username | Password | Role          |
|----------|----------|---------------|
| m1       | m1       | Manager       |
| m2       | m2       | Manager |

---

## Task Division

### Honghui
- Project setup and Maven configuration
- Movie model and inheritance hierarchy
- Movie file reading and data management
- Login functionality
- Movie search and ticket booking
- JUnit testing
- GitHub Actions and CI configuration
- Integration testing and debugging

### Michael
- Swing GUI development
- Movie management functions
- Add, update and delete movie functionality
- GUI search and movie display
- Export functionality
- User interface testing
- Documentation and final testing

### Shared Work
- Requirements analysis
- Git and GitHub version control
- GitHub Issues
- Pull Requests and code integration
- Final system testing
- Instruction Manual
- Code metrics
- Final submission preparation

---

## Requirements
- Java 25
- Maven
- JUnit 5
- Java Swing

## How to Run
1. Open the Project
Open the project in IntelliJ IDEA.

2. Make sure Java 25 is Configured
The project uses Java 25 as specified in pom.xml.

3. Make sure movies.txt Is in the Project Root
The file should be located at:
Assignment3_158710/movies.txt

4. Build and Test the Project
Use the Maven tool window in IntelliJ IDEA:
test
Maven
→ Lifecycle
→ clean
→ test
All JUnit tests should pass.

5. Run the Application
Run:
src/main/java/cinema/Main.java
The login window will appear.

## Movie Categories
The system supports:
1. Action
2. Comedy
3. Romance
4. Science Fiction
Different movie categories support their required extra attributes.

## Data File
Movie information is initially loaded from:
movies.txt
Each movie record contains:
Category, MovieID, Title, Director, Duration, Price, ShowTime, ExtraAttribute, AvailableTickets
The current movie data can also be exported through the GUI using a file chooser.

## Testing
JUnit 5 tests are provided for the main functionality of the system, including:

Movie model behaviour
Movie file reading
Movie management
Login functionality
Movie update functionality
Ticket booking

The project also uses GitHub Actions to automatically run Maven tests.

## GitHub Actions
GitHub Actions is configured to run the Maven test suite automatically when changes are pushed to the repository or when a pull request is created.
Workflow file:
.github/workflows/maven.yml

## Documentation
The project includes:
1. `README.md` - project information and running instructions
2. `Instruction-Manual.pdf` - user instruction manual with GUI screenshots
3. `reports/metrics/` - source code metrics

## Version Control
Git and GitHub are used for version control.
The project uses:
1. Git branches
2. Commits
3. Pull Requests
4. GitHub Issues
5. GitHub Actions

## Project Structure

```text
Assignment3_158710/
├── .github/
│   └── workflows/
│       └── maven.yml
├── reports/
│   └── metrics/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── cinema/
│   │           ├── model/
│   │           ├── staff/
│   │           ├── service/
│   │           ├── exception/
│   │           └── gui/
│   └── test/
│       └── java/
│           └── cinema/
├── movies.txt
├── pom.xml
├── README.md
└── Instruction-Manual.pdf

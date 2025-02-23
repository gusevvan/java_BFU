package lab3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    private static Scanner scanner;
    private static CinemaManager manager;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        manager = new CinemaManager();
        fillTestData(manager);
        String userType = authorizeInApp();
        System.out.println("Authorize successful under " + userType);
        showStartMenu(userType);

        // for (int i = 0; i < manager.getNumCinemas(); ++i) {
        //     System.out.println("Cinema:");
        //     System.out.println(manager.getCinema(i).getName());
        //     for (int j = 0; j < manager.getCinema(i).getNumHalls(); ++j) {
        //         System.out.println("Hall:");
        //         System.out.println(manager.getCinema(i).getHall(j).getName());
        //         for (int k = 0; k < manager.getCinema(i).getHall(j).getNumSeances(); ++k) {
        //             System.out.println("Seance:");
        //             System.out.println("Film:" + manager.getCinema(i).getHall(j).getSeance(k).getFilm());
        //             System.out.println("Start Date Stamp:" + manager.getCinema(i).getHall(j).getSeance(k).getStartDateStamp());
        //             System.out.println("Duration:" + manager.getCinema(i).getHall(j).getSeance(k).getDuration());
        //             System.out.println("Plan:");
        //             printStringArray(manager.getSeancePlan(i, j, k));
        //         }
        //     }
        // }

        // ArrayList<Integer> findedSeance = manager.findClosestSeance("test_film_1");
        // printSeancePos(findedSeance);
    }

    public static void showStartMenu(String userType) {
        System.out.println("Select Action by sending action number in braces:");
        System.out.println("(1) Show cinemas");
        System.out.println("(2) Find closest free seance for film");
        String action = scanner.next();
        if (action.equals("1")) {
            showCinemas(userType);
            return;
        } else if (action.equals("2")) {
            System.out.println("Input film:");
            String film = scanner.next();
            ArrayList<Integer> closestSeancePlace = manager.findClosestSeance(film);
            if (closestSeancePlace.get(0) == -1) {
                System.out.println("Seance not found");
            } else {
                System.out.println("Closest seance for " + film + " in:");
                System.out.println(manager.getCinema(closestSeancePlace.get(0)).getName() + " cinema " +
                             manager.getCinema(closestSeancePlace.get(0)).getHall(closestSeancePlace.get(1)).getName() + " hall");
            }
        } else {
            System.out.println("Incorrect action, try again.");
        }
        showStartMenu(userType);
    }

    public static void showCinemas(String userType) {
        System.out.println("Select action or cinema:");
        System.out.println("(-1) Go back");
        if (userType.equals("admin")) {
            System.out.println("(0) Add cinema");
        }
        for (int i = 0; i < manager.getNumCinemas(); ++i) {
            System.out.println("(" + (i + 1) + ") " + manager.getCinema(i).getName());
        }
        int action = 0;
        try {
            action = Integer.parseInt(scanner.next());
        }
         catch (NumberFormatException e) {
            System.out.println("Incorrect action, try again");
            showCinemas(userType);
            return;
        }
        if (action == -1) {
            showStartMenu(userType);
            return;
        } else if (action == 0 && userType.equals("admin")) {
            System.out.println("Input cinema name:");
            String cinemaName = scanner.next();
            manager.addCinema(cinemaName);
            showCinemas(userType);
        } else if (action >= 1 && action <= manager.getNumCinemas()) {
            showHalls(action - 1, userType);
        } else {
            System.out.println("Incorrect action, try again");
            showCinemas(userType);
        }
    }

    public static void showHalls(int cinemaId, String userType) {
        System.out.println("Select action or hall:");
        System.out.println("(-1) Go back");
        if (userType.equals("admin")) {
            System.out.println("(0) Add hall");
        }
        for (int i = 0; i < manager.getCinema(cinemaId).getNumHalls(); ++i) {
            System.out.println("(" + (i + 1) + ") " + manager.getCinema(cinemaId).getHall(i).getName());
        }
        int action = 0;
        try {
            action = Integer.parseInt(scanner.next());
        }
         catch (NumberFormatException e) {
            System.out.println("Incorrect action, try again");
            showHalls(cinemaId, userType);
            return;
        }
        if (action == -1) {
            showCinemas(userType);
            return;
        } else if (action == 0 && userType.equals("admin")) {
            System.out.println("Input hall name:");
            String hallName = scanner.next();
            System.out.println("Input rows number in hall:");
            int rows;
            try {
                rows = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Incorrect input, try again");
                showHalls(cinemaId, userType);
                return;
            }
            manager.getCinema(cinemaId).addHall(hallName);
            scanner.nextLine();
            System.out.println("Input " + rows + " strings each for place configuration in row");
            System.out.println("Put whitespace where no place and * where place.");
            ArrayList<String> config = new ArrayList<>();
            for (int i = 0; i < rows; ++i) {
                String line = scanner.nextLine();
                config.add(line);
            }
            manager.getCinema(cinemaId).getHall(manager.getCinema(cinemaId).getNumHalls() - 1).setHallConfig(config);;
            showHalls(cinemaId, userType);
        } else if (action >= 1 && action <= manager.getCinema(cinemaId).getNumHalls()) {
            showSeances(cinemaId, action - 1, userType);
        } else {
            System.out.println("Incorrect action, try again");
            showHalls(cinemaId, userType);
        }
    }

    public static void showSeances(int cinemaId, int hallId, String userType) {
        System.out.println("Select action or seance:");
        System.out.println("(-1) Go back");
        if (userType.equals("admin")) {
            System.out.println("(0) Add seance");
        }
        for (int i = 0; i < manager.getCinema(cinemaId).getHall(hallId).getNumSeances(); ++i) {
            Date currentDate = new java.util.Date(manager.getCinema(cinemaId).getHall(hallId).getSeance(i).getStartDateStamp());
            System.out.println("(" + (i + 1) + ") " + manager.getCinema(cinemaId).getHall(hallId).getSeance(i).getFilm() + " " +
                currentDate.toString() + " (" + manager.getCinema(cinemaId).getHall(hallId).getSeance(i).getDuration() + " mins)");
        }
        int action = 0;
        try {
            action = Integer.parseInt(scanner.next());
        }
         catch (NumberFormatException e) {
            System.out.println("Incorrect action, try again");
            showSeances(cinemaId, hallId, userType);
            return;
        }
        if (action == -1) {
            showHalls(cinemaId, userType);;
            return;
        } else if (action == 0 && userType.equals("admin")) {
            System.out.println("Input seance film:");
            String film = scanner.next();
            System.out.println("Input seance start datetime in format (yyyy-MM-dd HH:mm):");
            long dateTimeStamp;
            try {
                scanner.nextLine();
                String inputDate = scanner.nextLine();
                Date tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(inputDate);
                dateTimeStamp = tempDate.getTime();
            } catch (Exception e) {
                System.out.println("Incorrect action, try again");
                showSeances(cinemaId, hallId, userType);
                return;
            }
            System.out.println("Input seance duration in minutes:");
            int duration;
            try {
                duration = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Incorrect action, try again");
                showSeances(cinemaId, hallId, userType);
                return;
            }
            manager.getCinema(cinemaId).getHall(hallId).addSeance(film, dateTimeStamp, duration);
            showSeances(cinemaId, hallId, userType);
        } else if (action >= 1 && action <= manager.getCinema(cinemaId).getHall(hallId).getNumSeances()) {
            showSeanceBuyTicket(cinemaId, hallId, action - 1, userType);
        } else {
            System.out.println("Incorrect action, try again");
            showSeances(cinemaId, hallId, userType);
        }
    }

    public static void showSeanceBuyTicket(int cinemaId, int hallId, int seanceId, String userType) {
        System.out.println("Seance plan:");
        printStringArray(manager.getSeancePlan(cinemaId, hallId, seanceId));
        System.out.println("* - free place");
        System.out.println("X - busy place");
        System.out.println("Select action:");
        System.out.println("(-1) Go back");
        if (userType.equals("client")) {
            System.out.println("(0) Buy ticket");
        }
        String action = scanner.next();
        if (action.equals("-1"))  {
            showSeances(cinemaId, hallId, userType);
            return;
        } else if (action.equals("0") && userType.equals("client")) {
            System.out.println("Input free place (numbering starts from the upper left corner):");
            try {
                int place = scanner.nextInt();
                manager.sellTicket(cinemaId, hallId, seanceId, place);
            } catch (Exception e) {
                System.out.println("Incorrect action, try again");
            }
        } else {
            System.out.println("Incorrect action, try again");
        }
        showSeanceBuyTicket(cinemaId, hallId, seanceId, userType);
    }

    public static String authorizeInApp() {
        System.out.println("Select role for Enter (admin or client):");
        String userType = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();
        if (userType.equals("client")) {
            if (password.equals("client")) {
                return "client";
            }
        } else if (userType.equals("admin")) {
            if (password.equals("admin")) {
                return "admin";
            }
        }
        System.out.println("Error, try again.");
        return authorizeInApp();
    }

    public static void fillTestData(CinemaManager manager) {
        manager.addCinema("test_cinema_1");
        manager.addCinema("test_cinema_2");
        manager.addHall(0, "test_hall_1");
        manager.addHall(1, "test_hall_2");
        ArrayList<String> config = new ArrayList<>(Arrays.asList("***", " * ", "***"));
        manager.setHallConfig(0, 0, config);
        ArrayList<String> config2 = new ArrayList<>(Arrays.asList("*****", " * * ", "** **"));
        manager.setHallConfig(1, 0, config2);
        manager.addSeance(0, 0, "test_film_1", 1741000800000l, 100);
        manager.addSeance(0, 0, "test_film_2", 1741000000000l, 120);
        manager.addSeance(1, 0, "test_film_1", 1741000800000l, 100);
        try {
            manager.sellTicket(0, 0, 0, 4);
            manager.sellTicket(0, 0, 0, 5);
            manager.sellTicket(0, 0, 0, 6);
            manager.sellTicket(1, 0, 0, 2);
        }
        catch (Exception e) {
            
        }
    }

    public static void printStringArray(ArrayList<String> stringArray) {
        for (String string: stringArray) {
            System.out.println(string);
        }
    }

    public static void printSeancePos(ArrayList<Integer> seancePos) {
        System.out.println("Cinema: " + seancePos.get(0) + " Hall: " + seancePos.get(1) + " Seance: " + seancePos.get(2));
    }
}

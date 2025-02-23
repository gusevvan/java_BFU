package lab3;
import java.util.ArrayList;

public class Cinema {
    private ArrayList<Hall> hallList;
    private String cinemaName;

    public Cinema(String cinemaName) {
        this.cinemaName = cinemaName;
        hallList = new ArrayList<>();
    }

    public void addHall(String hallName) {
        hallList.add(new Hall(hallName));
    }

    public Hall getHall(int hallId) {
        return hallList.get(hallId);
    } 

    public int getNumHalls() {
        return hallList.size();
    }

    public String getName() {
        return cinemaName;
    }
}
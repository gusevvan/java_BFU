package lab3;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Hall {
    private String hallName;
    private ArrayList<Seance> seancesList;
    private ArrayList<String> config;
    private int numPlaces;

    public Hall(String hallName) {
        this.hallName = hallName;
        config = new ArrayList<>();
        seancesList = new ArrayList<>();
        numPlaces = 0;
    }

    public void addSeance(String film, long startDateStamp, int duration) {
        seancesList.add(new Seance(film, startDateStamp, duration, numPlaces));
    }

    public Seance getSeance(int seanceId) {
        return seancesList.get(seanceId);
    }

    public void setHallConfig(ArrayList<String> config) {
        this.config = config;
        for (String row: config) {
            for (int i = 0; i < row.length(); ++i) {
                if (row.charAt(i) == '*') {
                    numPlaces++;
                }
            }
        }
    }

    public ArrayList<String> getSeancePlan(int seanceId) {
        Seance seance = seancesList.get(seanceId);
        ArrayList<String> result = new ArrayList<String>();
        Set<Integer> selectedPlaces = seance.getSelectedPlaces();
        int currentPlace = 0;
        for (String row: config) {
            String resultRow = "";
            for (int i = 0; i < row.length(); ++i) {
                if (row.charAt(i) == '*') {
                    currentPlace++;
                    if (selectedPlaces.contains(currentPlace)) {
                        resultRow += 'X';
                    } else {
                        resultRow += '*';
                    }
                } else {
                    resultRow += ' ';
                }
            }
            result.add(resultRow);
        }
        return result;
    }  

    public int getNumSeances() {
        return seancesList.size();
    }

    public String getName() {
        return hallName;
    }
}

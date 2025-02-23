package lab3;
import java.util.HashSet;
import java.util.Set;

public class Seance {
    private String film;
    private long startDateStamp;
    private int duration;
    private int numPlaces;
    private Set<Integer> selectedPlaces;

    public Seance(String film, long startDateStamp, int duration, int numPlaces) { 
        this.film = film; 
        this.startDateStamp = startDateStamp;
        this.duration = duration;
        this.numPlaces = numPlaces; 
        selectedPlaces = new HashSet<>();
    } 

    public void addPlaceToSelected(int place) throws Exception {
        if (place <= 0 || place >= numPlaces || selectedPlaces.contains(place)) {
            throw new Exception("incorrect place");
        }
        selectedPlaces.add(place);
    }

    public Set<Integer> getSelectedPlaces() {
        return selectedPlaces;
    }

    public String getFilm() {
        return film;
    }

    public long getStartDateStamp() {
        return startDateStamp;
    }

    public boolean hasFreePlaces() {
        for (int i = 1; i <= numPlaces; ++i) {
            if (!selectedPlaces.contains(i)) {
                return true;
            }
        }
        return false;
    }

    public int getDuration() {
        return duration;
    }
}   

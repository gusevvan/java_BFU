package lab3;

import java.util.ArrayList;

public class CinemaManager {
    private ArrayList<Cinema> cinemaList;

    public CinemaManager() {
        cinemaList = new ArrayList<>();
    }

    public void addCinema(String cinemaName) {
        cinemaList.add(new Cinema(cinemaName));
    }

    public Cinema getCinema(int cinemaId) {
        return cinemaList.get(cinemaId);
    }

    public int getNumCinemas() {
        return cinemaList.size();
    }

    public void addHall(int cinemaId, String hallName) {
        cinemaList.get(cinemaId).addHall(hallName);
    }

    public void addSeance(int cinemaId, int hallId, String film, long startDateStamp, int duration) {
        cinemaList.get(cinemaId).getHall(hallId).addSeance(film, startDateStamp, duration);
    }

    public void sellTicket(int cinemaId, int hallId, int seanceId, int place) throws Exception {
        cinemaList.get(cinemaId).getHall(hallId).getSeance(seanceId).addPlaceToSelected(place);
    }

    public ArrayList<String> getSeancePlan(int cinemaId, int hallId, int seanceId) {
        return cinemaList.get(cinemaId).getHall(hallId).getSeancePlan(seanceId);
    }

    public void setHallConfig(int cinemaId, int hallId, ArrayList<String> config) {
        cinemaList.get(cinemaId).getHall(hallId).setHallConfig(config);
    }

    public ArrayList<Integer> findClosestSeance(String film) {
        ArrayList<Integer> result = new ArrayList<>();
        result.add(-1);
        result.add(-1);
        result.add(-1);
        long minDateStamp = Long.MAX_VALUE;
        for (int i = 0; i < cinemaList.size(); ++i) {
            for (int j = 0; j < cinemaList.get(i).getNumHalls(); ++j) {
                for (int k = 0; k < cinemaList.get(i).getHall(j).getNumSeances(); ++k) {
                    Seance currentSeance = cinemaList.get(i).getHall(j).getSeance(k);
                    if (currentSeance.getFilm().equals(film) && currentSeance.hasFreePlaces() &&
                                     currentSeance.getStartDateStamp() < minDateStamp) {
                        minDateStamp = currentSeance.getStartDateStamp();
                        result.set(0, i);
                        result.set(1, j);
                        result.set(2, k);
                    }
                }
            }
        }
        return result;
    }
}

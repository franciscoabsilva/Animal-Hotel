package hva.season;

import java.io.Serial;
import java.io.Serializable;

import hva.habitat.Deciduous;
import hva.habitat.Evergreen;

public class Autumn extends Season {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    @Override
    public int getSeasonalEffort(Deciduous tree) {
        return 5;
    }

    @Override 
    public int getSeasonalEffort(Evergreen tree) {
        return 1;
    }

    @Override
    public String getBiologicalCycle(Deciduous tree) {
        return "LARGARFOLHAS";
    }

    @Override 
    public String getBiologicalCycle(Evergreen tree) {
        return "COMFOLHAS";
    }

    @Override
    public Season nextSeason() {
        return new Winter();
    }

    @Override
    public String toString(){
        return "2";
    }
}

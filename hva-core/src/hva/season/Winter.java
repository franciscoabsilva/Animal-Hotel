package hva.season;

import java.io.Serial;
import java.io.Serializable;

import hva.habitat.Deciduous;
import hva.habitat.Evergreen;

public class Winter extends Season {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    @Override
    public int getSeasonalEffort(Deciduous tree) {
        return 0;
    }

    @Override 
    public int getSeasonalEffort(Evergreen tree) {
        return 2;
    }

    @Override
    public String getBiologicalCycle(Deciduous tree) {
        return "SEMFOLHAS";
    }

    @Override 
    public String getBiologicalCycle(Evergreen tree) {
        return "LARGARFOLHAS";
    }

    @Override
    public Season nextSeason() {
        return new Spring(); 
    }

    @Override
    public String toString(){
        return "3";
    }
}

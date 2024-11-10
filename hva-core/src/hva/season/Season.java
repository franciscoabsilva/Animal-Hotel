package hva.season;

import java.io.Serial;
import java.io.Serializable;

import hva.habitat.Deciduous;
import hva.habitat.Evergreen;

public abstract class Season implements Serializable {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    public abstract int getSeasonalEffort(Deciduous tree);
    public abstract int getSeasonalEffort(Evergreen tree);

    public abstract String getBiologicalCycle(Deciduous tree);
    public abstract String getBiologicalCycle(Evergreen tree);

    public abstract Season nextSeason();

    public abstract String toString();
}


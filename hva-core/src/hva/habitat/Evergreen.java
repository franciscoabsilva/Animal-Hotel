package hva.habitat;

import java.io.Serial;
import java.io.Serializable;

import hva.season.Season;

public class Evergreen extends Tree {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    public Evergreen(String treeKey, String treeName, int treeAge, int treeDifficulty) {
        super(treeKey, treeName, treeAge, treeDifficulty);
    }

    @Override
    public Season getSeason() {
        return super.getSeason();
    }
    
    @Override
    public int getTreeDifficulty(){
        return super.getTreeDifficulty();
    }

    @Override
    public int getTreeAge(){
        return super.getTreeAge();
    }

    public double cleaningEffort(){
        return getTreeDifficulty() * getSeason().getSeasonalEffort(this) * Math.log(getTreeAge() + 1);
    }

    @Override
    public String toString(){
        return super.toString() + "|PERENE|" + getSeason().getBiologicalCycle(this);
    }
}

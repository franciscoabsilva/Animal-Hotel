package hva.habitat;

import java.io.Serial;
import java.io.Serializable;

import hva.season.Season;

/**
 * Represents a tree within the habitat system, containing information about its characteristics and habitat.
 */
public abstract class Tree implements Serializable {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The unique key identifier for the tree. */
    private final String _treeKey;

    /** The name of the tree. */
    private String _treeName;

    /** The age of the tree in years. */
    private int _treeAge;

    private double _ageProgress = 0.0;

    /** The difficulty level for maintaining the tree. */
    private int _treeDifficulty;

    /** The habitat in which the tree is located. */
    private Habitat _habitat;

    private Season _currentSeason;

    /**
     * Constructs a new Tree with the specified key, name, age, difficulty, and type.
     * 
     * @param treeKey The unique key identifier for the tree.
     * @param treeName The name of the tree.
     * @param treeAge The age of the tree in years.
     * @param treeDifficulty The difficulty level for maintaining the tree.
     */
    public Tree(String treeKey, String treeName, int treeAge, int treeDifficulty) {
        _treeKey = treeKey;
        _treeName = treeName;
        _treeAge = treeAge;
        _treeDifficulty = treeDifficulty;
    }

    /**
     * Returns the unique key identifier for the tree.
     * 
     * @return The tree key.
     */
    public String getKey(){
        return _treeKey;
    }

    // PARA O CRIADOR
    public void setCurrentSeason(Season currentSeason) {
        _currentSeason = currentSeason;
    }

    // PARA AVANÇAR A ESTAÇÃO
    public void nextSeason() {
        _currentSeason = _currentSeason.nextSeason();
        advanceAgeProgress();
    }

    public void advanceAgeProgress() {
        _ageProgress += 0.25;
        if (_ageProgress >= 1.0) {
            _treeAge++; 
            _ageProgress = 0.0; 
        }
    }
    
    public Season getSeason() {
        return _currentSeason;
    }

    public int getTreeDifficulty(){
        return _treeDifficulty;
    }

    public int getTreeAge(){
        return _treeAge;
    }

    public abstract double cleaningEffort();    

    @Override
    // Format:   ÁRVORE|idÁrvore|nomeÁrvore|idadeÁrvore|dificuldadeBaseLimpeza
    public String toString(){
        return "ÁRVORE|" + _treeKey + "|" + _treeName + "|" + _treeAge + "|" + _treeDifficulty;
    }
}

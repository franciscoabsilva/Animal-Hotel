package hva.habitat;

import java.io.Serial;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import hva.animal.Animal;
import hva.animal.Specie;
import hva.employee.Handler;
import hva.vaccine.Vaccine;

/**
 * Represents a habitat within the hotel system, containing information
 * about its characteristics and the animals residing within it.
 */
public class Habitat implements Serializable {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;  

    /** The unique key identifier for the habitat. */
    private final String _habitatKey;

    /** The name of the habitat. */
    private String _habitatName;

    /** The area of the habitat in square units. */
    private int _habitatArea;

    /** A map of animals residing in the habitat, case insensitive. */
    private Map<String, Animal> _animals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private Map<String, Handler> _handlers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** A map of trees in the habitat, case insensitive. */
    private Map<String, Tree> _trees = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** A list of species with positive influence in this habitat. */
    private List<Specie> _positiveInfluenceSpecies = new ArrayList<>();

    /** A list of species with negative influence in this habitat. */
    private List<Specie> _negativeInfluenceSpecies = new ArrayList<>();


    /**
     * Constructs a new Habitat with the specified key, name, and area.
     * 
     * @param habitatKey The unique key identifier for the habitat.
     * @param habitatName The name of the habitat.
     * @param habitatArea The area of the habitat in square units.
     */
    public Habitat(String habitatKey, String habitatName, int habitatArea){
        _habitatKey = habitatKey;
        _habitatName = habitatName;
        _habitatArea = habitatArea;     
    }

    /**
     * Constructs a new Habitat with the specified key, name, area, and trees.
     * 
     * @param habitatKey The unique key identifier for the habitat.
     * @param habitatName The name of the habitat.
     * @param habitatArea The area of the habitat in square units.
     * @param trees An array of trees in the habitat.
     */
    public Habitat(String habitatKey, String habitatName, int habitatArea, Tree[] trees){
        _habitatKey = habitatKey;
        _habitatName = habitatName;
        _habitatArea = habitatArea;
        for (Tree tree : trees){
            _trees.put(tree.getKey(), tree);
        }
    }

    /**
     * Adds an animal to the habitat.
     * 
     * @param animal The animal to be added to the habitat.
     */
    public void addAnimal(Animal animal){
        _animals.put(animal.getKey(), animal);
    }

    public void removeAnimal(Animal animal){
        _animals.remove(animal.getKey());
    }

    public void setArea(int area){
        _habitatArea = area;
    }

    public void addTree(Tree tree){
        _trees.put(tree.getKey(), tree);
    }

    public String showAllTrees(){
        return _trees.values().stream()
                .map(Tree::toString)
                .collect(Collectors.joining("\n"));
    }

    public String showAllAnimals(){
        return _animals.values().stream()
                .map(Animal::toString)
                .collect(Collectors.joining("\n"));
    }

    public void addHandler(Handler handler){
        _handlers.put(handler.getKey(), handler);
    }

    public void removeHandler(Handler handler){
        _handlers.remove(handler.getKey());
    }

    public int countHandlers(){
        return _handlers.size();
    }

    public void addPositiveInfluenceSpecie(Specie specie){
        _positiveInfluenceSpecies.add(specie);
    }

    public void addNegativeInfluenceSpecie(Specie specie){
        _negativeInfluenceSpecies.add(specie);
    }

    public void removeInfluenceSpecie(Specie specie){
        if (_positiveInfluenceSpecies.contains(specie)){
            _positiveInfluenceSpecies.remove(specie);
        }
        else if (_negativeInfluenceSpecies.contains(specie)){
            _negativeInfluenceSpecies.remove(specie);
        }
    }

    public int sameSpecieAnimals(Animal animal){
        int count = -1;
        Specie specie = animal.getSpecie();
        for (Animal a : _animals.values()){
            if (a.getSpecie().getKey() == specie.getKey()){
                count++;
            }
        }
        return count;
    }

    public int differentSpecieAnimals(Animal animal){
        int count = 0;
        Specie specie = animal.getSpecie();
        for (Animal a : _animals.values()){
            if (!(a.getSpecie().getKey() == specie.getKey())){
                count++;
            }
        }
        return count;
    }

    public int getHabitatInfluence(Animal animal){
        Specie specie = animal.getSpecie();
        
        if (_positiveInfluenceSpecies.contains(specie)){
            return 20;
        }
        else if (_negativeInfluenceSpecies.contains(specie)){
            return -20;
        }
        else {
            return 0;
        }
    }

    public double workInHabitat(){
        double work = getArea() + 3 * getPopulation();
        for (Tree tree : _trees.values()){
            work += tree.cleaningEffort();
        }
        return work;        
    }
    

    /**
     * Retrieves the unique key identifier for the habitat.
     * 
     * @return The unique key identifier for the habitat.
     */
    public String getKey(){
        return _habitatKey;
    }

    public int getArea(){
        return _habitatArea;
    }

    public int getPopulation(){
        return _animals.size();
    }

    @Override
    // Format:   HABITAT|idHabitat|nome|área|númeroÁrvores
    public String toString(){
        String string = "HABITAT|" + _habitatKey + "|" + _habitatName + "|" + _habitatArea +
        "|" + _trees.size();
        for (Tree tree : _trees.values()){
            string += "\n" + tree.toString();
        }
        return string;
    }
}

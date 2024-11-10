package hva.animal;

import java.io.Serial;
import java.io.Serializable;

import java.util.Map;
import java.util.TreeMap;

import hva.employee.Veterinarian;

public class Specie implements Serializable {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The unique key identifier for the species. */
    private final String _specieKey;

    /** The name of the species. */
    private String _specieName;

    /** A map of animals associated with the species, case insensitive. */
    private Map<String, Animal> _animals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private Map<String, Veterinarian> _veterinarians = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * Constructs a new Specie with the specified key and name.
     * 
     * @param specieKey The unique key identifier for the species.
     * @param specieName The name of the species.
     */
    public Specie(String specieKey, String specieName){
        _specieKey = specieKey;
        _specieName = specieName;
    }

    /**
     * Adds an animal to the species.
     * 
     * @param animal The animal to be added to this species.
     */
    public void addAnimal(Animal animal){
        _animals.put(animal.getKey(), animal);
    }

    public void addVeterinarian(Veterinarian veterinarian){
        _veterinarians.put(veterinarian.getKey(), veterinarian);
    }

    public void removeVeterinarian(Veterinarian veterinarian){
        _veterinarians.remove(veterinarian.getKey());
    }

    public int countVeterinarians(){
        return _veterinarians.size();
    }

    public int getPopulation(){
        return _animals.size();
    }

    /**
     * Returns the unique key identifier for the species.
     * 
     * @return The species key.
     */
    public String getKey(){
        return _specieKey;
    }

    public String getName(){
        return _specieName;
    }
}

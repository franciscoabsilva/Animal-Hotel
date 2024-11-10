package hva.animal;

import java.io.Serial;
import java.io.Serializable;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import hva.habitat.Habitat;
import hva.satisfaction.SatisfactionCalculator;
import hva.satisfaction.AnimalSatisfactionCalculator;
import hva.vaccine.Vaccination;

/**
 * Represents an animal in the hotel, associated with a specific species and habitat.
 */
public class Animal implements Serializable {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The unique key identifier for the animal. */
    private final String _animalKey;

    /** The name of the animal. */
    private String _animalName;

    /** The species the animal belongs to. */
    private Specie _specie;

    /** The habitat where the animal resides. */
    private Habitat _habitat;

    /** The health history of the animal. */
    private String _animalHealth = "";

    private final SatisfactionCalculator _satisfactionCalculator = new AnimalSatisfactionCalculator();

    private List<Vaccination> _vaccinationHistory = new ArrayList<>();

    /**
     * Constructs a new Animal with the specified key, name, species, and habitat.
     *
     * @param animalKey The unique key for the animal.
     * @param animalName The name of the animal.
     * @param specie The species to which the animal belongs.
     * @param habitat The habitat where the animal resides.
     */
    public Animal(String animalKey, String animalName, Specie specie, Habitat habitat) {
        _animalKey = animalKey;
        _animalName = animalName;
        _specie = specie;
        _habitat = habitat;
    }

    /**
     * Retrieves the unique key of the animal.
     *
     * @return The unique key identifier for the animal.
     */
    public String getKey() {
        return _animalKey;
    }

    public Specie getSpecie() {
        return _specie;
    }

    public Habitat getHabitat() {
        return _habitat;
    }

    public void setHabitat(Habitat habitat){
        _habitat = habitat;
    }

    public void addCorrectVaccination(Vaccination vaccination){
        _vaccinationHistory.add(vaccination);
        _animalHealth += "NORMAL";
    }

    public void addIncorrectVaccination(Vaccination vaccination, int damage){
        _vaccinationHistory.add(vaccination);
        
        _animalHealth += switch (damage) {
            case 0 -> "CONFUSÃO";
            case 1, 2, 3, 4 -> "ACIDENTE";
            default -> "ERRO";
        };
    }

    public String showVaccinationHistory() {
        return _vaccinationHistory.stream()
                .map(Vaccination::toString)
                .collect(Collectors.joining("\n"));
    }

    public double calculateSatisfaction() {
        return _satisfactionCalculator.calculateSatisfaction(this);
    }
    
    /**
     * Returns a string representation of the animal in a specific format.
     *
     * The format is: ANIMAL|idAnimal|nomeAnimal|idEspécie|historialDeSaúde|idHabitat
     *
     *
     * @return A string representation of the animal.
     */
    @Override
    public String toString() {
        if (_animalHealth.equals(""))
            return "ANIMAL|" + _animalKey + "|" + _animalName + "|" + _specie.getKey() +
            "|VOID|" + _habitat.getKey();
        else
            return "ANIMAL|" + _animalKey + "|" + _animalName + "|" + _specie.getKey() +
            "|" + _animalHealth + "|" + _habitat.getKey();
    }
}

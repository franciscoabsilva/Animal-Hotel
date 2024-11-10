package hva.employee;

import java.io.Serial;
import java.io.Serializable;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import hva.animal.Specie;
import hva.satisfaction.SatisfactionCalculator;
import hva.satisfaction.VeterinarianSatisfactionCalculator;
import hva.vaccine.Vaccination;

/**
 * Represents a veterinarian who is responsible for the health and treatment of animals.
 * A veterinarian may specialize in certain species.
 */
public class Veterinarian extends Employee<Specie> {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** A map of species that the veterinarian specializes in. */
    private Map<String, Specie> _species = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private List<Vaccination> _medicalActs = new ArrayList<>();

    private final SatisfactionCalculator _satisfactionCalculator = new VeterinarianSatisfactionCalculator();

    /**
     * Constructs a new Veterinarian with the specified key and name.
     * 
     * @param veterinarianKey The unique key identifier for the veterinarian.
     * @param veterinarianName The name of the veterinarian.
     */
    public Veterinarian(String veterinarianKey, String veterinarianName) {
        super(veterinarianKey, veterinarianName);
    }

    /**
     * Constructs a new Veterinarian with the specified key, name, and a list of species.
     * 
     * @param veterinarianKey The unique key identifier for the veterinarian.
     * @param veterinarianName The name of the veterinarian.
     * @param species An array of species that the veterinarian specializes in.
     */
    public Veterinarian(String veterinarianKey, String veterinarianName, Specie[] species) {
        super(veterinarianKey, veterinarianName);
        for (Specie specie : species) {
            _species.put(specie.getKey(), specie);
        }
    }

    public void addResponsability(Specie responsability) {
        _species.put(responsability.getKey(), responsability);
    }

    public void removeResponsability(Specie responsability) {
        _species.remove(responsability.getKey());
    }

    public boolean hasResponsability(Specie responsability) {
        return _species.containsKey(responsability.getKey());
    }

    public void addVaccination(Vaccination vaccination) {
        _medicalActs.add(vaccination);
    }


    public String showMedicalActs() {
        return _medicalActs.stream() 
                .map(Vaccination::toString)
                .collect(Collectors.joining("\n"));
    }

    public Specie[] getSpecies() {
        return _species.values().toArray(new Specie[0]);
    }

    public double calculateSatisfaction() {
        return _satisfactionCalculator.calculateSatisfaction(this);
    }

    @Override
    // Format: VET|id|nome|idResponsabilidades
    public String toString() {
        if (_species.isEmpty())
            return "VET|" + super.toString();
        else
            return "VET|" + super.toString() + "|" + String.join(",", _species.keySet());
    }
}

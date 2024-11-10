package hva.vaccine;

import java.io.Serial;
import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Collection;

import hva.animal.Specie;
import hva.animal.Animal;
import hva.employee.Veterinarian;

/**
 * Represents a vaccine that can be administered to various species of animals.
 * It keeps track of the vaccinable species and the orders of vaccinations performed.
 */
public class Vaccine implements Serializable {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The unique key identifier for the vaccine. */
    private final String _vaccineKey;

    /** The name of the vaccine. */
    private String _vaccineName;

    /** A map of species that can be vaccinated with this vaccine. */
    private Map<String, Specie> _vaccinableSpecies = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** A registry of vaccine orders, mapping each animal to the veterinarian who administered the vaccine. */
    private List<Vaccination> _vaccinationsHistory = new ArrayList<>();



    /**
     * Constructs a new Vaccine with the specified key and name.
     * 
     * @param vaccineKey The unique key identifier for the vaccine.
     * @param vaccineName The name of the vaccine.
     */
    public Vaccine(String vaccineKey, String vaccineName) {
        _vaccineKey = vaccineKey;
        _vaccineName = vaccineName;
    }

    /**
     * Constructs a new Vaccine with the specified key, name, and a list of vaccinable species.
     * 
     * @param vaccineKey The unique key identifier for the vaccine.
     * @param vaccineName The name of the vaccine.
     * @param vaccinableSpecies An array of species that can be vaccinated with this vaccine.
     */
    public Vaccine(String vaccineKey, String vaccineName, Specie[] vaccinableSpecies) {
        _vaccineKey = vaccineKey;
        _vaccineName = vaccineName;
        for (Specie specie : vaccinableSpecies) {
            _vaccinableSpecies.put(specie.getKey(), specie);
        }
    }

    public boolean canVaccinate(Specie specie) {
        return _vaccinableSpecies.containsKey(specie.getKey());
    }

    public void addVaccination(Vaccination vaccination){
        _vaccinationsHistory.add(vaccination);
    }

    public String getKey() {
        return _vaccineKey;
    }

    public Collection<Specie> getVaccinableSpecies() {
        return _vaccinableSpecies.values();
    }

    @Override
    // Format:  VACINA|idVacina|nomeVacina|númeroDeAplicações|espécies
    public String toString() {
        if (_vaccinableSpecies.isEmpty())
            return "VACINA|" + _vaccineKey + "|" + _vaccineName + "|" +
            _vaccinationsHistory.size();
        else
            return "VACINA|" + _vaccineKey + "|" + _vaccineName + "|" +
            _vaccinationsHistory.size() + "|" + String.join(",", _vaccinableSpecies.keySet());
    }
}

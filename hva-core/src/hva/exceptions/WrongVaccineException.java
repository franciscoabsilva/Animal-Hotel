package hva.exceptions;

import java.io.Serial;

/**
 * Veterinarian is not allowed to vaccinate animal
 */
public class WrongVaccineException extends Exception {
  
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The veterinarian key. */
    private final String _vaccineKey;

    /** The animal key. */
    private final String _animalKey;

    /**  
     *  @param vaccineKey
     *  @param animalKey
     */
    public WrongVaccineException(String vaccineKey, String animalKey) {
        _vaccineKey = vaccineKey;
        _animalKey = animalKey;
    }

    public String getVaccineKey() {
        return _vaccineKey;
    }

    public String getAnimalKey() {
        return _animalKey;
    }
}
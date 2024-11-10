package hva.exceptions;

import java.io.Serial;

/**
 * Veterinarian is not allowed to vaccinate animal
 */
public class VeterinarianCantVaccinateException extends Exception {
  
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The veterinarian key. */
    private final String _veterinarianKey;

    /** The specie key. */
    private final String _specieKey;

    /**  
     *  @param veterinarianKey
     *  @param specieKey
     */
    public VeterinarianCantVaccinateException(String veterinarianKey, String specieKey) {
        _veterinarianKey = veterinarianKey;
        _specieKey = specieKey;
    }

    /** @return the veterinarian key */
    public String getVeterinarianKey() {
        return _veterinarianKey;
    }

    /** @return the specie key */
    public String getSpecieKey() {
        return _specieKey;
    }

}
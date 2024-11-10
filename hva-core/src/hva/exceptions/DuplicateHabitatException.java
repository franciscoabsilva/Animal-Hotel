package hva.exceptions;

import java.io.Serial;

/**
 * Given key has already been used.
 */
public class DuplicateHabitatException extends Exception {
  
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The animal key. */
    private final String _key;

    /** @param key */
    public DuplicateHabitatException(String key) {
        _key = key;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }

}
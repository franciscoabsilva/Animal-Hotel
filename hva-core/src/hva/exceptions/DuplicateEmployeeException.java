package hva.exceptions;

import java.io.Serial;

/**
 * Given key has already been used.
 */
public class DuplicateEmployeeException extends Exception {
  
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The employee key. */
    private final String _key;

    /** @param key */
    public DuplicateEmployeeException(String key) {
        _key = key;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }

}
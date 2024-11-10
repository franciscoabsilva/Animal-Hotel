package hva.exceptions;

import java.io.Serial;

/**
 * No employee has the given key.
 */
public class UnknownEmployeeException extends Exception {
  
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The employee key. */
    private final String _key;

    /** @param key */
    public UnknownEmployeeException(String key) {
        _key = key;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }
}
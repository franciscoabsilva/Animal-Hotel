package hva.exceptions;

import java.io.Serial;

/**
 * No tree has the given key.
 */
public class UnknownTreeException extends Exception {
  
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The tree key. */
    private final String _key;

    /** @param key */
    public UnknownTreeException(String key) {
        _key = key;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }

}
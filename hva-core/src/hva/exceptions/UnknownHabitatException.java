package hva.exceptions;

import java.io.Serial;

/**
 * No habitat has the given key.
 */
public class UnknownHabitatException extends Exception {
  
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The habitat key. */
    private final String _key;

    /** @param key */
    public UnknownHabitatException(String key) {
        _key = key;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }

}
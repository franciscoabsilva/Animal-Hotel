package hva.exceptions;

import java.io.Serial;

/**
 * No veterinarian has the given key.
 */
public class UnknownVeterinarianException extends Exception {
  
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The veterinarian key. */
    private final String _key;

    /** @param key */
    public UnknownVeterinarianException(String key) {
        _key = key;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }

}
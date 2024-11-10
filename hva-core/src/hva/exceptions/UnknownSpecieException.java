package hva.exceptions;

import java.io.Serial;

/**
 * No specie has the given key.
 */
public class UnknownSpecieException extends Exception {
  
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The animal key. */
    private final String _key;

    /** @param key */
    public UnknownSpecieException(String key) {
        _key = key;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }

}
package edu.kit.informatik.constructs.databind;

/**
 * This class is used as a blueprint for a key-value-pair, also known as a map.
 * @param <K> The data type of the key.
 * @param <V> The data type of the value.
 */
public class KeyValuePair<K, V> {
    private K key;
    private V value;

    /**
     * Instantiates a new Key Value Pair.
     * @param key The key of the pair.
     * @param value The value of the pair.
     */
    public KeyValuePair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Gets the key of the pair.
     * @return The key of the pair.
     */
    public K getKey() {
        return key;
    }

    /**
     * Sets the key of the pair.
     * @param key The new key.
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Gets the value of the pair.
     * @return The value of the pair.
     */
    public V getValue() {
        return value;
    }

    /**
     * Sets the value of the pair.
     * @param value The new value.
     */
    public void setValue(V value) {
        this.value = value;
    }
}
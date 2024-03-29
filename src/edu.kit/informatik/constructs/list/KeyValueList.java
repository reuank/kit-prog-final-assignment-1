package edu.kit.informatik.constructs.list;

import edu.kit.informatik.constructs.databind.KeyValuePair;

/**
 * Used for key-value-lists.
 * @param <K>
 * @param <V>
 */
public class KeyValueList<K, V> extends List<KeyValuePair<K, V>> {
    /**
     * Checks whether the KeyValueList contains the given key.
     * @param key The key that shall be searched for.
     * @return Returns true if the key way found in the list.
     */
    public boolean containsKey(K key) {
        return findPairByKey(key) != null;
    }

    /**
     * Gets the value that corresponds to a key.
     * @param key The key whose corresponding value shall be returned.
     * @return The value that belongs to the given key.
     */
    public V getValueByKey(K key) {
        return findPairByKey(key).getValue();
    }

    /**
     * Finds a KeyValuePair in the list that has a specific key.
     * @param key The key that shall be searched for.
     * @return Returns the matching KeyValuePair if existent, else null.
     */
    private KeyValuePair<K, V> findPairByKey(K key) {
        Iterator it = iterator();
        while (it.hasNext()) {
            if (it.currentData().getKey().equals(key)) {
                return it.currentData();
            }
            it.next();
        }

        return null;
    }

    /**
     * Adds a new key-value-pair to the list.
     * @param key The key of the pair.
     * @param value The corresponding value.
     */
    public void add(K key, V value) {
        this.add(new KeyValuePair<>(key, value));
    }
}
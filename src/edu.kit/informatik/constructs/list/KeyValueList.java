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
        Iterator it = iterator();
        while (it.hasNext()) {
            if (it.currentData().getKey().equals(key)) {
                return true;
            }
            it.next();
        }

        return false;
    }

    /**
     * Gets the value that corresponds to a key.
     * @param key The key whose corresponding value shall be returned.
     * @return The value that belongs to the given key.
     */
    public V getValueByKey(K key) {
        Iterator it = iterator();
        while (it.hasNext()) {
            if (it.currentData().getKey().equals(key)) {
                return it.currentData().getValue();
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
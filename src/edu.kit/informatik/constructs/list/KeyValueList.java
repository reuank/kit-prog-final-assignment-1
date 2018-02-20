package edu.kit.informatik.constructs.list;

import edu.kit.informatik.constructs.databind.KeyValuePair;

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
     * @param key
     * @return
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

    public void add(K key, V value) {
        this.add(new KeyValuePair<>(key, value));
    }
}
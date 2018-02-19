package edu.kit.informatik.constructs.list;

import edu.kit.informatik.constructs.databind.KeyValuePair;

public class KeyValueList<K, V> extends List<KeyValuePair<K, V>> {
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
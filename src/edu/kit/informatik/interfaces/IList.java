package edu.kit.informatik.interfaces;

import edu.kit.informatik.constructs.list.List;

public interface IList<T> {

    List.Iterator iterator();

    void add(T item);

    void add(T item, int addAt);

    boolean contains(T needle);

    boolean remove(T needle);

    default boolean remove(int index) {
        return remove(get(index));
    }

    T get(int index);

    default T getFirst() {
        return get(0);
    }

    default T getLast() {
        return get(size() - 1);
    }

    int size();

    default boolean isEmpty() {
        return !this.iterator().hasNext();
    }
}

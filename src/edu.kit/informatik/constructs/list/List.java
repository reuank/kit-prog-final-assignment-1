package edu.kit.informatik.constructs.list;

public class List<T> {
    private ListItem head;

    /**
     * Constructor for lists.
     */
    public List() {
        this.head = null;
    }

    /**
     * Creates a new list iterator.
     * @return Returns the created iterator.
     */
    public Iterator iterator() {
        return new Iterator(this.head);
    }

    /**
     * Adds an item to the end of the list.
     * @param item The item that should be added.
     */
    public void add(T item) {
        // Create first entry if list is empty
        if (this.head == null) {
            this.head = new ListItem(item, null);
            return;
        }

        ListItem i = this.head;
        while (i.next != null) { // Go to the end of the lsit
            i = i.next;
        }

        i.next = new ListItem(item, null);
    }

    /**
     * Adds an item to a list at a specific index. Adds to the end of the list, if "addAt" is invalid.
     * @param item The item that should be added
     * @param addAt The index you want to place the element at.
     */
    public void add(T item, int addAt) {
        if (addAt < 0 || addAt > (size() - 1)) {
            add(item);
            return;
        }

        int currentIndex = 0;
        ListItem i = this.head;

        if (addAt == 0) {
            this.head = new ListItem(item, this.head);
            return;
        }

        while (i.next != null && currentIndex < (addAt - 1)) {
            i = i.next;
            currentIndex++;
        }

        i.next = new ListItem(item, i.next);
    }

    /**
     * Checks whether a list element is in the list.
     * @param needle The element that shall be searched for.
     * @return Returns true if element is in the list.
     */
    public boolean contains(T needle) {
        Iterator it = iterator();
        while (it.hasNext()) {
            if (it.currentData() == needle) {
                return true;
            }
            it.next();
        }

        return false;
    }

    /**
     * Removes a list element from a list
     * @param needle The element that should be removed
     * @return Returns true if the element was removed
     */
    public boolean remove(T needle) {
        if (needle == null) {
            return false;
        }

        ListItem i = this.head;
        boolean deleted = false;

        // Head Element(e) entfernen
        while (i != null && i.itemData == needle) {
            i = i.next;
            this.head = i;
            deleted = true;
        }

        // Liste leer?
        if (i == null) {
            return deleted; // Nichts mehr zu tun...
        }

        // Liste durchsuchen
        while (i.next != null) {
            if (i.next.itemData == needle) { // Gefunden -> Löschen
                i.next = i.next.next;
                deleted = true;
            } else {
                i = i.next;
            }
        }

        return deleted;
    }

    /**
     * Removes the n-th element from a list
     * @param index The index of the element that should be removed
     * @return Returns true if the element was removed
     */
    public boolean remove(int index) {
        return remove(get(index));
    }

    /**
     * Gets the n-th list element
     * @param index The position of the element you want to get, beginning at 1
     * @return Returns the n-th list element (n = index)
     */
    public T get(int index) {
        if (index < 0 || (index > size() - 1)) {
            return null;
        }

        Iterator it = iterator();

        if (index == 0) {
            return it.currentData();
        } else {
            int i = 0;
            while (it.hasNext() && i < index) {
                it.next();
                i++;
            }

            return it.currentData();
        }
    }

    /**
     * @return Returns the first list element, null if list is empty
     */
    public T getFirst() {
        return get(0);
    }

    /**
     * @return Returns the last list element, null if list is empty
     */
    public T getLast() {
        return get(size() - 1);
    }

    /**
     * Checks whether there are elements in the list
     * @return Returns false if there are list elements in the list
     */
    public boolean isEmpty() {
        return !this.iterator().hasNext();
    }

    /**
     * @return Returns the size of the list, beginning at 1
     */
    public int size() {
        int size = 0;
        List.Iterator it = iterator();
        while (it.hasNext()) {
            it.next();
            size++;
        }

        return size;
    }

    /**
     * Blueprint for a list item. Has content and one connection to the next element.
     */
    private class ListItem {
        T itemData;
        ListItem next;

        ListItem(T itemData, ListItem next) {
            this.itemData = itemData;
            this.next = next;
        }
    }

    public final class Iterator {
        private ListItem cursor;

        private Iterator(ListItem start) { // privater Konstruktor
            this.cursor = start;
        }

        /**
         * @return Returns the itemData of the current listItem. Null if last element or list empty.
         */
        public T currentData() { // query
            if (this.cursor == null) {
                return null;
            }

            return this.cursor.itemData;
        }

        /**
         * @return Returns true if list element has next element
         */
        public boolean hasNext() { // query
            return this.cursor != null;
        }

        /**
         * Moves the cursor one step further in the list
         */
        public void next() { // command
            this.cursor = this.cursor.next;
        }
    }
}
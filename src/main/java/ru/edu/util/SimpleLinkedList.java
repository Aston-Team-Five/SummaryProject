package ru.edu.util;

import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public class SimpleLinkedList<E> extends AbstractSequentialList<E> implements List<E> {

    private Element<E> head;
    private Element<E> trailer;
    private int size;

    public SimpleLinkedList() {
    }

    public SimpleLinkedList(Collection<? extends E> collection) {
        addAll(collection);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int index = 0;
        for (Element<E> current = head; current != null; current = current.next, index++) {
            result[index] = current.item;
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        int index = 0;
        Object[] result = a;
        for (Element<E> current = head; current != null; current = current.next, index++) {
            result[index] = current.item;
        }

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    private void addLast(E e) {
        Element<E> element = new Element<>(trailer, e, null);
        if (trailer != null) {
            trailer.next = element;
        }
        trailer = element;
        if (head == null) {
            head = element;
        }
        size++;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (Element<E> current = head; current != null; current = current.next) {
                if (current.item == null) {
                    unlink(current);
                    return true;
                }
            }
        } else {
            for (Element<E> current = head; current != null; current = current.next) {
                if (o.equals(current.item)) {
                    unlink(current);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = !c.isEmpty();
        for (E e : c) {
            addLast(e);
        }
        return result;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);
        boolean result = !c.isEmpty();

        // долго выполняется, т. к. каждую итерацию выполняется поиск элемента по индексу, переделать
        // сначала слинковать элементы из коллекции друг с другом, полученный список прилинковать к текущему списку
        int i = index;
        for (E item : c) {
            add(i, item);
            i++;
        }

        return result;
    }

    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    /**
     * Tells if the argument is the index of an existing element.
     */
    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    /**
     * Check if the argument is the index of an existing element.
     */
    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    /**
     * Constructs an IndexOutOfBoundsException detail message.
     * Of the many possible refactorings of the error handling code,
     * this "outlining" performs best with both server and client VMs.
     */
    private String outOfBoundsMsg(int index) {
        return "List has " + size + " elements but received index " + index;
    }

    @Override
    public void clear() {
        clearLinks();
        size = 0;
        trailer = null;
    }

    /**
     * Очистим ссылки, чтобы сборщику мусора было легче удалить неиспользуемые объекты
     */
    private void clearLinks() {
        while (head != null) {
            Element<E> current = head;
            head = head.next;
            current.prev = null;
            current.item = null;
            current.next = null;
        }
    }

    @Override
    public E get(int index) {
        return getElement(index).item;
    }

    private Element<E> getElement(int index) {
        checkElementIndex(index);

        if (index < size - index) {
            return getElementFromHead(index);
        } else {
            return getElementFromTrailer(index);
        }
    }

    private Element<E> getElementFromHead(int index) {
        int currentIndex = 0;
        Element<E> currentElement = head;
        while (currentIndex != index) {
            currentElement = currentElement.next;
            currentIndex++;
        }
        return currentElement;
    }

    private Element<E> getElementFromTrailer(int index) {
        int currentIndex = size - 1;
        Element<E> currentElement = trailer;
        while (currentIndex != index) {
            currentElement = currentElement.prev;
            currentIndex--;
        }
        return currentElement;
    }

    @Override
    public E set(int index, E element) {
        Element<E> currentElement = getElement(index);
        E oldItem = currentElement.item;
        currentElement.item = element;
        return oldItem;
    }

    @Override
    public void add(int index, E element) {
        if (index == size) {
            addLast(element);
        } else {
            addBefore(index, element);
        }
    }

    private void addBefore(int index, E element) {
        Element<E> currentElement = getElement(index);
        addBefore(element, currentElement);
    }

    void addBefore(E element, Element<E> currentElement) {
        Element<E> newElement = new Element<>(currentElement.prev, element, currentElement);
        currentElement.prev = newElement;
        if (newElement.prev == null) {
            head = newElement;
        } else {
            newElement.prev.next = newElement;
        }
        size++;
    }

    @Override
    public E remove(int index) {
        Element<E> currentElement = getElement(index);
        return unlink(currentElement);
    }

    private E unlink(Element<E> element) {
        final E item = element.item;
        final Element<E> next = element.next;
        final Element<E> prev = element.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            element.prev = null;
        }

        if (next == null) {
            trailer = prev;
        } else {
            next.prev = prev;
            element.next = null;
        }

        element.item = null;
        size--;
        return item;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (Element<E> current = head; current != null; current = current.next, index++) {
                if (current.item == null)
                    return index;
            }
        } else {
            for (Element<E> current = head; current != null; current = current.next, index++) {
                if (o.equals(current.item))
                    return index;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = size - 1;
        if (o == null) {
            for (Element<E> x = trailer; x != null; x = x.prev, index--) {
                if (x.item == null) {
                    return index;
                }
            }
        } else {
            for (Element<E> x = trailer; x != null; x = x.prev, index--) {
                if (o.equals(x.item)) {
                    return index;
                }
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        getElement(index);
        return new SimpleListIterator(index);
    }

    @Override
    public String toString() {
        return "SimpleLinkedList{" + elementsToString() + '}';
    }

    private String elementsToString() {
        if (size == 0) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(head.item);
        Element<E> current = head.next;
        while (current != null) {
            stringBuilder.append(", ").append(current.item);
            current = current.next;
        }
        return stringBuilder.toString();
    }


    /**
     * Compares the specified object with this list for equality.  Returns
     * {@code true} if and only if the specified object is also a list, both
     * lists have the same size, and all corresponding pairs of elements in
     * the two lists are <i>equal</i>.  (Two elements {@code e1} and
     * {@code e2} are <i>equal</i> if {@code Objects.equals(e1, e2)}.)
     * In other words, two lists are defined to be
     * equal if they contain the same elements in the same order.  This
     * definition ensures that the equals method works properly across
     * different implementations of the {@code List} interface.
     *
     * @param o the object to be compared for equality with this list
     * @return {@code true} if the specified object is equal to this list
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof List))
            return false;

        ListIterator<E> e1 = listIterator();
        ListIterator<?> e2 = ((List<?>) o).listIterator();
        while (e1.hasNext() && e2.hasNext()) {
            E o1 = e1.next();
            Object o2 = e2.next();
            if (!(o1 == null ? o2 == null : o1.equals(o2)))
                return false;
        }
        return !(e1.hasNext() || e2.hasNext());
    }

    /**
     * Returns the hash code value for this list.
     *
     * @return the hash code value for this list
     * @implSpec This implementation uses exactly the code that is used to define the
     * list hash function in the documentation for the {@link List#hashCode}
     * method.
     */
    @Override
    public int hashCode() {
        int hashCode = 1;
        for (E e : this)
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        return hashCode;
    }

    private static class Element<E> {
        E item;
        Element<E> next;
        Element<E> prev;

        Element(Element<E> prev, E element, Element<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private class SimpleListIterator implements ListIterator<E> {
        private Element<E> lastReturned;
        private Element<E> next;
        private int nextIndex;
        private int expectedModCount = modCount;

        SimpleListIterator(int index) {
            next = (index == size) ? null : getElement(index);
            nextIndex = index;
        }

        public boolean hasNext() {
            return nextIndex < size;
        }

        public E next() {
            checkForComodification();
            if (!hasNext())
                throw new NoSuchElementException();

            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.item;
        }

        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        public E previous() {
            checkForComodification();
            if (!hasPrevious())
                throw new NoSuchElementException();

            lastReturned = next = (next == null) ? trailer : next.prev;
            nextIndex--;
            return lastReturned.item;
        }

        public int nextIndex() {
            return nextIndex;
        }

        public int previousIndex() {
            return nextIndex - 1;
        }

        public void remove() {
            checkForComodification();
            if (lastReturned == null)
                throw new IllegalStateException();

            Element<E> lastNext = lastReturned.next;
            unlink(lastReturned);
            if (next == lastReturned)
                next = lastNext;
            else
                nextIndex--;
            lastReturned = null;
            expectedModCount++;
        }

        public void set(E e) {
            if (lastReturned == null)
                throw new IllegalStateException();
            checkForComodification();
            lastReturned.item = e;
        }

        public void add(E e) {
            checkForComodification();
            lastReturned = null;
            if (next == null)
                addLast(e);
            else
                addBefore(e, next);
            nextIndex++;
            expectedModCount++;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            while (modCount == expectedModCount && nextIndex < size) {
                action.accept(next.item);
                lastReturned = next;
                next = next.next;
                nextIndex++;
            }
            checkForComodification();
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
}

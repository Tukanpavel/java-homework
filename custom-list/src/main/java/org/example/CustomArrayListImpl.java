package org.example;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Stream;


public class CustomArrayListImpl<E> implements CustomArrayList<E> {
    private static final int INITIAL_CAPACITY = 10;

    private int size = 0;
    private transient Object[] arrayList;

    public CustomArrayListImpl() {
        arrayList = new Object[INITIAL_CAPACITY];
    }

    public CustomArrayListImpl(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Cannot create an array with negative capacity");
        }
        if (initialCapacity == 0) {
            arrayList = new Object[INITIAL_CAPACITY];
        } else {
            arrayList = new Object[initialCapacity];
        }
    }

    public void add(int index, E element) {
        isInRage(index);
        ensureCapacity(1);
        arrayList[index] = element;
        size++;
    }

    private void ensureCapacity(int addedLength) {
        int minCapacity = size() + addedLength;
        if (minCapacity > capacity()) {
            extendArray(minCapacity);
        }
    }

    private int capacity() {
        return arrayList.length;
    }

    private void extendArray(int minCapacity) {
        int oldCapacity = capacity();
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        while (newCapacity < minCapacity) {
            newCapacity += (newCapacity >> 1);
        }
        var temp = new Object[newCapacity];
        System.arraycopy(arrayList, 0, temp, 0, oldCapacity);
        arrayList = temp;
    }

    private void isInRage(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index is negative");
        }
        if (index >= size()) {
            throw new IndexOutOfBoundsException("Index exceeded the index of the last element");
        }
    }

    private int size() {
        return size;
    }

    public void addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return;
        }
        ensureCapacity(c.size());
        var cArr = c.toArray();
        System.arraycopy(cArr, 0, arrayList, size(), cArr.length);
        size += c.size() - 1;
    }

    public void clear() {
        arrayList = new Object[]{};
    }

    public E get(int index) {
        return (E) arrayList[index];
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void remove(int index) {
        isInRage(index);
        System.arraycopy(arrayList, index + 1, arrayList, index, size() - index);
        size--;
    }

    public Boolean remove(E o) {
        if (o == null) {
            for (int i = 0; i < size(); i++) {
                if (arrayList[i] == null) {
                    remove(i);
                    return false;
                }
            }
            return true;
        }
        for (int i = 0; i < size(); i++) {
            if (o.equals(arrayList[i])) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    public void sort(Comparator<? super E> c) {
        var tempList = new Object[size() + 1];
        System.arraycopy(arrayList, 0, tempList, 0, size() + 1);
        tempList = quickSort(c, tempList);
        System.arraycopy(tempList, 0, arrayList, 0, size() + 1);
    }

    @Override
    public int length() {
        return size();
    }

    private Object[] quickSort(Comparator<? super E> c, Object[] array) {
        if (array.length == 0) {
            return array;
        }
        var mid = (E) array[0];
        return Stream.concat(
                Stream.concat(
                        Arrays.stream(
                                quickSort(c,
                                        Arrays.stream(array)
                                                .filter(e -> c.compare((E) e, mid) < 0).toArray())
                        ),
                        Arrays.stream(array).filter(e -> e.equals(mid))
                ), Arrays.stream(
                        quickSort(c,
                                Arrays.stream(array)
                                        .filter(e -> c.compare((E) e, mid) > 0).toArray())
                )
        ).toArray();
    }
}

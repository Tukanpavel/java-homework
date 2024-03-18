package org.example;

import java.util.Collection;
import java.util.Comparator;

public interface CustomArrayList<E> {
    void add(int index, E element);

    boolean addAll(Collection<? extends E> c);

    void clear();

    E get(int index);

    boolean isEmpty();

    void remove(int index);

    Boolean remove(E o);

    void sort(Comparator<? super E> c);
}

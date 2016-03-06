package at.jku.oeh.lan.laganizer.model.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class converter {
    public static <E> Set<E> iterableToSet(Iterable<E> iterable) {
        Set<E> set = new HashSet<>();
        for (E e : iterable) {
            set.add(e);
        }
        return set;
    }

    public static <E> List<E> iterableToList(Iterable<E> iterable) {
        List<E> list = new LinkedList<>();
        for (E e : iterable) {
            list.add(e);
        }
        return list;
    }
}

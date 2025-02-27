package com.ctw.workstation.data;

import java.util.*;

public class Database<T> {
    protected final Map<Integer, T> map;

    public Database() {
        map = new HashMap<Integer, T>();
    }

    public int add(final T t) {
        map.put(t.getId(), t);
        return t.getId();
    }

    public void remove(final int id) {
        map.remove(id);
    }

    public T find(final int id) {
        return map.get(id);
    }

    public List<T> asList() {
        return new ArrayList<T>(map.values());
    }

    public Set<T> asSet() {
        return new HashSet<>(map.values());
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

}

package com.javarush.task.task37.task3707;

import java.io.*;
import java.util.*;

public class AmigoSet<E> extends AbstractSet<E> implements Serializable,Cloneable, Set<E> {
    private final static Object PRESENT = new Object();
    private transient HashMap<E,Object> map;

    public AmigoSet() {
        map = new HashMap<>();
    }
    public AmigoSet(Collection<? extends E> collection) {
        this.map = new HashMap<>(Math.max((int) (collection.size() / .75f) + 1, 16));
        addAll(collection);
        HashSet<E> set;
    }

    @Override
    public boolean add(E e) {

           Object ob = map.put(e, PRESENT);
        if(ob==null) return true;
        else return false;
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean remove(Object o) {
        Object ob = map.remove(o);
        if(ob==null) return true;
        else return false;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        AmigoSet<E> cloned = new AmigoSet<>();
        try{
            cloned.map = (HashMap<E, Object>) this.map.clone();
        }catch (Exception e){
            throw new InternalError(e);
        }
        return cloned;
    }

    private void writeObject(ObjectOutputStream s) throws java.io.IOException {
        s.defaultWriteObject();
        s.writeInt(HashMapReflectionHelper.<Integer>callHiddenMethod(map, "capacity"));
        s.writeFloat(HashMapReflectionHelper.<Float>callHiddenMethod(map, "loadFactor"));
        s.writeInt(map.size());
        for (E e : map.keySet())
            s.writeObject(e);
    }


    private void readObject(ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();
        int capacity = s.readInt();
        float loadFactor = s.readFloat();
        map = new HashMap<>(capacity, loadFactor);
        int size = s.readInt();
        for (int i = 0; i < size; i++) {
            E e = (E) s.readObject();
            map.put(e, PRESENT);
        }
    }
}

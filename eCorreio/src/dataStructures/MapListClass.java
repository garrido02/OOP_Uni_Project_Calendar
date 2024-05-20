package dataStructures;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapListClass<K, E> implements MapList<K, E>{
    private Map<K, List<E>> elems;


    public MapListClass(){
        elems = new HashMap<>();
    }

    @Override
    public void addElem(Object key, Object elem) {
        List<E> l;
        if (elems.containsKey(key)){
            l = elems.get(key);
        } else {
            l = new LinkedList<>();
            elems.put((K) key, l);
        }
        l.add((E) elem);
    }

    @Override
    public boolean containsKey(Object key) {
        return elems.containsKey(key);
    }

    @Override
    public List getElem(Object key) {
        return elems.get(key);
    }

    @Override
    public void remove(Object key, List elem) {
        List<E> l;
        l = elems.get(key);
        l.remove((E) elem);
    }
}

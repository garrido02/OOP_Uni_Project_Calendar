package dataStructures;
import java.util.List;

public interface MapList<K, E> {
    void addElem(K key, E elem);
    boolean containsKey(K key);
    List<E> getElem(K key);
    void remove(K key, List elem);
}

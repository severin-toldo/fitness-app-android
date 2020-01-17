package com.stoldo.fitness_app_android.model;

import java.util.Comparator;
import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

public class Tuple<K, V> implements Comparator {
    @Getter
    @Setter
    private K key;

    @Getter
    @Setter
    private V value;

    private HashMap<String, Object> extras = new HashMap<>();

    public Tuple(K key, V value){
        this.key = key;
        this.value = value;
    }

    public void setExtra(String key, Object value){
        extras.put(key, value);
    }

    public Object getExtra(String key){
        return extras.get(key);
    }

    @Override
    public int compare(Object o1, Object o2) {
        return Integer.compare((Integer)((Tuple<K, V>)o1).getExtra("index"), (Integer)((Tuple<K, V>)o2).getExtra("index"));
    }
}

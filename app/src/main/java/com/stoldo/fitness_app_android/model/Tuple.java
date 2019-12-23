package com.stoldo.fitness_app_android.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tuple<K, V> {
    private K key;
    private V value;

    public Tuple(K key, V value){
        this.key = key;
        this.value = value;
    }


}

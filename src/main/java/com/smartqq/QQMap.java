package com.smartqq;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class QQMap<K, V> {

    private Map<K, V> map = new ConcurrentHashMap<>();

    public V put(K k, V v){
        map.put(k, v);
        return v;
    }

    public V get(K k){
        return map.get(k);
    }

    public K valueOf(V v){
        if(v==null) {
            return null;
        }

        for (K k : map.keySet()) {
            if(v == map.get(k)){
                return k;
            }
        }

        return null;
    }

    public V remove(K k){
        return map.remove(k);
    }

    public Set<K> keySet(){
        return map.keySet();
    }

}

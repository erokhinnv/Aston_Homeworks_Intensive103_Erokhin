package org.example;

import java.util.HashMap;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        MyHashMap<Integer, String> hashMap = new MyHashMap<>();
        for (int i = 1; i < 1000000; ++i) {
            hashMap.put(i, "val" + i);
        }
        System.out.println(hashMap.getSize());
        for (int i = 999999; i > 500000; --i) {
            hashMap.remove(i);
        }
        System.out.println(hashMap.getSize());

        //hashMap.remove(1);
        //System.out.println(hashMap.get(2));
    }
}
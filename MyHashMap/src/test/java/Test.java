import org.example.MyHashMap;
import org.junit.jupiter.api.Assertions;

import java.util.Map;
import java.util.Set;

public class Test {

    @org.junit.jupiter.api.Test
    void testPut() {
        MyHashMap<Integer, String> myHashMap;

        myHashMap = new MyHashMap<>();
        myHashMap.put(1, "First");
        Assertions.assertFalse(myHashMap.isEmpty());
        Assertions.assertEquals(1, myHashMap.getSize());
        Assertions.assertEquals("First", myHashMap.get(1));
        Assertions.assertEquals("First", myHashMap.put(1, "Second"));
        Assertions.assertEquals(1, myHashMap.getSize());
        for (int i = 2; i < 1000000; ++i) {
            myHashMap.put(i, "Val" + i);
        }
        Assertions.assertEquals(999999, myHashMap.getSize());

    }

    @org.junit.jupiter.api.Test
    void testResize() {
        MyHashMap<Integer, String> myHashMap;
        myHashMap = new MyHashMap<>();
        for (int i = 1; i < 100000; ++i) {
            myHashMap.put(i, "val" + i);
        }
        Assertions.assertEquals("val100", myHashMap.get(100));
    }

    @org.junit.jupiter.api.Test
    void testGet() {
        MyHashMap<Integer, String> myHashMap;
        myHashMap = new MyHashMap<>();
        Assertions.assertNull(myHashMap.get(1));
        myHashMap.put(1, "First");
        Assertions.assertEquals("First", myHashMap.get(1));
    }

    @org.junit.jupiter.api.Test
    void testRemove() {
        MyHashMap<Integer, String> myHashMap;
        MyHashMap<Integer, String> myHashMap2;

        myHashMap = new MyHashMap<>();
        myHashMap.put(1, "First");
        myHashMap.put(2, "Second");
        myHashMap.put(3, "Third");
        myHashMap.remove(2);
        Assertions.assertNull(myHashMap.get(2));
        Assertions.assertEquals(2, myHashMap.getSize());
        myHashMap2 = new MyHashMap<>();
        for (int i = 1; i < 100000; ++i) {
            myHashMap2.put(i, "val" + i);
        }
        Assertions.assertEquals(99999, myHashMap2.getSize());
        for (int i = 99999; i > 1; --i) {
            myHashMap2.remove(i);
        }
        Assertions.assertEquals(1, myHashMap2.getSize());
    }

    @org.junit.jupiter.api.Test
    void testValues() {
        MyHashMap<Integer, String> myHashMap;
        Set<String> values;

        myHashMap = new MyHashMap<>();
        myHashMap.put(1, "Value1");
        myHashMap.put(2, "Value2");
        values = myHashMap.values();
        Assertions.assertTrue(values.contains("Value1"));
        Assertions.assertTrue(values.contains("Value2"));
        Assertions.assertEquals(2, values.size());
    }

    @org.junit.jupiter.api.Test
    void testKeySet() {
        MyHashMap<Integer, String> myHashMap;
        Set<Integer> keys;

        myHashMap = new MyHashMap<>();
        myHashMap.put(1, "Value1");
        myHashMap.put(2, "Value2");
        keys = myHashMap.keySet();
        Assertions.assertTrue(keys.contains(1));
        Assertions.assertTrue(keys.contains(2));
        Assertions.assertEquals(2, keys.size());
    }

    @org.junit.jupiter.api.Test
    void testEntrySet() {
        MyHashMap<Integer, String> myHashMap;
        Set<Map.Entry<Integer, String>> entries;

        myHashMap = new MyHashMap<>();
        myHashMap.put(1, "Val1");
        myHashMap.put(2, "Val2");
        entries = myHashMap.entrySet();
        Assertions.assertEquals(2, entries.size());
        for (Map.Entry<Integer, String> entry : entries) {
            Assertions.assertTrue(entry.getKey().equals(1) || entry.getKey().equals(2));
            Assertions.assertTrue(entry.getValue().equals("Val1") || entry.getValue().equals("Val2"));
        }
    }
}

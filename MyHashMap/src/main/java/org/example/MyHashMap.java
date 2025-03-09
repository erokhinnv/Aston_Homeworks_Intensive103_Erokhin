package org.example;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class MyHashMap<K, V> {

    // Возвращает количество элементов, находящихся в коллекции
    public int getSize() {
        return size;
    }

    /**
     *  Проверяет, пуста ли коллекция.
     *  Возвращает true, если коллекция пуста и false, если в ней что-то есть
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Добавляет новый элемент в коллекцию.
     * Параметры: key - ключ, value - значение.
     * Возвращает элемент, который находился в словаре по переданному ключу до вызова метода.
     * Если коллекция пуста, то она будет создана с размером 16 (см. resize())
     * Далее вычисляется хэш.
     * По остатку от деления хэша на размер массива корзин вычисляется индекс корзины,
     *  в которую нужно добавить пару. Если корзины не существует, перед добавлением пары она создаётся
     * Далее, идёт проверка на существующий ключ.
     * Если будет найдено совпадение, то есть мы пытаемся добавить новый элемент по уже существующему ключу, метод
     * вернёт значение, которое соответствует существующему ключу, то есть вернёт существующий элемент.
     * Если совпадение не будет найдено, то будет создана новая пара, в которую будут
     * записаны переданные данные (ключ и значение),
     * сама пара будет добавлена в корзину.
     * После добавления пары, актуальное количество элементов в коллекции увеличивается на 1.
     * Далее происходит проверка, превысило ли количество элементов пороговое значение threshold,
     *  при котором коллекция увеличивается в размере.
     * Например, по умолчанию мы устанавливаем размер коллекции 16,
     *  а пороговое значение - 75% от размера коллекции, то есть 12. Когда мы попытаемся добавить 13-й элемент,
     *  общий размер коллекции увеличится в 2 раза (см. resize()).
     * В случае успешного добавления элемента в коллекцию, метод вернёт null,
     *  поскольку перед добавлением по переданному ключу элемента в словаре не было.
     */
    public V put(K key, V value) {
        LinkedList<Pair<K, V>> basket;
        Pair<K, V> newPair;

        if (baskets == null || baskets.length == 0)
            resize();

        int hash = key.hashCode();
        int basketIndex = hash % baskets.length;
        if (baskets[basketIndex] == null)
            baskets[basketIndex] = new LinkedList<>();
        basket = baskets[basketIndex];
        for (Pair<K, V> pair : basket) {
            if (pair.hash == hash && pair.key.equals(key))
                return pair.value;
        }
        newPair = new Pair<>();
        newPair.key = key;
        newPair.value = value;
        newPair.hash = hash;
        basket.push(newPair);
        ++size;
        if (size > threshold)
            resize();

        return null;
    }

    /**
     * Возвращает значение, которому соответствует ключ или null, если
     * словарь не содержит такого значения для ключа.
     * Параметр: key - ключ, по которому требуется получить значение.
     */

    public V get(K key) {
        int hash, basketIndex;
        LinkedList<Pair<K, V>> basket;

        if (baskets == null) {
            return null;
        }
        hash = key.hashCode();
        basketIndex = hash % baskets.length;
        basket = baskets[basketIndex];
        if (basket == null) {
            return null;
        }
        for (Pair<K, V> pair: basket) {
            if (pair.hash == hash && pair.key.equals(key)) {
                return pair.value;
            }
        }
        return null;
    }

    /**
     * Удаляет элемент из коллекции по ключу.
     * Параметр: key - ключ, по которому требуется удалить пару.
     */
    public void remove(K key) {
        int hash, basketIndex;
        LinkedList<Pair<K, V>> basket;

        if (baskets == null)
            return;
        hash = key.hashCode();
        basketIndex = hash % baskets.length;
        basket = baskets[basketIndex];
        if (basket == null)
            return;
        for (Pair<K, V> pair: basket) {
            if (pair.hash == hash && pair.key.equals(key)) {
                basket.remove(pair);
                --size;
                break;
            }
        }
    }

    /**
     * Возвращает множество значений, которые находятся в словаре.
     */
    public Set<V> values() {
        HashSet<V> vals;
        vals = new HashSet<>();
        if (baskets != null) {
            for (LinkedList<Pair<K, V>> basket: baskets) {
                if (basket != null) {
                    for (Pair<K, V> pair : basket) {
                        vals.add(pair.value);
                    }
                }
            }
        }
        return vals;
    }
    /**
     * Возвращает множество ключей, которые находятся в словаре.
     */
    public Set<K> keySet() {
        HashSet<K> keys;
        keys = new HashSet<>();
        if (baskets != null) {
            for (LinkedList<Pair<K, V>> basket: baskets) {
                if (basket != null) {
                    for (Pair<K, V> pair : basket) {
                        keys.add(pair.key);
                    }
                }
            }
        }
        return keys;
    }
    /**
     * Возвращает множество пар ключ-значение, хранящихся в словаре.
     */
    public Set<Map.Entry<K,V>> entrySet() {
        HashSet<Map.Entry<K, V>> entries;
        entries = new HashSet<>();
        if (baskets != null) {
            for (LinkedList<Pair<K, V>> basket: baskets) {
                if (basket != null) {
                    entries.addAll(basket);
                }
            }
        }
        return entries;
    }

    // Пара ключ-значение
    private static class Pair<K, V> implements Map.Entry<K, V> {

        // Возвращает ключ из пары
        @Override
        public K getKey() {
            return key;
        }

        // Возвращает значение из пары
        @Override
        public V getValue() {
            return value;
        }


        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        // Возвращает строку, в которой представлена пара
        public String toString() {
            return key + "=" + value;
        }

        private K key;
        private V value;
        private int hash;
    }

    /**
     * Инициализирует или удваивает размер словаря.
     * Если словарь null, то задаёт начальный размер и пороговое значение (75% от общего размера словаря).
     * Если словарь уже существует, то он копируется, затем создаётся новый словарь увеличенного размера и
     * в новый словарь добавляются пары из старого словаря. При добавлении пар в новый словарь,
     *  для них вычисляются новые индексы корзин по остатку от деления хэша пары на общий размер увеличенного словаря.
     */
    private void resize() {
        int newBasketsSize;
        LinkedList<Pair<K, V>>[] oldBaskets;
        if (baskets == null) {
            newBasketsSize = 16;
        } else {
            newBasketsSize = (baskets.length) * 2;
        }
        @SuppressWarnings({"unchecked"})
        LinkedList<Pair<K, V>>[] newBaskets = (LinkedList<Pair<K, V>>[]) new LinkedList[newBasketsSize];
        oldBaskets = baskets;
        baskets = newBaskets;
        threshold = (int)(newBasketsSize * 0.75);
        if (oldBaskets != null) {
            for (LinkedList<Pair<K, V>> oldBasket : oldBaskets) {
                if (oldBasket != null) {
                    for (Pair<K, V> pair : oldBasket) {
                        int newIndex;
                        newIndex = pair.hash % baskets.length;
                        if (baskets[newIndex] == null)
                            baskets[newIndex] = new LinkedList<>();
                        baskets[newIndex].push(pair);
                    }
                    oldBasket.clear();
                }
            }
        }
    }
    // Массив корзин
    private LinkedList<Pair<K, V>>[] baskets;
    // Актуальное количество пар в словаре
    private int size;
    // Пороговое значение количества пар, при котором общий размер словаря будет удвоен.
    private int threshold;
}
package com.artem.shortener;



import com.artem.shortener.strategy.*;
import com.artem.shortener.tests.FunctionalTest;
import com.artem.shortener.tests.SpeedTest;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        StorageStrategy strategy1 = new HashMapStorageStrategy();
        testStrategy(strategy1, 10000);
        StorageStrategy strategy2 = new OurHashMapStorageStrategy();
        testStrategy(strategy2, 10000);
        StorageStrategy strategy3 = new FileStorageStrategy();
        testStrategy(strategy3, 100);
        StorageStrategy strategy4 = new OurHashBiMapStorageStrategy();
        testStrategy(strategy4, 10000);
        StorageStrategy strategy5 = new HashBiMapStorageStrategy();
        testStrategy(strategy5, 10000);
        StorageStrategy strategy6 = new DualHashBidiMapStorageStrategy();
        testStrategy(strategy6, 10000);

        FunctionalTest functionalTest = new FunctionalTest();
        functionalTest.testHashMapStorageStrategy();
        functionalTest.testOurHashMapStorageStrategy();
        functionalTest.testFileStorageStrategy();
        functionalTest.testOurHashBiMapStorageStrategy();
        functionalTest.testHashBiMapStorageStrategy();
        functionalTest.testDualHashBidiMapStorageStrategy();

        SpeedTest speedTest = new SpeedTest();
        speedTest.testHashMapStorage();
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        Set<Long> set = new HashSet<>();
        for (String string : strings) {
            set.add(shortener.getId(string));
        }
        return set;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        Set<String> set = new HashSet<>();
        for (Long key : keys) {
            set.add(shortener.getString(key));
        }
        return set;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {
        System.out.println(strategy.getClass().getSimpleName() + ": " + elementsNumber + " elements");

        Set<String> stringSet = new HashSet<>();
        for (long i = 0; i < elementsNumber; i++) {
            stringSet.add(Helper.generateRandomString());
        }

        Shortener shortener = new Shortener(strategy);

        long start = new Date().getTime();
        Set<Long> ids = getIds(shortener, stringSet);
        Helper.printMessage("method getIds: " + (new Date().getTime() - start) + " ms");

        start = new Date().getTime();
        Set<String> compareSet = getStrings(shortener, ids);
        Helper.printMessage("method getStrings: " + (new Date().getTime() - start) + " ms");

        if (stringSet.equals(compareSet)) {
            Helper.printMessage("Тест пройден.");
        } else {
            Helper.printMessage("Тест не пройден.");
        }
    }
}

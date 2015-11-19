package com.example.asynclab;

import java.util.Random;

public final class DataRepository implements Repository<String> {

    static {
        sInstance = new DataRepository();
        sRandom = new Random(245725682568l);
    }

    private static DataRepository sInstance;
    private static Random sRandom;

    private DataRepository() {}

    public static DataRepository get() {
        return sInstance;
    }

    @Override
    public String loadData() {
        try {
            Thread.sleep(randInt(5000, 8000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Data successfully loaded!";
    }

    public int randInt(int min, int max) {
        return sRandom.nextInt((max - min) + 1) + min;
    }
}

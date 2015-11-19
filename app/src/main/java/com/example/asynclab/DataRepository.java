package com.example.asynclab;

public final class DataRepository implements Repository<String> {

    static {
        sInstance = new DataRepository();
    }

    private static DataRepository sInstance;

    private DataRepository() {}

    public static DataRepository get() {
        return sInstance;
    }

    @Override
    public String loadData() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Data successfully loaded!";
    }
}

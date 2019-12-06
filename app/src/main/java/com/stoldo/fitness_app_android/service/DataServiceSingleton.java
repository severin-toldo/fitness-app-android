package com.stoldo.fitness_app_android.service;

public class DataServiceSingleton {
    private static DataService instance = new DataService();

    public static DataService getInstance() {
        if (instance == null) {
            synchronized (DataServiceSingleton.class) {
                if (instance == null) {
                    instance = new DataService();
                }
            }
        }

        return instance;
    }

    private DataServiceSingleton() {
    }
}

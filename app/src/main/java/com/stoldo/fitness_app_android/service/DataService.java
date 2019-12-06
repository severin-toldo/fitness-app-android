package com.stoldo.fitness_app_android.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

class DataService {
    private Thread saveService;
    //private Thread loadService;

    private String path = null;
    private String json = null;

    public DataService(){
        saveService = new Thread() {
            public void run(){
                Save(path, json);
                path = null;
                json = null;
            }
        };

        //loadService = new Thread() {
        //    public void run(){
        //        Load(path);
        //    }
        //};
    }

    public void SaveAsync(String filePath, String json){
        path = filePath;
        this.json = json;
        saveService.start();
    }

    //TODO better
    public String LoadAsync(String filePath){
        return Load(filePath);
    }

    private void Save(String filePath, String json){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private String Load(String filePath){
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}

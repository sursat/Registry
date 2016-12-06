package com.registry.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileWorker {
    public static String getJSONStringFromFile(String fileName) throws FileNotFoundException {
        if (fileName == null || fileName.length() == 0)
            throw new NullPointerException();
        File file = new File(fileName);
        if (!file.exists())
            throw new FileNotFoundException();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line, jsonData = "";
        try {
            while ((line = br.readLine()) != null) {
                jsonData += line;

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonData;
    }
}
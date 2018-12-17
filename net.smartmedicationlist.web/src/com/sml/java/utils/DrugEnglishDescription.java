package com.sml.java.utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DrugEnglishDescription {
    public DrugEnglishDescription() {
    }
    public static String get(String drug) throws IOException{
        drug = drug.trim().toUpperCase();

        URL url = new URL("http://thesite.space/drugNamesWithDescription.csv");
        URLConnection connection = url.openConnection();

        InputStreamReader input = new InputStreamReader(connection.getInputStream());
        BufferedReader buffer = null;
        String line = "";
        String csvSplitBy = ",";

        String ret = "Description not found";


        try {

            buffer = new BufferedReader(input);


            while ((line = buffer.readLine()) != null) {
                String[] entries = line.split(csvSplitBy);
                if (drug.equals(entries[0].trim().toUpperCase())){
                    ret = entries[1];
                }
            }

            return ret;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return ret;

        }
    }
}

package com.comp2042;

import java.io.*;

public class BestScore {
    private static final String FILE_NAME = "src/main/resources/bestscore.txt";


    public static int loadBestScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void saveBestScore(int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(String.valueOf(score));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

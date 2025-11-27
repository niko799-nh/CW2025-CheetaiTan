package com.comp2042.model;

import java.io.*;

/**
 * Handles saving and loading the player's best score.
 * <p>
 * This class stores the highest score in a simple text file so that the game can remember the record even after closing.
 * It is used by the Start Menu to show the best score.
 */
public class BestScore {

    /** The file to save the best score. */
    private static final String FILE_NAME = "src/main/resources/bestscore.txt";

    /**
     * Reads the best score from the file.
     * @return the saved best score, or 0 if the file does not exist or cannot be read.
     */
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

    /**
     * Saves new best score to the file.
     * @param score the score to save as the new best score
     */
    public static void saveBestScore(int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(String.valueOf(score));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

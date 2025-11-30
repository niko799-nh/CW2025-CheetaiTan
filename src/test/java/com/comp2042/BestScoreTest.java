package com.comp2042;

import com.comp2042.model.BestScore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

public class BestScoreTest {

    private static final String FILE_PATH = "src/main/resources/bestscore.txt";
    private File originalFile;
    private File backupFile;

    @BeforeEach
    public void setUp() throws IOException {
        originalFile = new File(FILE_PATH);
        backupFile = new File(FILE_PATH + ".bak");

        if (originalFile.exists()) {
            Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Delete the test file created during testing
        if (originalFile.exists()) {
            originalFile.delete();
        }

        // RESTORE the real high score from backup
        if (backupFile.exists()) {
            Files.copy(backupFile.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            backupFile.delete(); // Remove the backup file
        }
    }

    @Test
    public void testSaveAndLoadScore() {
        BestScore.saveBestScore(500);
        int loadedScore = BestScore.loadBestScore();
        assertEquals(500, loadedScore, "Saved score should match loaded score");
    }

    @Test
    public void testOverwriteScore() {
        BestScore.saveBestScore(100);
        BestScore.saveBestScore(9999);
        int loadedScore = BestScore.loadBestScore();
        assertEquals(9999, loadedScore, "Should load the most recently saved score");
    }
}
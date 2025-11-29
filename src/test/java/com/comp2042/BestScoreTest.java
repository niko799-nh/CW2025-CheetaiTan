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

    // This must match the FILE_NAME inside BestScore.java
    private static final String FILE_PATH = "src/main/resources/bestscore.txt";
    private File originalFile;
    private File backupFile;

    @BeforeEach
    public void setUp() throws IOException {
        originalFile = new File(FILE_PATH);
        backupFile = new File(FILE_PATH + ".bak");

        // 1. If a real high score exists, BACK IT UP
        if (originalFile.exists()) {
            Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @AfterEach
    public void tearDown() throws IOException {
        // 2. Delete the test file created during testing
        if (originalFile.exists()) {
            originalFile.delete();
        }

        // 3. RESTORE the real high score from backup
        if (backupFile.exists()) {
            Files.copy(backupFile.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            backupFile.delete(); // Remove the backup file
        }
    }

    @Test
    public void testSaveAndLoadScore() {
        // Save a fake score
        BestScore.saveBestScore(500);

        // Load it back
        int loadedScore = BestScore.loadBestScore();

        // Check if it matches
        assertEquals(500, loadedScore, "Saved score should match loaded score");
    }

    @Test
    public void testOverwriteScore() {
        // Save first score
        BestScore.saveBestScore(100);

        // Overwrite with higher score
        BestScore.saveBestScore(9999);

        // Verify only the new score remains
        int loadedScore = BestScore.loadBestScore();
        assertEquals(9999, loadedScore, "Should load the most recently saved score");
    }
}
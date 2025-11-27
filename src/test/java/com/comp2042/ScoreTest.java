package com.comp2042;

import com.comp2042.model.Score;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * This test class is used to check the Score class.
 *  It verifies the reset function, repeated updates, score addition, and the starting score value.
 */
public class ScoreTest {
    /**
     * This test ensures that a new Score start with 0.*/
    @Test
    public void testInitialScoreIsZero() {
        Score score = new Score();
        assertEquals(0, score.scoreProperty().get(), "Score should start at 0");
    }

    @Test
    public void testAddScore() {
        Score score = new Score();
        score.add(10);
        assertEquals(10, score.scoreProperty().get(), "Score should increase by 10");
    }
    /**
     * This test verifies that the score is updated correctly based on the total sum after many additions.
     */
    @Test
    public void testAddMultipleTimes() {
        Score score = new Score();
        score.add(5);
        score.add(15);
        assertEquals(20, score.scoreProperty().get(), "Score after multiple additions should be correct");
    }
    /**
     * This test ensures that the score is 0 after game reset.
     */
    @Test
    public void testResetScore() {
        Score score = new Score();
        score.add(30);
        score.reset();
        assertEquals(0, score.scoreProperty().get(), "Score should reset back to 0");
    }
}

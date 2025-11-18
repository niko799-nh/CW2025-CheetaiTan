package com.comp2042;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreTest {

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

    @Test
    public void testAddMultipleTimes() {
        Score score = new Score();
        score.add(5);
        score.add(15);
        assertEquals(20, score.scoreProperty().get(), "Score after multiple additions should be correct");
    }

    @Test
    public void testResetScore() {
        Score score = new Score();
        score.add(30);
        score.reset();
        assertEquals(0, score.scoreProperty().get(), "Score should reset back to 0");
    }
}

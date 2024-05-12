package Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class NumberleModelTest {
    INumberleModel instance;

    @BeforeEach
    void setUp() {
        instance = new NumberleModel();
    }

    @AfterEach
    void tearDown() {
        instance = null;
    }

    /*After entering the expression one times, the attempts will decrease 1
    @
    @ requires expression \in String;
    @ requires isValidInput(expression);
    @ requires flag3 is true;
    @ ensures remainingAttempts == \old(remainingAttempts) - 1;
    @*/
    @org.junit.jupiter.api.Test
    public void testCase1() {
        String expression = "1+2+3=6";
        instance.initialize();
        instance.processInput(expression);
        assertTrue(instance.getRemainingAttempts()==INumberleModel.MAX_ATTEMPTS-1);
    }

    /*After entering the wrong answer six times, the game is judged as GameOver
    @
    @ requires expression \in String;
    @ requires isValidInput(expression);
    @ requires flag3 is true;
    @ ensures remainingAttempts == \old(remainingAttempts) - 1;
    @ ensures gameWon <==> expression.equals(targetNumber);
    @*/
    @org.junit.jupiter.api.Test
    public void testCase2() {
        String expression = "6+6-3=9";
        instance.initialize();
        instance.processInput(expression);
        instance.processInput(expression);
        instance.processInput(expression);
        instance.processInput(expression);
        instance.processInput(expression);
        instance.processInput(expression);
        assertTrue(instance.isGameOver());
    }

    /*After entering the expression one times, the attempts will not change
    @
    @ requires expression \in String;
    @ requires !isValidInput(expression);
    @ requires flag3 is true;
    @ ensures remainingAttempts == \old(remainingAttempts);
    @*/
    @org.junit.jupiter.api.Test
    public void testCase3() {
        String errorExpression = "1111111";
        instance.initialize();
        instance.processInput(errorExpression);
        assertTrue(instance.getRemainingAttempts()==INumberleModel.MAX_ATTEMPTS);
    }
}
package Model;

import java.util.HashMap;

public interface INumberleModel {
    int MAX_ATTEMPTS = 6;

    void initialize();
    void processInput(String input);
    boolean isGameOver();
    boolean isGameWon();
    String getTargetNumber();
    StringBuilder getCurrentGuess();
    int getRemainingAttempts();
    void startNewGame();
    int[] getInputStatus();
    HashMap<Character, Integer> getButtonStatus();
    boolean[] getFlags();
}
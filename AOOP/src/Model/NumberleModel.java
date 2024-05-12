package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class NumberleModel extends Observable implements INumberleModel {
    //The NumberleModel class represents a model for Numberle games and implements the INumberleModel interface.
    private String targetNumber;
    // The target number or equation
    private StringBuilder currentGuess;
    // The current guess
    private int remainingAttempts;
    // The remaining attempts
    private boolean gameWon;
    // Flag indicating if the game is won
    private int[] inputStatus;
    // Array to store input status
    private HashMap<Character,Integer> buttonStatus;
    // Map to store button status
    private boolean[] flags = {true,true,true};
    // Flags array: [whether to read equations from file, whether to print the target number, whether to generate fixed equation]

    public boolean invariant() {
        return (remainingAttempts >= 0 && remainingAttempts <= MAX_ATTEMPTS) &&
                (targetNumber != null) &&
                (inputStatus != null) &&
                (buttonStatus != null);
    }

    /**
     * Generate the target equation by randomly selecting one from a file.
     *
     * @return The randomly selected equation from the file
     */
    private String generateTargetEquation() {
        List<String> equations = new ArrayList<>();
        String fileName = "equations.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                equations.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (flags[2]==true) {
            // If the third element of the flags array is true, return a randomly selected equation from the file; otherwise, return a fixed equation
            Random rand = new Random();
            return equations.get(rand.nextInt(equations.size()));
        }
        else {
            return "1+2+3=6";
        }
    }

    /**
     * Initialize the game.
     */
    @Override
    public void initialize() {
        Random rand = new Random();
        targetNumber = Integer.toString(rand.nextInt(10000000));
        currentGuess = new StringBuilder("       ");
        remainingAttempts = MAX_ATTEMPTS;
        gameWon = false;
        setChanged();
        notifyObservers();
        targetNumber = generateTargetEquation();
        if(flags[1]==true) {
            // If the second element of the flags array is true, print the target number
            System.out.println(targetNumber);
        }

        inputStatus = new int[]{0,0,0,0,0,0,0};
        buttonStatus = new HashMap<>();
        buttonStatus.put('1',0);
        buttonStatus.put('2',0);
        buttonStatus.put('3',0);
        buttonStatus.put('4',0);
        buttonStatus.put('5',0);
        buttonStatus.put('6',0);
        buttonStatus.put('7',0);
        buttonStatus.put('8',0);
        buttonStatus.put('9',0);
        buttonStatus.put('0',0);
        buttonStatus.put('+',0);
        buttonStatus.put('-',0);
        buttonStatus.put('*',0);
        buttonStatus.put('/',0);
        buttonStatus.put('=',0);

        assert invariant();
    }

    /**
     * Process user input.
     *
     * @param input The string input by the user
     */
    @Override
    public void processInput(String input) {
        assert invariant();
        if (input == null || input.length() != 7) {
            setChanged();
            notifyObservers("Invalid Input");
            return;
        }

        if(flags[0]==true){
            // Check for the presence of equals sign, operators, and numbers
            if(!input.contains("=")){
                setChanged();
                notifyObservers("Without Equals");
                System.out.println("Invalid equation! The equation must contains equals sign.");
                return;
            }
            if(!input.contains("+")&&!input.contains("-")&&!input.contains("*")&&!input.contains("/")){
                setChanged();
                notifyObservers("Without Operator");
                System.out.println("Invalid equation! The equation must contains at least one operator.");
                return;
            }
            if(!input.contains("1")&&!input.contains("2")&&!input.contains("3")&&!input.contains("4")&&
                    !input.contains("5")&&!input.contains("6")&&!input.contains("7")&&!input.contains("8")&&
                    !input.contains("9")&&!input.contains("0")){
                setChanged();
                notifyObservers("Without Number");
                System.out.println("Invalid equation! The equation must contains numbers.");
                return;
            }
        }

        remainingAttempts--;
        // Iterate through the input string and update input status and button status based on matching with the target number
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (i < targetNumber.length() && c == targetNumber.charAt(i)) {
                System.out.print("Green ");
                inputStatus[i] = 1;
                buttonStatus.put(c,1);
            } else if (targetNumber.contains(String.valueOf(c))) {
                System.out.print("Orange ");
                inputStatus[i] = 2;
                if (buttonStatus.get(c)!=1) {
                    buttonStatus.put(c,2);
                }
            } else {
                System.out.print("Gray ");
                inputStatus[i] = 3;
                buttonStatus.put(c,3);
            }
        }
        System.out.println();

        if (input.equals(targetNumber)) {
            gameWon = true;
        }
        if (isGameOver()) {
            setChanged();
            notifyObservers(gameWon ? "Game Won" : "Game Over");
        } else {
            setChanged();
            notifyObservers("Try Again");
        }
    }


    @Override
    public boolean isGameOver() {
        return remainingAttempts <= 0 || gameWon;
    }

    @Override
    public boolean isGameWon() {
        return gameWon;
    }

    @Override
    public String getTargetNumber() {
        return targetNumber;
    }

    @Override
    public StringBuilder getCurrentGuess() {
        return currentGuess;
    }

    @Override
    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    @Override
    public void startNewGame() {
        initialize();
    }

    public int[] getInputStatus() {
        return inputStatus;
    }

    public HashMap<Character, Integer> getButtonStatus() {
        return buttonStatus;
    }
    public boolean[] getFlags() {
        return flags;
    }
}

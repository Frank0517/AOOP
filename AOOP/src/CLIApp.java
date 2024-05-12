import Controller.*;
import Model.*;

import java.util.Scanner;


public class CLIApp {
    public static void main(String[] args) {
        INumberleModel model = new NumberleModel();

        try (Scanner scanner = new Scanner(System.in)) {
            model.startNewGame();
            System.out.println("Welcome to Numberle - CLI Version");
            System.out.println("You have " + model.getRemainingAttempts() + " attempts to guess the right equation. Good luck!");

            while (!model.isGameOver()) {
                System.out.println("\nEnter your guess (7 characters long equation): ");
                String input = scanner.nextLine();

                model.processInput(input);

                if (model.isGameOver()) {
                    if (model.isGameWon()) {
                        System.out.println("Congratulations! You've guessed the equation correctly.");
                    } else {
                        System.out.println("Game Over! The correct equation was: " + model.getTargetNumber());
                    }
                } else {
                    System.out.println("Try again. You have " + model.getRemainingAttempts() + " attempts left.");
                }
            }
        }
    }
}

import arc.*;
import java.io.*;
import java.util.*;

public class CPTAzaan {
    Console c;
    Procedure fp;
    char[] gameboard;
    char guessedLetter;
    String input;
    int wrongGuesses;
    boolean playAgain;
    int wins;
    int highscore;

    public Game() throws IOException {
        c = new Console("Guess the Word");
        startNewGame();
    }

    public void startNewGame() throws IOException {
        fp = new Procedure();  // Load new word
        gameboard = new char[fp.getLength()];
        for (int i = 0; i < gameboard.length; i++) {
            gameboard[i] = '_';
        }
        wrongGuesses = 0;
        playAgain = true;
        wins = 0;

        playGame();
    }

    public void playGame() throws IOException {
        while (playAgain) {
            while (wrongGuesses < 4 && !gameWon()) {
                displayGameboard();
                getLetter();
                testLetter();
            }

            if (gameWon()) {
                wins++;
                c.println("🎉 Congratulations! You guessed the word: " + fp.getWord());
                c.println("Wins so far: " + wins);
            } else {
                c.println("❌ Out of guesses. The word was: " + fp.getWord());
            }

            c.print("Play again? (y/n): ");
            String answer = c.readLine();
            if (!answer.equalsIgnoreCase("y")) {
                checkHighscore();
                playAgain = false;
                c.println("Thanks for playing! Total wins: " + wins);
            } else {
                resetGame();
            }
        }
    }

    public void displayGameboard() {
        c.clear();
        c.println("Word to guess: ");
        for (char ch : gameboard) {
            c.print(ch + " ");
        }
        c.println("\nTries remaining: " + (4 - wrongGuesses));
    }

    public boolean gameWon() {
        for (char ch : gameboard) {
            if (ch == '_') return false;
        }
        return true;
    }

    public void getLetter() {
        while (true) {
            c.print("Enter a letter: ");
            input = c.readLine().toLowerCase();

            if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                guessedLetter = input.charAt(0);
                break;
            } else {
                c.println("Invalid input. Please enter one letter.");
            }
        }
    }

    public void testLetter() {
        boolean correct = false;
        for (int i = 0; i < fp.getLength(); i++) {
            if (guessedLetter == fp.getWord().charAt(i)) {
                gameboard[i] = guessedLetter;
                correct = true;
            }
        }

        if (!correct) {
            wrongGuesses++;
            c.println("'" + guessedLetter + "' is not in the word. Tries left: " + (4 - wrongGuesses));
        }
    }

    public void resetGame() throws IOException {
        fp = new Procedure();
        gameboard = new char[fp.getLength()];
        for (int i = 0; i < gameboard.length; i++) {
            gameboard[i] = '_';
        }
        wrongGuesses = 0;
    }

    public void checkHighscore() throws IOException {
        File file = new File("scores.txt");
        if (!file.exists()) {
            PrintWriter writer = new PrintWriter(file);
            writer.println(0);
            writer.close();
        }

        Scanner inputFile = new Scanner(file);
        highscore = Integer.parseInt(inputFile.nextLine());
        inputFile.close();

        if (wins > highscore) {
            c.println("🏆 NEW HIGHSCORE! You won " + wins + " times (previous: " + highscore + ")");
            PrintWriter outputFile = new PrintWriter(file);
            outputFile.println(wins);
            outputFile.close();
        } else {
            c.println("You won " + wins + " time(s). Highscore is still " + highscore + ".");
        }
    }

    public static void main(String[] args) throws IOException {
        new Game();
    }
}

import arc.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class CPTAzaan {
    static arc.Console con = new arc.Console("Guess The Word", 1280, 720);

    public static void main(String[] args) {
        Random rand = new Random();
        System.out.println("[DEBUG] Game started");

        // === Animation Intro ===
        for (int i = 0; i < 6; i++) {
            con.clear();
            if (i % 2 == 0) {
                con.println("\n\n\n\n              G U E S S   T H E   W O R D");
            } else {
                con.println("\n\n\n\n              🎉 LET'S PLAY! 🎉");
            }
            con.sleep(300);
        }

        con.clear();
        con.print("Enter your name: ");
        String strName = con.readLine();
        int intPoints = strName.equalsIgnoreCase("statitan") ? 20 : 10;
        System.out.println("[DEBUG] Player name: " + strName + ", Points: " + intPoints);

        char choice = ' ';
        while (choice != 'q') {
            con.clear();
            con.println("MAIN MENU:");
            con.println("(p) Play Game");
            con.println("(v) View Leaderboard");
            con.println("(a) Add Theme");
            con.println("(h) Help");
            con.println("(s) ??? Secret Option");
            con.println("(q) Quit");
            con.print("Choose: ");
            choice = con.getChar();
            System.out.println("[DEBUG] Menu Choice: " + choice);

            if (choice == 'h') {
                con.clear();
                con.println("HELP:");
                con.println("- Guess the letters in the word.");
                con.println("- Wrong guess = lose a point.");
                con.println("- Secret option and cheat available!");
                con.getChar();
            } else if (choice == 's') {
                con.clear();
                con.println("💡 SECRET OPTION 💡");
                con.println("Why do Java developers wear glasses?");
                con.sleep(1500);
                con.println("Because they don't C#! 😂");
                con.getChar();
            } else if (choice == 'v') {
                con.clear();
                try {
                    TextInputFile leaderboard = new TextInputFile("leaderboard.txt");
                    con.println("LEADERBOARD:");
                    while (!leaderboard.eof()) {
                        con.println(leaderboard.readLine());
                    }
                    leaderboard.close();
                } catch (Exception e) {
                    con.println("Error reading leaderboard.");
                }
                con.getChar();
            } else if (choice == 'a') {
                con.clear();
                con.print("New theme name (ex. food.txt): ");
                String newTheme = con.readLine();
                TextOutputFile newFile = new TextOutputFile(newTheme);

                con.println("Add words one at a time. Type STOP to finish:");
                while (true) {
                    con.print("Enter word: ");
                    String word = con.readLine();
                    if (word.equalsIgnoreCase("STOP")) break;
                    if (!word.trim().isEmpty()) {
                        newFile.print(word + "\n");
                        con.println("[Saved] " + word);
                    }
                }
                newFile.close();

                // Add to themes.txt
                try {
                    boolean alreadyListed = false;
                    TextInputFile checkThemes = new TextInputFile("themes.txt");
                    while (!checkThemes.eof()) {
                        if (newTheme.equalsIgnoreCase(checkThemes.readLine())) {
                            alreadyListed = true;
                            break;
                        }
                    }
                    checkThemes.close();

                    if (!alreadyListed) {
                        TextOutputFile append = new TextOutputFile("themes.txt", true);
                        append.print(newTheme + "\n");
                        append.close();
                    }
                } catch (Exception e) {
                    con.println("Error updating themes.txt");
                }

                con.println("Theme saved!");
                con.getChar();
            } else if (choice == 'p') {
                con.clear();
                ArrayList<String> themes = new ArrayList<>();
                try {
                    TextInputFile tFile = new TextInputFile("themes.txt");
                    while (!tFile.eof()) {
                        String line = tFile.readLine();
                        if (line != null && !line.trim().isEmpty()) themes.add(line);
                    }
                    tFile.close();
                } catch (Exception e) {
                    con.println("No themes file found.");
                }

                if (themes.size() == 0) {
                    con.println("No themes available. Please add one first.");
                    con.getChar();
                    continue;
                }

                con.println("Themes:");
                for (int i = 0; i < themes.size(); i++) {
                    con.println("(" + (i+1) + ") " + themes.get(i));
                }
                con.print("Type theme name: ");
                String fileName = con.readLine();

                File f = new File(fileName);
                if (!f.exists()) {
                    con.println("Theme file not found.");
                    con.getChar();
                    continue;
                }

                ArrayList<String> words = new ArrayList<>();
                try {
                    TextInputFile themeIn = new TextInputFile(fileName);
                    while (!themeIn.eof()) words.add(themeIn.readLine());
                    themeIn.close();
                } catch (Exception e) {
                    con.println("Error reading theme.");
                    con.getChar();
                    continue;
                }

                if (words.size() == 0) {
                    con.println("No words found in this theme.");
                    con.getChar();
                    continue;
                }

                String wordToGuess = words.get(rand.nextInt(words.size()));
                StringBuilder hidden = new StringBuilder("_".repeat(wordToGuess.length()));
                ArrayList<Character> guessed = new ArrayList<>();

                con.setDrawColor(Color.MAGENTA);
                con.drawRect(10, 10, 1260, 700);

                while (intPoints > 0 && hidden.toString().contains("_")) {
                    con.println("Word: " + hidden);
                    con.println("Points: " + intPoints);
                    con.print("Guess a letter: ");
                    char g = con.getChar();

                    if (guessed.contains(g)) {
                        con.println("Already guessed.");
                        continue;
                    }

                    guessed.add(g);
                    boolean found = false;
                    for (int i = 0; i < wordToGuess.length(); i++) {
                        if (wordToGuess.charAt(i) == g) {
                            hidden.setCharAt(i, g);
                            found = true;
                        }
                    }

                    if (!found) {
                        intPoints--;
                        con.println("Wrong!");
                    } else {
                        con.println("Correct!");
                    }
                }

                if (intPoints > 0) {
                    con.println("You guessed it! The word was: " + wordToGuess);
                } else {
                    con.println("You lost! The word was: " + wordToGuess);
                }

                TextOutputFile lb = new TextOutputFile("leaderboard.txt", true);
                lb.print(strName + "," + intPoints + "\n");
                lb.close();

                con.println("Press any key to return to menu.");
                con.getChar();
            }
        }

        con.closeConsole();
    }
}

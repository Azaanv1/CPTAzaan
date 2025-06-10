import arc.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class CPTAzaan {
    public static void main(String[] args) {
        arc.Console con = new arc.Console("Guess The Word", 1280, 720);
        Random rand = new Random();

        System.out.println("[DEBUG] Game started");

        // Display flashing welcome text animation
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
        int intPoints = strName.equals("statitan") ? 15 : 8;  // Cheat feature
        System.out.println("[DEBUG] Player name: " + strName);

        // Show main menu
        char choice = ' ';
        while (choice != 'q') {
            con.clear();
            con.println("MAIN MENU:");
            con.println("(p) Play Game");
            con.println("(v) View Leaderboard");
            con.println("(a) Add Theme");
            con.println("(h) Help");
            con.println("(s) ??? (Secret)");
            con.println("(q) Quit");
            con.print("Enter choice: ");

            choice = con.getChar();
            System.out.println("[DEBUG] Menu choice: " + choice);

            if (choice == 'h') {
                con.clear();
                con.println("HELP MENU:");
                con.println("- Guess letters to reveal the hidden word.");
                con.println("- You lose a point for wrong guesses.");
                con.println("- Win by revealing the full word before points run out!");
                con.println("Press any key to return to menu...");
                con.getChar();
            } else if (choice == 's') {
                con.clear();
                con.println("🎉 SECRET JOKE 🎉");
                con.println("Why did the programmer quit their job?");
                con.sleep(1500);
                con.println("Because they didn't get arrays! 😂");
                con.println("Press any key to go back...");
                con.getChar();
            } else if (choice == 'p') {
                con.clear();
                con.println("Loading themes...");
                
                TextInputFile themeFile = new TextInputFile("themes.txt");
                ArrayList<String> themes = new ArrayList<>();
                while (!themeFile.eof()) {
                    themes.add(themeFile.readLine());
                }
                themeFile.close();

                if (themes.size() == 0) {
                    con.println("No themes found! Please add one first.");
                    con.getChar();
                    continue;
                }

                con.println("Available Themes:");
                for (int i = 0; i < themes.size(); i++) {
                    con.println("(" + (i + 1) + ") " + themes.get(i));
                }

                con.print("Enter theme name exactly: ");
                String chosenTheme = con.readLine();

                File file = new File(chosenTheme);
                if (!file.exists()) {
                    con.println("That theme does not exist.");
                    con.getChar();
                    continue;
                }

                con.setDrawColor(Color.BLUE);
                con.drawRect(20, 20, 1080, 600);
                con.println("Theme loaded: " + chosenTheme);
                con.println("(Gameplay not implemented in this draft.) Press any key.");
                con.getChar();
            } else if (choice == 'a') {
                con.clear();
                con.print("Enter new theme name (e.g., animals.txt): ");
                String newTheme = con.readLine();
                TextOutputFile newFile = new TextOutputFile(newTheme);
                String word;
                con.println("Enter words (type STOP to finish):");
                while (true) {
                    word = con.readLine();
                    if (word.equalsIgnoreCase("STOP")) break;
                    newFile.print(word + "\n");
                }
                newFile.close();
                TextOutputFile appendTheme = new TextOutputFile("themes.txt", true);
                appendTheme.print(newTheme + "\n");
                appendTheme.close();
                con.println("Theme saved. Press any key to return.");
                con.getChar();
            } else if (choice == 'v') {
                con.clear();
                TextInputFile leader = new TextInputFile("leaderboard.txt");
                con.println("LEADERBOARD:");
                while (!leader.eof()) {
                    con.println(leader.readLine());
                }
                leader.close();
                con.println("Press any key to return to menu.");
                con.getChar();
            }
        }

        con.closeConsole();
    }
}

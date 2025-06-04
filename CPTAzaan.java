import arc.*;
import java.util.*;
import java.io.*;

public class GuessTheWord {
public static Console con = new Console("Guess the Word Game");

    public static String[] themeFiles;
    public static String[][] words;
    public static ArrayList<String[]> leaderboard = new ArrayList<>();
		// create master file called themes.txt
		// Inside master you will need a bunch of diffrent themes
		
		int intX = 400;
        int intY = 400;
        int intZ = 400;
			       
			while(intX > 0){
			con.setDrawColor(Color.BLACK);
			con.fillRect(0,0,700,700);
			con.setDrawColor(Color.WHITE);
		// Setting a predifined color
		con.setDrawColor(Color.RED);
		intZ = intZ -9;
		// Setting a custom red, blue, green rgb color 0-255
		con.setDrawColor(new Color(3, 100, 230));
		intY = intY + 5;
		BufferedImage GTW = con.loadImage("GTW.png");
		Font fntTest = con.loadFont("Play Now", 40);
		con.drawImage(GTW, intX, 0);
		intX = intX - 8;
		
		con.setDrawFont(fntTest);
		
	


        while (true) {
            con.clear();
            con.println("=== GUESS THE WORD ===");
            con.println("(P) Play Game");
            con.println("(V) View Leaderboard");
            con.println("(A) Add Theme");
            con.println("(H) Help");
            con.println("(Q) Quit");
            String choice = con.readLine("Enter your choice: ").toUpperCase();

            if (choice.equals("P")) playGame();
            else if (choice.equals("V")) viewLeaderboard();
            else if (choice.equals("A")) addTheme();
            else if (choice.equals("H")) showHelp();
            else if (choice.equals("Q")) break;
            else con.println("Invalid choice. Try again.");
        }
    }

    // Load themes from themes.txt
    public static void loadThemes() throws IOException {
        TextInputFile file = new TextInputFile("themes.txt");
        ArrayList<String> list = new ArrayList<>();
        while (!file.eof()) list.add(file.readLine());
        file.close();
        themeFiles = list.toArray(new String[0]);
    }

    // Play the game
    public static void playGame() throws IOException {
        loadThemes();
        con.clear();
        String playerName = con.readLine("Enter your name: ");
        con.println("Available Themes:");
        for (int i = 0; i < themeFiles.length; i++)
            con.println((i + 1) + ". " + themeFiles[i].replace(".txt", ""));
        int themeChoice = con.readInt("Choose a theme by number: ") - 1;

        if (themeChoice < 0 || themeChoice >= themeFiles.length) {
            con.println("Invalid theme.");
            return;
        }

        loadWords(themeFiles[themeChoice]);
        sortWords();

        int totalPoints = 0;
        for (String[] row : words) {
            int roundPoints = playRound(row[0]);
            totalPoints += roundPoints;
            String again = con.readLine("Play next word? (Y/N): ").toUpperCase();
            if (!again.equals("Y")) break;
        }

        saveToLeaderboard(playerName, totalPoints);
        con.println("Game Over. Your total points: " + totalPoints);
        con.readLine("Press Enter to return to menu...");
    }

    // Load words into 2D array
    public static void loadWords(String themeFile) throws IOException {
        TextInputFile file = new TextInputFile(themeFile);
        ArrayList<String[]> list = new ArrayList<>();
        while (!file.eof()) {
            String word = file.readLine().toUpperCase();
            list.add(new String[]{word, String.valueOf((int)(Math.random() * 100) + 1)});
        }
        file.close();
        words = list.toArray(new String[0][2]);
    }

    // Bubble sort by random number column
    public static void sortWords() {
        for (int i = 0; i < words.length - 1; i++) {
            for (int j = 0; j < words.length - i - 1; j++) {
                if (Integer.parseInt(words[j][1]) > Integer.parseInt(words[j+1][1])) {
                    String[] temp = words[j];
                    words[j] = words[j+1];
                    words[j+1] = temp;
                }
            }
        }
    }

    // One round of the game
    public static int playRound(String word) {
        Set<Character> guessed = new HashSet<>();
        char[] display = new char[word.length()];
        Arrays.fill(display, '_');
        int points = 8;

        while (points > 0 && new String(display).contains("_")) {
            con.clear();
            con.println("Word: " + String.valueOf(display));
            con.println("Guessed: " + guessed);
            con.println("Points: " + points);
            String guess = con.readLine("Guess a letter: ").toUpperCase();
            if (guess.length() != 1 || !Character.isLetter(guess.charAt(0))) {
                con.println("Invalid input.");
                continue;
            }

            char letter = guess.charAt(0);
            guessed.add(letter);
            boolean found = false;

            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == letter) {
                    display[i] = letter;
                    found = true;
                }
            }

            if (!found) {
                points--;
                con.println("Incorrect guess!");
            } else {
                con.println("Correct!");
            }
        }

        if (!new String(display).contains("_")) {
            con.println("You guessed it! The word was: " + word);
        } else {
				con.println("You're out of points. The word was: " + word);
        }

        return points;
    }

  

		

			
			
		
	}
      
      
      
      
      
      
	}

}

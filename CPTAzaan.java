import arc.*;
import java.util.*;

public class CPTAzaan {
    public static void main(String[] args) {
        Console con = new Console(1280,720, "Guess The Word");
       //Ask for player name
       con.println("Enter your name");
       String strName = con.readLine();
       boolean blnRunning = true;
       while (blnRunning){
		   //Main Menu
		   con.println("MAIN MENU");
		   con.println("(p) Play Game");
		   con.println("(v) View Leaderboard");
		   con.println("(a) Add Theme");
		   con.println("(h) Help");
		   con.println("Enter choice: ");
		   char chrChoice = con.readChar();
		   
		   if(chrChoice =='p'){
			   
			  //Load themes 
			  TextInputFile themeFile = new TextInputFile("themes.txt");
			  ArrayList<String> arrThemes = new ArrayList<String>();
			  while(!themeFile.eof()){
				  arrThemes.add(themeFile.readLine());
			  }
			  themeFile.close();
			  con.println("\nAvaliable Themes:");
				 for (int i = 0; i < arrThemes.size(); i++) {
                    con.println((i + 1) + ". " + arrThemes.get(i));
                }
				
				    con.print("Enter theme file name (ex: food.txt): ");
                String strTheme = con.readLine();

                TextInputFile themeWords = new TextInputFile(strTheme);
                int intCount = 0;
                while (!themeWords.eof()) {
                    themeWords.readLine();
                    intCount++;
                }
                themeWords.close();
				
				String[][] arrWords = new String[intCount][2];
				theme Words = new TextInputFile(strTheme);
				for (int i= 0, i < intCount; i++){
					arrWords[i][0] = themeWords.readLine();
					arrWords[i][1] = Integer.toString((int ) (Math.randdom()* 100+1));
				}
				themeWords.close();
				
				//Bubble sort by collum 2 
				for (int i = 0; i < arrWords.length - 1; i++){
					for(int j = 0; i <arrWords.length - i 1; j++){
						if(integer.parseInt(arrWords[j][1])<Integer.pareInt(arrWords
						


        
    }
}



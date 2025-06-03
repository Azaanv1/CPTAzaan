import arc.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Font;
public class CPTAzaan {
    public static void main(String[] args) {
		Console con = new Console();
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
		
		con.setDrawColor(new Color(200, 30, 30));
		con.drawString("Quit!", 0,0);
		// repainting ensures that all the drawing commands above appear
		con.repaint();
		con.sleep(33);
		
		

			
			
		
	}
      
      
      
      
      
      
	}
}

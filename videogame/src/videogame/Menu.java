package videogame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {
	
	
	public static Rectangle playButton = new Rectangle(GamePanel.WIDTH / 4 + 250, 150, 100, 50); 
	public static Rectangle multiplayButton = new Rectangle(GamePanel.WIDTH / 4 + 250, 250, 100, 50) ;
	public static Rectangle quitButton = new Rectangle(GamePanel.WIDTH / 4 + 250, 350, 100, 50) ; 
	
	public static void render (Graphics g) {
		Graphics2D g2d = (Graphics2D) g; 
	
		
		Font fnt0 = new Font("arial", Font.BOLD, 50); 
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("Snake Game", GamePanel.SCREEN_WIDTH / 4, 100);
		
		Font fnt1 = new Font("arial", Font.BOLD, 30); 
		g.setFont(fnt1);
		g.drawString("Play", playButton.x + 20, playButton.y + 33);
		g2d.draw(playButton);
		
		Font fnt2 = new Font("arial", Font.BOLD, 30); 
		g.setFont(fnt2);
		g.drawString("Co-op", multiplayButton.x + 8, multiplayButton.y + 33);
		g2d.draw(multiplayButton);
		
		Font fnt3 = new Font("arial", Font.BOLD, 30); 
		g.setFont(fnt3);
		g.drawString("Quit", quitButton.x + 20, quitButton.y + 33);
		g2d.draw(quitButton);
		
		Font fnt4 = new Font("arial", Font.BOLD, 10); 
		g.setFont(fnt4);
		g.drawString("Ver 1.0.0", 0, 10);
	}

}

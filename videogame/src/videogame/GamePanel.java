package videogame;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;


import videogame.Multiplayer.MultiplayerKeyAdapter;
import videogame.Multiplayer.STATE;

import java.util.ArrayList;
import java.util.Random; 

public class GamePanel extends JPanel implements ActionListener {
	
	static final int SCREEN_WIDTH = 600 ; 
	static final int SCREEN_HEIGHT = 600 ; 
	static final int UNIT_SIZE = 25 ; 
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT) / UNIT_SIZE ; 
	static int DELAY = 75  ;
	final int x[] = new int [GAME_UNITS] ; 
	final int y[] = new int [GAME_UNITS]  ;
	int bodyParts = 6; 
	int applesEaten; 
	int appleX; 
	int appleY; 
	int poisonX;
	int poisonY; 
	int poison2X; 
	int poison2Y; 
	int poison3X; 
	int poison3Y; 
	int poison4X; 
	int poison4Y; 
	int poison5X ;
	int poison5Y ; 
	int level = 1 ;
	char direction = 'R' ; 
	boolean running = false; 
	Timer timer; 
	Random random; 
	Random random2; 
	Random random3; 
	Random random4;
	Random random5; 
	Random random6; 
	Menu menu; 
	
	
	// Second Player or Multiplayer
	final int x1[] = new int [GAME_UNITS] ;
	final int y1[] = new int [GAME_UNITS] ;
	final int x2[] = new int [GAME_UNITS] ;
	final int y2[] =  new int [GAME_UNITS] ;
	int bodyParts1 = 6;
	int applesEaten1;
	int bodyParts2 = 6;
	int applesEaten2;
	char direction1 = 'R';
	char direction2 = 'R';
	JFrame frame; 
	MultiplayerKeyAdapter keyAdapter = new MultiplayerKeyAdapter();
	String winner;
	
	
	
	
	public static enum STATE {
		MENU,
		GAME,
		MULTIPLAYER
	}; 
	
	public static STATE state = STATE.MENU; 
	
	
	
	
	GamePanel() {
		random = new Random() ; 
	    random2 = new Random() ;
	    random3 = new Random(); 
	    random4 = new Random(); 
	    random5 = new Random();
	    random6 = new Random(); 
		menu = new Menu(); 
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		this.addKeyListener(new MultiplayerKeyAdapter());
		this.addMouseListener(new MouseInput()) ;
		startGame();
	//	mu.setFile(fileURL); 
	//	mu.play();
		
		
		
	}
	
	public void startGame()  {
		
		newApple();  
		running = true; 
		timer = new Timer(DELAY, this); 
		timer.start();
		}
		
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (state == STATE.GAME)
		draw(g); 
		if (state == STATE.MULTIPLAYER)
			draw2(g); 
		if (state == STATE.MENU)
			Menu.render(g); 
	}
	
	public void draw(Graphics g) {
		
		if (running) {
		for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
			g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
			g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
		}
		
		
		g.setColor(Color.red);
		g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE) ;
		
		if (level == 2) {
		g.setColor(Color.green);
		g.fillOval(poisonX, poisonY, UNIT_SIZE, UNIT_SIZE) ; }
		
		if ( level == 3 ) {
			g.setColor(Color.green);
			g.fillOval(poisonX, poisonY, UNIT_SIZE, UNIT_SIZE) ;
			
			g.setColor(Color.green);
			g.fillOval(poison2X, poison2Y, UNIT_SIZE, UNIT_SIZE) ;
			
			g.setColor(Color.green);
			g.fillOval(poison3X, poison3Y, UNIT_SIZE, UNIT_SIZE) ;
			
			g.setColor(Color.green);
			g.fillOval(poison4X, poison4Y, UNIT_SIZE, UNIT_SIZE) ;
			
			g.setColor(Color.green);
			g.fillOval(poison5X, poison5Y, UNIT_SIZE, UNIT_SIZE) ;
		}
		
	//	if (level == 4) {
	//		for(int i=0; i<bodyParts2;i++) {
		//		if(i == 0) {
		//			g.setColor(Color.green);
		//			g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
		//		}
		//		else {
		//			g.setColor(new Color(45, 180, 0));
		//			g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255) ));
		//			g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
		//		}
		//	}
			
	//	}
		
		
		for(int i=0; i<bodyParts;i++) {
			if(i == 0) {
				g.setColor(Color.green);
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
			else {
				g.setColor(new Color(45, 180, 0));
				g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255) ));
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
		}
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.BOLD, 25));
		FontMetrics metrics = getFontMetrics(g.getFont()); 
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/1, g.getFont().getSize());
		
	
		} else {
			gameOver(g) ;
		}
		
		
	}
	
public void draw2(Graphics g) {
		
		if (running) {
		for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
			g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
			g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
		}
		
		
		g.setColor(Color.red);
		g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE) ;
		
		
		
		for(int i=0; i<bodyParts1;i++) {
			if(i == 0) {
				g.setColor(Color.green);
				g.fillRect(x1[i], y1[i], UNIT_SIZE, UNIT_SIZE);
			}
			else {
				g.setColor(new Color(45, 180, 0));
				g.fillRect(x1[i], y1[i], UNIT_SIZE, UNIT_SIZE);
			}
		}
		
		for(int i=0; i<bodyParts2;i++) {
			if (i == 0) {
				g.setColor(Color.orange);
				g.fillRect(x2[i], y2[i], UNIT_SIZE, UNIT_SIZE);
			} else {
				g.setColor(new Color(228,113,71));
				g.fillRect(x2[i], y2[i], UNIT_SIZE, UNIT_SIZE);
			
			}
		}
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.BOLD, 25));
		FontMetrics metrics = getFontMetrics(g.getFont()); 
		g.drawString("Score: " + applesEaten1, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten1))/1, g.getFont().getSize());
		g.drawString("Score: " + applesEaten2, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten2))/50, g.getFont().getSize());
		
	
		} else {
			gameOver2(g) ;
		}
		
		
	}



	

	
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE; 
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE; 
	}
	
	 public void newPoison() {
		poisonX = random2.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE; 
		poisonY = random2.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE; 
	 }
	 
	 public void newPoison2() {
		 poison2X = random3.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE; 
		 poison2Y = random3.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE; 
	 }
	 
	 public void newPoison3() {
		 poison3X = random4.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE; 
		 poison3Y = random4.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE; 
	 }
	 
	 public void newPoison4() {
		 poison4X = random5.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE; 
		 poison4Y = random5.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE; 
	 }
	 
	 public void newPoison5() {
		 poison5X = random6.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE; 
		 poison5Y = random6.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE; 
	 }
	
	public void move() {
		
		for(int i=bodyParts; i>0; i--) {
			x[i] = x[i-1]; 
			y[i] = y[i-1]; 
		}
		
		switch(direction) {
		case'U' : 
			y[0] = y[0] - UNIT_SIZE; 
			break;
		case'D' : 
			y[0] = y[0] + UNIT_SIZE; 
			break; 
		case'L' : 
			x[0] = x[0] - UNIT_SIZE; 
			break; 
		case'R' : 
			x[0] = x[0] + UNIT_SIZE; 
			break; 
		}
		
	}
	
	public void move2() {
		for(int i=bodyParts1; i>0; i--) {
			x1[i] = x1[i-1]; 
			y1[i] = y1[i-1]; 
		}
		
		for(int i=bodyParts2; i>0; i--) {
			x2[i] = x2[i-1]; 
			y2[i] = y2[i-1]; 
		}
		
		switch(direction1) {
		case'U' : 
			y1[0] = y1[0] - UNIT_SIZE; 
			break;
		case'D' : 
			y1[0] = y1[0] + UNIT_SIZE; 
			break; 
		case'L' : 
			x1[0] = x1[0] - UNIT_SIZE; 
			break; 
		case'R' : 
			x1[0] = x1[0] + UNIT_SIZE; 
			break; 
		}
		
		switch(direction2) {
		case'U' : 
			y2[0] = y2[0] - UNIT_SIZE; 
			break;
		case'D' : 
			y2[0] = y2[0] + UNIT_SIZE; 
			break; 
		case'L' : 
			x2[0] = x2[0] - UNIT_SIZE; 
			break; 
		case'R' : 
			x2[0] = x2[0] + UNIT_SIZE; 
			break; 
		}
		
	}
	
	
	
	public void checkApple() {
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++; 
			applesEaten++; 
			newApple(); 
			
		}
			
		
		
	}
	
	public void checkApple2() {
		if((x1[0] == appleX) && (y1[0] == appleY)) {
			bodyParts1++; 
			applesEaten1++; 
			newApple(); 
			
		}
		if((x2[0] == appleX) && (y2[0] == appleY)) {
			bodyParts2++; 
			applesEaten2++; 
			newApple(); 
			
		}	
		
		
	}
	
	
	public void checkPoison() {
		if((x[0] == poisonX) && (y[0] == poisonY)) {
			bodyParts--; 
		    applesEaten--; 
			newPoison(); 
		}
			if((x[0] == poison2X) && (y[0] == poison2Y)) {
				bodyParts--; 
			    applesEaten--; 
			newPoison2(); }
			
			if((x[0] == poison3X) && (y[0] == poison3Y)) {
				bodyParts--; 
			    applesEaten--; 
			newPoison3(); }
			
			if((x[0] == poison4X) && (y[0] == poison4Y)) {
				bodyParts--; 
			    applesEaten--; 
			newPoison4(); }
			
			if((x[0] == poison5X) && (y[0] == poison5Y)) {
				bodyParts--; 
			    applesEaten--; 
			newPoison5(); }
		
			
		}
		
	
	
	public void checkCollisions() {
		// checks if head collides with body
		for(int i = bodyParts; i>0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				running = false; 
			}
		}
		// check if head touches left border 
		if(x[0] < 0) {
			running = false; 
		}
		//check if head touches right border
		if(x[0] > SCREEN_WIDTH) {
			running = false; 
		}
		
		// check if head touches top border 
		if(y[0] < 0) {
			running = false; 
		}
		
		// check if head touches bottom border
		if(y[0] > SCREEN_WIDTH) {
			running = false; 
		}
		
		if(!running) {
			timer.stop(); 
		}
	
	}
	
	public void checkCollisions2() {
		// checks if head collides with body
		for (int i = bodyParts1; i > 0; i--) {
			if ((x1[0] == x1[i]) && (y1[0] == y1[i])) {
				running = false;
				winner = "P2";
			}
		}
		if (x1[0] < 0) {
			running = false;
			winner = "P2";
		}
		if (x1[0] > SCREEN_WIDTH) {
			running = false;
			winner = "P2";
		}
		if (y1[0] < 0) {
			running = false;
			winner = "P2";
		}
		if (y1[0] > SCREEN_HEIGHT) {
			running = false;
			winner = "P2";
		}

		for (int i = bodyParts2; i > 0; i--) {
			if ((x2[0] == x2[i]) && (y2[0] == y2[i])) {
				running = false;
				winner = "P1";
			}
		}
		if (x2[0] < 0) {
			running = false;
			winner = "P1";
		}
		if (x2[0] > SCREEN_WIDTH) {
			running = false;
			winner = "P1";
		}
		if (y2[0] < 0) {
			running = false;
			winner = "P1";
		}
		if (y2[0] > SCREEN_HEIGHT) {
			running = false;
			winner = "P1";
		}

		for (int i = bodyParts2; i > 0; i--) {
			if ((x1[0] == x2[i]) && (y1[0] == y2[i])) {
				running = false;
				winner = "P2";
			}
		}
		for (int i = bodyParts1; i > 0; i--) {
			if ((x2[0] == x1[i]) && (y2[0] == y1[i])) {
				running = false;
				winner = "P1";
			}
		}

		if (!running) {
			timer.stop();
		}
		
	

	}
	
	
	
	// Adding more levels / Challenges. 
	
	public void checkEnd() {
		// Level 1 (Easy) 
		if (level == 1) {
			// 5
		if (applesEaten == 30) {
			level++;  
			JOptionPane.showMessageDialog(null, "You completed Level 1! Let's move onto the next round.");
			applesEaten = 0; 
			bodyParts = 6; 
			DELAY += 50 ; 
			newPoison(); 
			
		}
	}
		// Level 2
		if (level == 2) {
			// 25
			if (applesEaten == 30) {
				level++;  
				JOptionPane.showMessageDialog(null, "You completed Level 2! Let's move onto the next round.");
				applesEaten = 0; 
				bodyParts = 6; 
				DELAY += 100; 
				newPoison(); 
				newPoison2(); 
				newPoison3(); 
				newPoison4(); 
				newPoison5(); 
				
			}
		}
		
		// Level 3
		if (level == 3) {
			// 40
				if (applesEaten == 35) {
					level++;  
					JOptionPane.showMessageDialog(null, "Good job! You completed the first version of the game.");
					applesEaten = 0;
					System.exit(1);
					}
			}
	}
		
		/**
		// Level 4
		if (level == 4) {
			// 50
			if (applesEaten == 2) {
				level++;  
				JOptionPane.showMessageDialog(null, "You completed Level 4! Let's move onto the last round.");
				applesEaten = 0;
				bodyParts = 6; 
				DELAY += 300; 
				}
		}
		
		// Level 5
		if (level == 5) {
			// 80
			if (applesEaten == 5) {
				level++;  
				JOptionPane.showMessageDialog(null, "Good job! You completed the game.");
				applesEaten = 0;
				timer.stop();
				
			}
		}
		
		}
	*/ 
	
	
	
	
	public void gameOver(Graphics g) {
		
		
		// Score
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.BOLD, 75));
		FontMetrics metrics1 = getFontMetrics(g.getFont()); 
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
		// Game Over Text
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont()); 
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/4);
		
		// Restart Button
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		FontMetrics metrics3 = getFontMetrics(g.getFont()); 
		g.drawString("Press 'r' for RESTART",(SCREEN_WIDTH - metrics3.stringWidth("Press 'r' for RESTART"))/2, SCREEN_HEIGHT/2);
		
		
	}
	
public void gameOver2(Graphics g) {
		
		// Score
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.BOLD, 75));
		FontMetrics metrics1 = getFontMetrics(g.getFont()); 
		g.drawString("Score (P1): " + applesEaten1, (SCREEN_WIDTH - metrics1.stringWidth("Score (P1): " + applesEaten1))/2, g.getFont().getSize());
		// Game Over Text
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont()); 
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.BOLD, 75));
		FontMetrics metrics3 = getFontMetrics(g.getFont());
		g.drawString("Score (P2): " + applesEaten2, (SCREEN_WIDTH - metrics3.stringWidth("Score (P2): " + applesEaten2))/2, SCREEN_HEIGHT/4);
		
		// Restart Button
				g.setColor(Color.red);
				g.setFont(new Font("Arial", Font.BOLD, 30));
				FontMetrics metrics4 = getFontMetrics(g.getFont()); 
				g.drawString("Press 'p' for RESTART",(SCREEN_WIDTH - metrics4.stringWidth("Press 'p' for RESTART"))/2, 450);
				
}







public class MyKeyAdapter extends KeyAdapter{
	@Override
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT : 
			if (direction != 'R') {
				direction = 'L' ; 
			}
			break; 
			
		case KeyEvent.VK_RIGHT : 
			if (direction != 'L') {
				direction = 'R' ; 
			}
			break; 
			
		case KeyEvent.VK_UP : 
			if (direction != 'D') {
				direction = 'U' ; 
			}
			break; 
			
		case KeyEvent.VK_DOWN : 
			if (direction != 'U') {
				direction = 'D' ; 
			}
			break; 
		}
		if(!running && state == STATE.GAME && e.getKeyCode() == KeyEvent.VK_R) {
		
		menu = new Menu(); 
		level = 1; 
        bodyParts = 6; 
        applesEaten = 0; 
        newApple();  
		running = true; 
		timer.start();
		x[0] = 300; 
		y[0] = 300; 
		repaint(); 
		
		
		}
		
		
	}
		}
		




public class MultiplayerKeyAdapter extends KeyAdapter{
	@Override
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT : 
			if (direction2 != 'R') {
				direction2 = 'L' ; 
			}
			break; 
			
		case KeyEvent.VK_RIGHT : 
			if (direction2 != 'L') {
				direction2 = 'R' ; 
			}
			break; 
			
		case KeyEvent.VK_UP : 
			if (direction2 != 'D') {
				direction2 = 'U' ; 
			}
			break; 
			
		case KeyEvent.VK_DOWN : 
			if (direction2 != 'U') {
				direction2 = 'D' ; 
			}
			break; 
			
		case KeyEvent.VK_W:
			if (direction1 != 'D') {
				direction1 = 'U';
			}
			break;
		case KeyEvent.VK_D:
			if (direction1 != 'L') {
				direction1 = 'R';
			}
			break;
		case KeyEvent.VK_A:
			if (direction1 != 'R') {
				direction1 = 'L';
			}
			break;
		case KeyEvent.VK_S:
			if (direction1 != 'U') {
				direction1 = 'D';
			}
			break;
		}
		
		if(!running && state == STATE.MULTIPLAYER && e.getKeyCode() == KeyEvent.VK_P) {
			
			menu = new Menu(); 
	        bodyParts1 = 6; 
	        bodyParts2 = 6; 
	        applesEaten1 = 0; 
	        applesEaten2 = 0; 
	        newApple();  
			running = true; 
			timer.start();
			x1[0] = 200; 
			y1[0] = 200; 
			x2[0] = 300; 
			y2[0] = 300; 
			repaint(); 
			
			
			}

}
}

// URL fileURL; 
// Music mu = new Music(); 

// public void Sound() {
//	fileURL = getClass().getResource("/MusicPlayer/SnakeOST.wav");  }



@Override
public void actionPerformed(ActionEvent e) {
	if (running && state == STATE.GAME) {
		move() ; 
	//	move2(); 
		checkApple() ; 
		checkPoison(); 
		checkCollisions();
		checkEnd(); 
	}
	
	if (running && state == STATE.MULTIPLAYER) {
		move2() ; 
		checkApple2() ;  
		checkCollisions2();
	} 
	
	
	
	repaint() ;
}
}




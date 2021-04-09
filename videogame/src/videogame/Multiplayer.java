package videogame;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.Random; 


import javax.swing.JPanel; 


public class Multiplayer extends JPanel implements ActionListener {
	
	final int SCREEN_WIDTH = 600;
	final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT) / UNIT_SIZE ; 
	static final int DELAY = 75;
	final int x1[] = new int [GAME_UNITS] ;
	final int y1[] = new int [GAME_UNITS] ;
	final int x2[] = new int [GAME_UNITS] ;
	final int y2[] =  new int [GAME_UNITS] ;
	int bodyParts1 = 6;
	int applesEaten1;
	int bodyParts2 = 6;
	int applesEaten2;
	int appleX;
	int appleY;
	char direction1 = 'R';
	char direction2 = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	JFrame frame;
	Menu menu;  
	MultiplayerKeyAdapter keyAdapter = new MultiplayerKeyAdapter();
	String winner;
	
	public static enum STATE {
		MENU,
		GAME, 
		MULTIPLAYER
	}; 
	
	public static STATE state = STATE.MENU;  
	
	
	
	Multiplayer() {
		random = new Random() ; 
		menu = new Menu(); 
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MultiplayerKeyAdapter());
		this.addMouseListener(new MouseInput()) ;
		startGame(); 
		
	}
	
	public void startGame() {
		
		newApple(); 
		running = true; 
		timer = new Timer(DELAY, this); 
		timer.start();
		}
		
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (state == STATE.MULTIPLAYER)
		draw(g); 
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
				g.setColor(new Color(45,180,0));
				g.fillRect(x2[i], y2[i], UNIT_SIZE, UNIT_SIZE);
			
			}
		}
		
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.BOLD, 25));
		FontMetrics metrics = getFontMetrics(g.getFont()); 
		g.drawString("Score: " + applesEaten1, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten1))/1, g.getFont().getSize());
		g.drawString("Score: " + applesEaten2, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten2))/2, g.getFont().getSize());
		
	
		} else {
			gameOver(g) ;
		}
		
		
	}

	

	
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE; 
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE; 
	}
	
	
	public void move() {
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
		
	
	
	public void checkCollisions() {
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
	
	public void gameOver(Graphics g) {
		
		// Score
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.BOLD, 75));
		FontMetrics metrics1 = getFontMetrics(g.getFont()); 
		g.drawString("Score: " + applesEaten1, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten1))/2, g.getFont().getSize());
		g.drawString("Score: " + applesEaten2, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten2))/2, g.getFont().getSize());
		// Game Over Text
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont()); 
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		
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
		
	}
}



@Override
public void actionPerformed(ActionEvent e) {
	if (running && state == STATE.MULTIPLAYER) {
		move() ; 
		checkApple() ;  
		checkCollisions();
	} 
	
	repaint() ;
}
}



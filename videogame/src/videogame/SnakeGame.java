package videogame;

import java.io.File;
import java.io.IOException;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class SnakeGame {
	
	
public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException, InterruptedException {
		
		new GameFrame()  ;
		
	
		  
			File file = new File("MusicPlayer/SnakeOST.wav"); 
				 
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(file); 
			
				Clip clip = AudioSystem.getClip(); 
				clip.open(audioStream); 
				
				clip.start();
			  clip.loop(Clip.LOOP_CONTINUOUSLY) ; 
			
			  
			
		
		
	
		
		
}}




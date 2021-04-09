package videogame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		int mx = e.getX(); 
		int my = e.getY(); 
		
		
		if (GamePanel.state == GamePanel.STATE.MENU) {
		// Play Button
		if (mx >= GamePanel.WIDTH / 4 + 250 && mx <= GamePanel.WIDTH / 4 + 350 ) 
		{
			if (my >= 150 && my <= 200 )
			{
			// Pressed Play Button
				GamePanel.state = GamePanel.STATE.GAME; 
				
				
			}
		}
		
		// Multiplayer Button
		if (mx >= GamePanel.WIDTH / 4 + 250 && mx <= GamePanel.WIDTH / 4 + 350) {
			
			if (my >= 250 && my <= 300)
			{
				
				// Pressed Multiplayer Button
				GamePanel.state = GamePanel.STATE.MULTIPLAYER;  
			}
		}
		
		// Quit Button
		if (mx >= GamePanel.WIDTH / 4 + 250 && mx <= GamePanel.WIDTH / 4 + 350) {

			if (my >= 350 && my <= 400)
			{
				// Pressed Quit Button
				System.exit(1);
			}
		}
		}
		
	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

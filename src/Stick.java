import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Stick extends Block implements Constants {
	//represents top left coordinate, long skinny one
	
	public Stick(Game g) {
		posx = g.coordY+WIDE/2+BLOCK_SIZE; //blocks usually start in the middle of the game's screen
		posy = g.coordX;
		coordinates.add(new Point(posx, posy-BLOCK_SIZE));
		//starts out in a vertical line
		coordinates.add(new Point(posx, posy));
		coordinates.add(new Point(posx, posy+BLOCK_SIZE));
		coordinates.add(new Point(posx, posy+2*BLOCK_SIZE));
//		coordinates.add(new Point(posx, posy+3*BLOCK_SIZE));
	}
	
	public void rotateCCW() {
		//rotates counter clockwise
	}
	
	public void rotateCW() {
		//rotates clockwise
	}
	
	public void shiftLeft() {
		//moves to the left once
	}
	
	public void shiftRight() {
		//moves to the right once
	}
	
	public void hardDrop() {
		//immediately drops down onto the game screen
	}
	
	public void draw(Graphics g) {
		//draw the element here
		g.setColor(Color.BLUE);
		for (int i = 0; i < coordinates.size(); i++) {
			g.fillRect(coordinates.get(i).x, coordinates.get(i).y, BLOCK_SIZE-1, BLOCK_SIZE-1);
		}
	}
}

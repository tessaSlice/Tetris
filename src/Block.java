import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public abstract class Block {
	//represents top left coordinate
	public int posx; 
	public int posy;
	public ArrayList<Point> coordinates;
	
	public Block() {
		posx = 0;
		posy = 0;
		//for now... blocks usually start in the middle of the game's width screen
		coordinates = new ArrayList<Point>();
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
	}
	
}

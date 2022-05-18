import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public abstract class Block implements Constants {
	//represents top left coordinate
	public int posx; 
	public int posy;
	public ArrayList<Point> coordinates;
	
	public Block(Game g) {
		posx = g.coordX+WIDE/2-BLOCK_SIZE/2; //blocks usually start in the middle of the game's screen
		posy = g.coordY;
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

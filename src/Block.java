import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Block implements Constants {
	//represents top left coordinate
	public int posx; 
	public int posy;
	public ArrayList<Point> coordinates;
	public Point rotationPoint;
	
	public Block(Game g) {
		posx = g.coordX+WIDE/2-BLOCK_SIZE/2; //blocks usually start in the middle of the game's screen
		posy = g.coordY;
		//for now... blocks usually start in the middle of the game's width screen
		coordinates = new ArrayList<Point>();
	}
	
	public void rotateCCW(Game game) {
		//rotates counter clockwise
        // look into rotation matrix!!!
		for (int i = 0; i < coordinates.size(); i++) {
			int x = coordinates.get(i).x + BLOCK_SIZE/2; //shift them because they're currently on the top left corner
			int y = coordinates.get(i).y + BLOCK_SIZE/2; //shift them because they're currently on the top left corner
			int newX = x - (int)rotationPoint.getX();
			int newY = y - (int)rotationPoint.getY();
			int newPtX = -newY;
			int newPtY = newX;
			newPtX += (int)rotationPoint.getX();
			newPtY += (int)rotationPoint.getY();
			//will have to implement checks first to see if it works...
			newPtX -= BLOCK_SIZE/2;
			newPtY -= BLOCK_SIZE/2;
			coordinates.set(i, new Point(newPtX, newPtY));
		}
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

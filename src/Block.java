import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Block implements Constants {
	//represents top left coordinate
	public int posx; 
	public int posy;
	public ArrayList<Point> coordinates;
	public Point rotationPoint;
	public boolean aliveBlock;
	public boolean finished;
	
	public Block(Game g) {
		posx = g.coordX+WIDE/2; //blocks usually start in the middle of the game's screen
		posy = g.coordY-7*BLOCK_SIZE;
		coordinates = new ArrayList<Point>();
		aliveBlock = false;
		finished = false;
	}
	
	public boolean rotateCCW(Game game) {
		//rotates counter clockwise
        // look into rotation matrix!!!
		for (int i = 0; i < coordinates.size(); i++) {
			if (intersectsOtherBlock(game, 3)) return false; //check to see if it rotates
		}
		// if it doesn't intersect... then actually reset it
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
		return true;
	}
	
	public void rotateCW() {
		//rotates clockwise
	}
	
	public boolean shiftLeft(Game game) {
		//moves to the left once
		int[] tempXs = new int[coordinates.size()];
		for (int i = 0; i < coordinates.size(); i++) {
			tempXs[i] = (int) (coordinates.get(i).getX() - BLOCK_SIZE);
			if (tempXs[i] < game.coordX) return false; //just stops the operation from happening
			//also check if any other blocks are also inhabiting this one as well
			if (intersectsOtherBlock(game, 1)) return false;
		}
		int tempY;
		for (int i = 0; i < coordinates.size(); i++) {
			tempY = (int) coordinates.get(i).getY();
			coordinates.set(i, new Point(tempXs[i], tempY));
		}
		//update the rotationPoint
		int oldX = (int) rotationPoint.getX();
		rotationPoint = new Point(oldX - BLOCK_SIZE, (int) rotationPoint.getY());
		return true;
	}
	
	public boolean shiftRight(Game game) {
		//moves to the right once
		int[] tempXs = new int[coordinates.size()];
		for (int i = 0; i < coordinates.size(); i++) {
			tempXs[i] = (int) (coordinates.get(i).getX() + BLOCK_SIZE);
			if (tempXs[i] > game.coordX + WIDE - BLOCK_SIZE) return false; //just stops the operation from happening
			if (intersectsOtherBlock(game, 2)) return false;
		}
		int tempY;
		for (int i = 0; i < coordinates.size(); i++) {
			tempY = (int) coordinates.get(i).getY();
			coordinates.set(i, new Point(tempXs[i], tempY));
		}
		//update the rotationPoint
		int oldX = (int) rotationPoint.getX();
		rotationPoint = new Point(oldX + BLOCK_SIZE, (int) rotationPoint.getY());
		return true;
	}
	
	public boolean shiftDown(Game game) {
		//moves down once
		//check to see if it's already on the bottom most layer first
		//also check to see if it can't move down anymore... then return a boolean probably or else it will never set it to finished or set the new value, etc.
		int[] tempYs = new int[coordinates.size()];
		for (int i = 0; i < coordinates.size(); i++) {
			tempYs[i] = (int) (coordinates.get(i).getY() + BLOCK_SIZE);
			if (tempYs[i] > game.coordY + HIGH - BLOCK_SIZE) {
				finished = true;
				return false; //just stops the operation from happening
			}; 
			if (intersectsOtherBlock(game, 0)) return false;
		}
		for (int i = 0; i < coordinates.size(); i++) {
			coordinates.set(i, new Point(coordinates.get(i).x, tempYs[i]));
		}
		//update the rotationPoint
		int oldY = (int) rotationPoint.getY();
		rotationPoint = new Point((int) rotationPoint.getX(), (int) oldY + BLOCK_SIZE);
		return true;
	}
	
	public void hardDrop() {
		//immediately drops down onto the game screen
	}
	
	public void draw(Graphics g) {
		//draw the element here
	}
	
	public boolean intersectsOtherBlock(Game game, int condition) {
		//condition == 1 for left, condition == 2 for right, condition == 0 for down, condition == 3 for CCW
		boolean doesIntersect = false;
		for (int i = 0; i < game.blocks.size(); i++) {
			//for every coordinate for every block... compare it with the current block
			if (!this.equals(game.blocks.get(i))) {
				for (int j = 0; j < game.blocks.get(i).coordinates.size(); j++) {
					for (int k = 0; k < this.coordinates.size(); k++) {
						if (condition == 1) {
							//for checking leftwards
							if (game.blocks.get(i).coordinates.get(j).getX() == (this.coordinates.get(k).getX()) - BLOCK_SIZE && game.blocks.get(i).coordinates.get(j).getY() == (this.coordinates.get(k).getY())) doesIntersect = true; 
						} else if (condition == 2) {
							if (game.blocks.get(i).coordinates.get(j).getX() == (this.coordinates.get(k).getX()) + BLOCK_SIZE && game.blocks.get(i).coordinates.get(j).getY() == (this.coordinates.get(k).getY())) doesIntersect = true; 
						} else if (condition == 0 ) {
							// it's just checking downwards
							if (game.blocks.get(i).coordinates.get(j).getX() == (this.coordinates.get(k).getX()) && game.blocks.get(i).coordinates.get(j).getY() == (this.coordinates.get(k).getY() + BLOCK_SIZE)) doesIntersect = true; 
						} else { 
							//checks for CCW rotation
							int x = coordinates.get(k).x + BLOCK_SIZE/2; //shift them because they're currently on the top left corner
							int y = coordinates.get(k).y + BLOCK_SIZE/2; //shift them because they're currently on the top left corner
							int newX = x - (int)rotationPoint.getX();
							int newY = y - (int)rotationPoint.getY();
							int newPtX = -newY;
							int newPtY = newX;
							newPtX += (int)rotationPoint.getX();
							newPtY += (int)rotationPoint.getY();
							//will have to implement checks first to see if it works...
							newPtX -= BLOCK_SIZE/2;
							newPtY -= BLOCK_SIZE/2;
							if (game.blocks.get(i).coordinates.get(j).getX() == newPtX && game.blocks.get(i).coordinates.get(j).getY() == newPtY) doesIntersect = true;
							if (newPtX < game.coordX || newPtX > game.coordX+WIDE-BLOCK_SIZE) { //check to see if it also is outside of the left boundary
								doesIntersect = true; // got kinda lazy, could do a shifting move first and then rotate but then I'll have to check again... gah!
							}
						}
					}
				}
			}
		}
		return doesIntersect;
	}
	
}

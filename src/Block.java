import java.awt.Color;
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
	public int held; //should only be allowed to hold 1 time
	public int type;
	
	public Block(Game g) {
		posx = g.coordX+WIDE/2; //blocks usually start in the middle of the game's screen
		posy = g.coordY-8*BLOCK_SIZE;
		coordinates = new ArrayList<Point>();
		aliveBlock = false;
		finished = false;
		held = 0;
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
	
	public void hardDrop(Game game) {
		//immediately drops down onto the game screen
		boolean done = false;
		while (!done) {
			done = shiftDown(game);
			done = !done;
		}
	}
	
	public void hold(Game game) {
		if (this.held != 0) return; //can't just spam repeat holds... can only hold once per block
		//check if any previous blocks were currently held
		boolean previousHeld = false;
		int index = 0;
		for (int i = 0; i < game.blocks.size(); i++) {
			if (game.blocks.get(i).held != 0 && !game.blocks.get(i).finished) { //it's been held
				index = i;
				previousHeld = true;
			}
		}
		//if there was none previously held, just add it
		held++;
		aliveBlock = false; //why tf is this not working?
		posx = game.coordX;
		posy = game.coordY+HIGH+50; //temporary guess
		//reset coordinates based on posy and posx gah! has to make it block specific too!
		this.resetCoordinates();
		
		if (!previousHeld) {
			//have to find next block and set it to aliveBlock
			Block temp1;
			boolean isFirst = true;
			for (int i = 0; i < game.blocks.size(); i++) {
				temp1 = game.blocks.get(i);
				if (isFirst && !temp1.finished && temp1.held == 0) {
					//update the coordinates too... gah!
					temp1.posx = game.coordX+WIDE/2; //blocks usually start in the middle of the game's screen
					temp1.posy = game.coordY;
					temp1.resetCoordinates();
					temp1.aliveBlock = true;
					temp1.held++;
					//for now... blocks usually start in the middle of the game's width screen
					game.blocks.set(i, temp1);
					isFirst = false;
					return;
				}
			}
		} 
		if (previousHeld) { //if there was one previously held, switch it over
			//this == current aliveBlock
			Block temp1 = game.blocks.get(index);
			temp1.posx = game.coordX+WIDE/2;
			temp1.posy = game.coordY;
			//reset the coordinates gah!
			temp1.resetCoordinates();
			temp1.aliveBlock = true;
			game.blocks.set(index, temp1);
		}
	}
	
	public void draw(Graphics g) {
		//draw the element here
		g.setColor(BLOCK_COLORS[type+3]);
		for (int i = 0; i < coordinates.size(); i++) {
			g.fillRect(coordinates.get(i).x, coordinates.get(i).y, BLOCK_SIZE-1, BLOCK_SIZE-1);
		}
	}
	
	public void resetCoordinates() {
		//should only use posx and posy values
	}
	
	public boolean intersectsOtherBlock(Game game, int condition) {
		//condition == 1 for left, condition == 2 for right, condition == 0 for down, condition == 3 for CCW, condition == 4 if it's literally in the same location
		boolean doesIntersect = false;
		for (int i = 0; i < game.blocks.size(); i++) {
			//for every coordinate for every block... compare it with the current block
			if (!this.equals(game.blocks.get(i))) {
				for (int j = 0; j < game.blocks.get(i).coordinates.size(); j++) {
					for (int k = 0; k < this.coordinates.size(); k++) {
						if (condition == 1) {
							//for checking leftwards
							if (game.blocks.get(i).finished && game.blocks.get(i).coordinates.get(j).getX() == (this.coordinates.get(k).getX()) - BLOCK_SIZE && game.blocks.get(i).coordinates.get(j).getY() == (this.coordinates.get(k).getY())) doesIntersect = true; 
						} else if (condition == 2) {
							if (game.blocks.get(i).finished && game.blocks.get(i).coordinates.get(j).getX() == (this.coordinates.get(k).getX()) + BLOCK_SIZE && game.blocks.get(i).coordinates.get(j).getY() == (this.coordinates.get(k).getY())) doesIntersect = true; 
						} else if (condition == 0 ) {
							// it's just checking downwards, has to make sure that the block is on the ground first
							if (game.blocks.get(i).finished && game.blocks.get(i).coordinates.get(j).getX() == (this.coordinates.get(k).getX()) && game.blocks.get(i).coordinates.get(j).getY() == (this.coordinates.get(k).getY() + BLOCK_SIZE)) doesIntersect = true; 
						} else if (condition == 3 ){ 
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
						} else {
							//checks for current location... meant to end the game if it does
							if (game.blocks.get(i).finished && game.blocks.get(i).coordinates.get(j).getX() == (this.coordinates.get(k).getX()) && game.blocks.get(i).coordinates.get(j).getY() == (this.coordinates.get(k).getY())) doesIntersect = true;
						}
					}
				}
			}
		}
		return doesIntersect;
	}
	
}

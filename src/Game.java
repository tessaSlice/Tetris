import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Game implements Constants {
	//the game's location... plan to have multiple displayed on the visual screen. Block's location will be relative to the game's location
	//dimension is 10 wide * 20 high
	public int coordX; 
	public int coordY;
	public ArrayList<Block> blocks;
	public int score; //using original nintendo scoring system because I'm lazy
	public int level;
	public boolean over;
	public ArrayList<Block> bagOfBlocks;
	
	//neural network parts
//	public NeuralNetwork brain;
//	public double[] senses;
//	public int[] columnHeights; //should show the highest (technically lowest) y value in each column
	
	public Game(int x, int y) {
		coordX = x;
		coordY = y;
		blocks = new ArrayList<Block>();
		
		score = 0;
		level = 0;
		over = false;
		bagOfBlocks = new ArrayList<Block>();
		refillBag();
		
		//NN part
//		brain = new NeuralNetwork(13, 6, 4, 5); //could change to 6 later on if I want to implement hold function
//		columnHeights = new int[10];
//		for (int i = 0; i < 10; i++) {
//			columnHeights[i] = 0;
//		}
	}
	
	public void update() {
		if (over) return;
		
		level = score / 10000; //arbitrary number I picked ngl
		int rowsRemoved = 0;
		//check to see if there's a full row of blocks
		int[] rowCount = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //counts the number of coordinates there are in every row
		for (int i = 0; i < blocks.size(); i++) {
			//check to see if there are 10 coordinates with the same posy
			if (blocks.get(i).finished) { //prevent the ones that haven't been loaded up yet from getting triggered
				for (int j = 0; j < blocks.get(i).coordinates.size(); j++) {
					//determine which index of the rowCount array the coordinate goes into
					int index = (int) ((blocks.get(i).coordinates.get(j).getY() - this.coordY)/(BLOCK_SIZE));
					if (index < 0 || index > 19) over = true; //can use this to immediately call the ending of the game!!!
					else rowCount[index] += 1; 
				}	
			}
		}
		for (int i = 0; i < rowCount.length; i++) {
			if (rowCount[i] == 10) {
				rowsRemoved++;
				//it's filled up an entire row
				//start removing coordinates specific to that row
				int targetCoord = i*BLOCK_SIZE+coordY;
				for (int j = blocks.size() -1; j >= 0; j--) {
					for (int k = blocks.get(j).coordinates.size() - 1; k >= 0; k--) {
						if (blocks.get(j).coordinates.get(k).getY() == targetCoord) {
							blocks.get(j).coordinates.remove(k);
						}
					}
				}
				//also have to move every single coordinate that's above that row have to shift it down by BLOCK_SIZE
				for (int j = 0; j < blocks.size(); j++) {
					for (int k = 0; k < blocks.get(j).coordinates.size(); k++) {
						if (blocks.get(j).coordinates.get(k).getY() < targetCoord && blocks.get(j).finished) { //has to be finished or else blocks that aren't will have shifted downwards but their rotationPoint won't have
							blocks.get(j).coordinates.set(k, new Point((int)(blocks.get(j).coordinates.get(k).getX()), (int) blocks.get(j).coordinates.get(k).getY() + BLOCK_SIZE));
						}
					}
				}
			}
		}
		if (rowsRemoved == 1) score += 40 * (1+level); //scoring system
		else if (rowsRemoved == 2) score += 100 * (1+level);
		else if (rowsRemoved == 3) score += 300 * (1+level);
		else if (rowsRemoved == 4) score += 1200 * (1+level);
		
		//also just check if all of the coordinates of a block have been removed... then remove the block
		for (int i = blocks.size() - 1; i >= 0; i--) {
			if (blocks.get(i).coordinates.size() == 0) blocks.remove(i);
		}
		
		Block temp1 = new Block(this);
		//check if the current aliveBlock can't move anymore... then aliveBlock = false;
		for (int i = 0; i < blocks.size(); i++) {
			//see if it can move down anymore
			temp1 = blocks.get(i);
			if (temp1.aliveBlock && temp1.intersectsOtherBlock(this, 0)) {
				temp1.aliveBlock = false;
				temp1.finished = true;
				if (temp1.intersectsOtherBlock(this, 4)) over = true; //it currently collides with another block
			}
		}
		
		Block temp;
		boolean isFirst = true;
		for (int i = 0; i < blocks.size(); i++) {
			temp = blocks.get(i);
			if (temp.finished) {
				temp.aliveBlock = false;
				blocks.set(i, temp);
			} else if (temp.held != 0 && !temp.aliveBlock) { //currently the piece being held
				//reset the hold position since it's not working too well in the Block class
				temp.posx = coordX;
				temp.posy = coordY+HIGH+50;
				temp.resetCoordinates();
			} else if (temp.aliveBlock) { //already found the first block
				isFirst = false;
			} else if (isFirst) {
				temp.aliveBlock = true;
				temp.posx = coordX+WIDE/2; //blocks usually start in the middle of the game's screen
				temp.posy = coordY;
				//update the coordinates too... gah!
				temp.resetCoordinates();
				//for now... blocks usually start in the middle of the game's width screen
				blocks.set(i, temp);
				isFirst = false;
			} else {
				temp.aliveBlock = false;
				blocks.set(i, temp);
			}
		}

//		add another block if there's no more blocks left in the array that are still in queue
		int aliveCount = 0;
		for (Block b : blocks) {
			if (!b.finished) aliveCount++;
		}
		if (aliveCount < 5 && !over) { //check to make sure the game isn't over first before adding stuff
			//per Random Generator Guidelines - https://tetris.fandom.com/wiki/Random_Generator
			temp = new Block(this);
			if (bagOfBlocks.size() == 0) refillBag();
			int rand = (int) (Math.random() * bagOfBlocks.size());
			temp = bagOfBlocks.remove(rand);
			blocks.add(temp);
		}
	}
	
	
	
//	public void performML() {
//		if (over) return;
//		//do ML part first
//		double[] senses = new double[13];
//		//get the highest y coordinate of each column
//		for (int i = 0; i < blocks.size(); i++) {
//			for (int j = 0; j < blocks.get(i).coordinates.size(); j++) {
//				if (blocks.get(i).finished) {
//					int index = (int) ((blocks.get(i).coordinates.get(j).getX() - this.coordX)/(BLOCK_SIZE));
//					if (columnHeights[index] < blocks.get(i).coordinates.get(j).getY()) {
//						columnHeights[index] = (int) blocks.get(i).coordinates.get(j).getY(); //update the index so it's the greatest
//					}
//				}
//			}
//		}
//		for (int i = 0; i < 10; i++) {
//			senses[i] = columnHeights[i]; 
//		}
//		Block tempB = new Block(this);
//		for (int i = 0; i < blocks.size(); i++) {
//			if (blocks.get(i).aliveBlock) tempB = blocks.get(i);
//		}
//		senses[10] = tempB.type;
//		senses[11] = tempB.rotationPoint.getX();
//		senses[12] = tempB.rotationPoint.getY();
//		
//		double[] thoughts = brain.makeGuess(senses);
//		
//		//find the biggest value in thoughts
//		int responses = greatestValue(thoughts);
//		
//		if (responses == 1) { //move left
//			for (int i = 0; i < blocks.size(); i++) {
//        		if (blocks.get(i).aliveBlock && !blocks.get(i).finished) {
//            		blocks.get(i).shiftLeft(this);
//        		}
//        	}
//		} else if (responses == 2) { //shift right
//			for (int i = 0; i < blocks.size(); i++) {
//        		if (blocks.get(i).aliveBlock && !blocks.get(i).finished) {
//            		blocks.get(i).shiftRight(this);
//        		}
//        	}
//		} else if (responses == 3) { //drop down
//			for (int i = 0; i < blocks.size(); i++) {
//        		if (blocks.get(i).aliveBlock && !blocks.get(i).finished) {
//            		blocks.get(i).hardDrop(this);
//        		}
//        	}
//		} else if (responses == 4) { //rotate CCW
//			for (int i = 0; i < blocks.size(); i++) {
//        		if (blocks.get(i).aliveBlock && !blocks.get(i).finished) {
//            		blocks.get(i).rotateCCW(this);
//        		}
//        	}
//		} //else do nothing
//	}
	
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(coordX, coordY, WIDE, HIGH);
		if (!over) {
			for (int i = 0; i < blocks.size(); i++) {
				blocks.get(i).draw(g);
			}	
		} else {
			//game over
			for (int i = blocks.size()-1; i >= 0; i--) {
				blocks.remove(i);
			}
			g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g.setColor(Color.MAGENTA);
			g.drawString("GAME OVER", coordX+BLOCK_SIZE/2, coordY + HIGH/2);
		}
	}
	
	public int greatestValue(double[] thoughts) {
		double greatestValue = 0;
		for (int i = 0; i < thoughts.length; i++) {
			if (thoughts[i] > greatestValue) greatestValue = thoughts[i];
		}
		//locate the index and return it
		int index = 0;
		for (int i = 0; i < thoughts.length; i++) {
			if (thoughts[i] == greatestValue) index = i;
		}
		return index;
	}
	
	public void refillBag() {
		bagOfBlocks.add(new Stick(this));
		bagOfBlocks.add(new LBlock(this));
		bagOfBlocks.add(new L2Block(this));
		bagOfBlocks.add(new Pyramid(this));
		bagOfBlocks.add(new SBlock(this));
		bagOfBlocks.add(new ZBlock(this));
		bagOfBlocks.add(new Square(this));
	}
}

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
	
	public Game(int x, int y) {
		coordX = x;
		coordY = y;
		blocks = new ArrayList<Block>();
		
		score = 0;
		level = 0;
	}
	
	public void update() {
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
				blocks.set(i, temp1);
			}
		}
		//reset alive block status
		Block temp;
		boolean isFirst = true;
		for (int i = 0; i < blocks.size(); i++) {
			temp = blocks.get(i);
			if (temp.finished) {
				temp.aliveBlock = false;
				blocks.set(i, temp);
			} else if (isFirst) {
				if (temp.aliveBlock) return; // if it's still the first alive element then don't do anything
				temp.aliveBlock = true;
				//update the coordinates too... gah!
				for (int j = 0; j < 7; j++) {
					temp.shiftDown(this);
				}
				//for now... blocks usually start in the middle of the game's width screen
				blocks.set(i, temp);
				isFirst = false;
			} else {
				temp.aliveBlock = false;
				blocks.set(i, temp);
			}
		}
//		add another block if there's no more aliveBlocks
		int aliveCount = 0;
		for (Block b : blocks) {
			if (!b.finished) aliveCount++;
		}
		if (aliveCount < 5 && !over) { //check to make sure the game isn't over first before adding stuff
			temp = new Block(this);
			int rand = (int) (Math.random() * 7);
			if (rand == 0) {
				temp = new Stick(this);
			} else if (rand == 1) {
				temp = new LBlock(this);
			} else if (rand == 2) {
				temp = new L2Block(this);
			} else if (rand == 3) {
				temp = new Pyramid(this);
			} else if (rand == 4) {
				temp = new SBlock(this);
			} else if (rand == 5) {
				temp = new Square(this);
			} else if (rand == 6) {
				temp = new ZBlock(this);
			}
			blocks.add(temp);
		}
	}
	
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
}

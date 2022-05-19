import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Game implements Constants {
	//the game's location... plan to have multiple displayed on the visual screen. Block's location will be relative to the game's location
	//dimension is 10 wide * 20 high
	public int coordX; 
	public int coordY;
	public ArrayList<Block> blocks;
	
	public Game(int x, int y) {
		coordX = x;
		coordY = y;
		blocks = new ArrayList<Block>();
	}
	
	public void update() {
		//check to see if there's a full row of blocks
		int[] rowCount = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //counts the number of coordinates there are in every row
		for (int i = 0; i < blocks.size(); i++) {
			//check to see if there are 10 coordinates with the same posy
			if (blocks.get(i).finished) { //prevent the ones that haven't been loaded up yet from getting triggered
				for (int j = 0; j < blocks.get(i).coordinates.size(); j++) {
					//determine which index of the rowCount array the coordinate goes into
					rowCount[(int) ((blocks.get(i).coordinates.get(j).getY() - this.coordY)/(BLOCK_SIZE))] += 1;
				}	
			}
		}
		for (int i = 0; i < rowCount.length; i++) {
			if (rowCount[i] == 10) {
				//it's filled up an entire row
				//start removing coordinates specific to that row
				int targetCoord = i*BLOCK_SIZE+coordY;
				for (int j = 0; j < blocks.size(); j++) {
					for (int k = 0; k < blocks.get(j).coordinates.size(); k++) {
						if (blocks.get(j).coordinates.get(k).getY() == targetCoord) {
							blocks.get(j).coordinates.remove(k);
						}
					}
				}
				//also have to move every single coordinate that's above that row have to shift it down by BLOCK_SIZE
				for (int j = 0; j < blocks.size(); j++) {
					for (int k = 0; k < blocks.get(j).coordinates.size(); k++) {
						if (blocks.get(j).coordinates.get(k).getY() < targetCoord) {
							blocks.get(j).coordinates.set(k, new Point((int)(blocks.get(j).coordinates.get(k).getX()), (int) blocks.get(j).coordinates.get(k).getX() + BLOCK_SIZE));
						}
					}
				}
			}
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
		if (aliveCount < 5) {
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
		for (int i = 0; i < blocks.size(); i++) {
			blocks.get(i).draw(g);
		}
	}
}

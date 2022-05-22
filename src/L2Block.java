import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class L2Block extends Block implements Constants {
    //posx and posy represents top left coordinate
	
	public L2Block(Game g) {
        super(g);
        type = -3;
        rotationPoint = new Point(posx+BLOCK_SIZE/2, posy+3*BLOCK_SIZE/2);
		//for now... blocks usually start in the middle of the game's width screen
		coordinates.add(new Point(posx, posy));
        coordinates.add(new Point(posx, posy+BLOCK_SIZE));
        coordinates.add(new Point(posx, posy+2*BLOCK_SIZE));
        coordinates.add(new Point(posx-BLOCK_SIZE, posy+2*BLOCK_SIZE));
	}
	
	public void draw(Graphics g) {
		//draw the element here
        g.setColor(Color.RED);
		for (int i = 0; i < coordinates.size(); i++) {
			g.fillRect(coordinates.get(i).x, coordinates.get(i).y, BLOCK_SIZE-1, BLOCK_SIZE-1);
		}
	}
	
	public void resetCoordinates() {
		//should only use posx and posy values
		rotationPoint = new Point(posx+BLOCK_SIZE/2, posy+3*BLOCK_SIZE/2);
		//remove all coordinates before inserting new ones
		coordinates.clear();
		coordinates.add(new Point(posx, posy));
        coordinates.add(new Point(posx, posy+BLOCK_SIZE));
        coordinates.add(new Point(posx, posy+2*BLOCK_SIZE));
        coordinates.add(new Point(posx-BLOCK_SIZE, posy+2*BLOCK_SIZE));
	}
}

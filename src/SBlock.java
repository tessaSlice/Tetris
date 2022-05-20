import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class SBlock extends Block implements Constants {
    //posx and posy represents top left coordinate
	
	public SBlock(Game g) {
        super(g);
        rotationPoint = new Point(posx+3*BLOCK_SIZE/2, posy+3*BLOCK_SIZE/2);
		//for now... blocks usually start in the middle of the game's width screen
		coordinates.add(new Point(posx+BLOCK_SIZE, posy));
        coordinates.add(new Point(posx+2*BLOCK_SIZE, posy));
        coordinates.add(new Point(posx, posy+BLOCK_SIZE));
        coordinates.add(new Point(posx+BLOCK_SIZE, posy+BLOCK_SIZE));
	}
	
	public void draw(Graphics g) {
		//draw the element here
        g.setColor(Color.YELLOW);
		for (int i = 0; i < coordinates.size(); i++) {
			g.fillRect(coordinates.get(i).x, coordinates.get(i).y, BLOCK_SIZE-1, BLOCK_SIZE-1);
		}
	}
}

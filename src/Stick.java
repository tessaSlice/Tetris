import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Stick extends Block implements Constants {
	//represents top left coordinate, long skinny one
	
	public Stick(Game g) {
        super(g);
        rotationPoint = new Point(posx, posy+2*BLOCK_SIZE);
        // starts out as a vertical line
		coordinates.add(new Point(posx, posy));
		coordinates.add(new Point(posx, posy+BLOCK_SIZE));
		coordinates.add(new Point(posx, posy+2*BLOCK_SIZE));
		coordinates.add(new Point(posx, posy+3*BLOCK_SIZE));
	}
	
	public void draw(Graphics g) {
		//draw the element here
		g.setColor(Color.CYAN);
		for (int i = 0; i < coordinates.size(); i++) {
			g.fillRect(coordinates.get(i).x, coordinates.get(i).y, BLOCK_SIZE-1, BLOCK_SIZE-1);
		}
	}
}

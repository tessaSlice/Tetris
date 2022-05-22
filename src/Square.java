import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Square extends Block implements Constants {
    //posx and posy represents top left coordinate
	
	public Square(Game g) {
        super(g);
        type = -1;
        rotationPoint = new Point(posx+BLOCK_SIZE, posy+BLOCK_SIZE);
		//for now... blocks usually start in the middle of the game's width screen
		coordinates.add(new Point(posx, posy));
        coordinates.add(new Point(posx+BLOCK_SIZE, posy));
        coordinates.add(new Point(posx, posy+BLOCK_SIZE));
        coordinates.add(new Point(posx+BLOCK_SIZE, posy+BLOCK_SIZE));
	}
	
	public void draw(Graphics g) {
		//draw the element here
        g.setColor(Color.PINK);
		for (int i = 0; i < coordinates.size(); i++) {
			g.fillRect(coordinates.get(i).x, coordinates.get(i).y, BLOCK_SIZE-1, BLOCK_SIZE-1);
		}
	}
	
	public void resetCoordinates() {
		rotationPoint = new Point(posx+BLOCK_SIZE, posy+BLOCK_SIZE);
		//for now... blocks usually start in the middle of the game's width screen
		coordinates.clear();
		coordinates.add(new Point(posx, posy));
        coordinates.add(new Point(posx+BLOCK_SIZE, posy));
        coordinates.add(new Point(posx, posy+BLOCK_SIZE));
        coordinates.add(new Point(posx+BLOCK_SIZE, posy+BLOCK_SIZE));
	}
}
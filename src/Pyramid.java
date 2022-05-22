import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Pyramid extends Block implements Constants {
    //posx and posy represents top left coordinate
	
	public Pyramid(Game g) {
        super(g);
        type = 1;
        rotationPoint = new Point(posx+3*BLOCK_SIZE/2, posy+3*BLOCK_SIZE/2);
		//for now... blocks usually start in the middle of the game's width screen
		coordinates.add(new Point(posx, posy+BLOCK_SIZE));
        coordinates.add(new Point(posx+BLOCK_SIZE, posy));
        coordinates.add(new Point(posx+BLOCK_SIZE, posy+BLOCK_SIZE));
        coordinates.add(new Point(posx+2*BLOCK_SIZE, posy+BLOCK_SIZE));
	}
	
	public void resetCoordinates() {
		rotationPoint = new Point(posx+3*BLOCK_SIZE/2, posy+3*BLOCK_SIZE/2);
		//for now... blocks usually start in the middle of the game's width screen
		coordinates.clear();
		coordinates.add(new Point(posx, posy+BLOCK_SIZE));
        coordinates.add(new Point(posx+BLOCK_SIZE, posy));
        coordinates.add(new Point(posx+BLOCK_SIZE, posy+BLOCK_SIZE));
        coordinates.add(new Point(posx+2*BLOCK_SIZE, posy+BLOCK_SIZE));
	}
}

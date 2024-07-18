import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Stick extends Block implements Constants {
	// represents top left coordinate, long skinny one

	public Stick(Game g) {
		super(g);
		type = 0;
		rotationPoint = new Point(posx, posy + 2 * BLOCK_SIZE);
		// starts out as a vertical line
		coordinates.add(new Point(posx, posy));
		coordinates.add(new Point(posx, posy + BLOCK_SIZE));
		coordinates.add(new Point(posx, posy + 2 * BLOCK_SIZE));
		coordinates.add(new Point(posx, posy + 3 * BLOCK_SIZE));
	}

	public void resetCoordinates() {
		rotationPoint = new Point(posx, posy + 2 * BLOCK_SIZE);
		// starts out as a vertical line
		coordinates.clear();
		coordinates.add(new Point(posx, posy));
		coordinates.add(new Point(posx, posy + BLOCK_SIZE));
		coordinates.add(new Point(posx, posy + 2 * BLOCK_SIZE));
		coordinates.add(new Point(posx, posy + 3 * BLOCK_SIZE));
	}
}

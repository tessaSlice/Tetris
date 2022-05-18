import java.awt.Color;
import java.awt.Graphics;
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
	
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(coordX, coordY, WIDE, HIGH);
		int midX = coordX + WIDE/2;
		int midY = coordY + HIGH/2;
		g.setColor(Color.RED);
		g.drawOval(midX, midY, 10, 10);
		for (int i = 0; i < blocks.size(); i++) {
			blocks.get(i).draw(g);
		}
        // use elements array instead, nah it shouldn't be that bad rn
        // for (int r = 0; r < elements.length; r++) {
        //     for (int c = 0; c < elements[r].length; c++) {
        //         if (elements[r][c] != null) {
        //             elements[r][c].draw(g);
        //         }
        //     }
        // }
	}
}

import java.awt.Graphics;
import java.awt.Color;

public class BlockInfo implements Constants {
	public int index; //what index of the blocks array they belong to
	//1 = stick; 2 = L; 3 = L2; 4 = S; 5 = Z; 6 = square; 7 = "pyramid"
	public int type;
    
    public int posx;
    public int posy;
	
	public BlockInfo(int x, int y) {
		index = 0; 
		type = 0;
        posx = x;
        posy = y;
	}

    public void draw(Graphics g) {
        // if (type == 0), etc change colors, but for now just do this
        g.setColor(Color.BLUE);
        g.fillRect(posx, posy, BLOCK_SIZE-1, BLOCK_SIZE-1);
    }
}

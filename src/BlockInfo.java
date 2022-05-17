
public class BlockInfo implements Constants {
	public int index; //what index of the blocks array they belong to
	//1 = stick; 2 = L; 3 = L2; 4 = S; 5 = Z; 6 = square; 7 = "pyramid"
	public int type;
	
	public BlockInfo() {
		index = 0; 
		type = 0;
	}
}

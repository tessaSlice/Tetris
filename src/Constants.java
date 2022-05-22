import java.awt.Color;

public interface Constants {
	//Window settings
    public static final int SCREEN_WIDE = 1200;
    public static final int SCREEN_HIGH = 700;
    
    //Game settings
    public static final int WIDE = SCREEN_WIDE/6;
    public static final int HIGH = WIDE*2;
    
    //Block settings
    public static final int BLOCK_SIZE = WIDE/10;
    public static final Color[] BLOCK_COLORS = {Color.BLUE, Color.YELLOW, Color.PINK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED};
    
    //Visual settings
    public static final int DELAY_THRESHOLD = 15;
    
    //Matrix settings
    public static final double MUTATERATE = .075;
    public static final double MUTATEVALUE = 42;
}
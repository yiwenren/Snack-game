import java.awt.Color;
import java.awt.*;
import java.util.*;

public class Egg {
	int row;
	int col;
	int w = yard.BLOCK_SIZE;
	int h = yard.BLOCK_SIZE;
	
	Color color = Color.RED;
	private static Random r = new Random();
	
	public Egg(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Egg(){
		this(r.nextInt(yard.ROWS - 2) + 2, r.nextInt(yard.COLS));
	}
	
	public void reAppear(){
		this.row = r.nextInt(yard.ROWS - 2) + 2;
		this.col = r.nextInt(yard.COLS);
	}
	
	public Rectangle getRect(){
		return new Rectangle(yard.BLOCK_SIZE * col, yard.BLOCK_SIZE * row, w, h);
	}
	
	public void draw(Graphics g){
		Color c = g.getColor();
		if(color == Color.RED) color = Color.YELLOW;
		else color = Color.RED;
		g.setColor(color);
		g.fillOval(col * yard.BLOCK_SIZE, row * yard.BLOCK_SIZE, w, h);
		g.setColor(c);
	}

}

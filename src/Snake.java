import java.awt.*;
import java.awt.event.KeyEvent;

public class Snake {
	
	private Node head = null;
	private Node tail = null;
	private int size = 0;
	
	
	
	private Node node = new Node(20, 30, Dir.L);
	private yard y;
	
	public Snake(yard y){
		head = node;
		tail = node;
		size = 1;
		this.y = y;
	}
	
	public void draw(Graphics g){
		if(size <= 0){
			return;
		}
		move();
		for(Node n = head; n != null; n = n.next){
			n.draw(g);
		}
		
	}
	
	public void move(){
		addToHead();
		deleteTail();
		checkDead();
	}
	
	private void checkDead(){
		//if the snake run out of the panel, it dies
		if(head.row < 1 || head.col < 0 || head.row > yard.ROWS || head.col > yard.COLS){
			y.stop();
		}
		//if the snake run into its body, it dies
		for(Node n = head.next; n != null; n = n.next){
			if(head.row == n.row && head.col == n.col){
				y.stop();
			}
		}
	}
	
	public void deleteTail(){
		if(size == 0) return;
		tail = tail.prev;
		tail.next = null;
	}
	
	public void addToTail(){
		Node node = null;
		switch(tail.dir){
		case L:
			node = new Node(tail.row, tail.col + 1, tail.dir);
			break;
		case R:
			node = new Node(tail.row, tail.col - 1, tail.dir);
			break;
		case U:
			node = new Node(tail.row + 1, tail.col, tail.dir);
			break;
		case D:
			node = new Node(tail.row - 1, tail.col, tail.dir);
			break;
		}
		tail.next = node;
		node.prev = tail;
		tail = node;
		size ++;
	}
	
	public void addToHead(){
		Node node = null;
		switch(head.dir){
		case L:
			node = new Node(head.row, head.col - 1, head.dir);
			break;
		case R:
			node = new Node(head.row, head.col + 1, head.dir);
			break;
		case U:
			node = new Node(head.row - 1, head.col, head.dir);
			break;
		case D:
			node = new Node(head.row + 1, head.col, head.dir);
			break;
		}
		node.next = head;
		head.prev = node;
		head = node;
		size ++;
	}

	public void KeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT:
			if(head.dir != Dir.R)
				head.dir = Dir.L;
			break;
		case KeyEvent.VK_RIGHT:
			if(head.dir != Dir.L)
				head.dir = Dir.R;
			break;
		case KeyEvent.VK_UP:
			if(head.dir != Dir.D)
				head.dir = Dir.U;
			break;
		case KeyEvent.VK_DOWN:
			if(head.dir != Dir.U)
				head.dir = Dir.D;
			break;
		}
		
	}
	
	public Rectangle getRect(){
		return new Rectangle(yard.BLOCK_SIZE * head.col, yard.BLOCK_SIZE * head.row, head.w, head.h);
	}
	
	public void eat(Egg e){
		if(this.getRect().intersects(e.getRect())){
			e.reAppear();
			this.addToHead();
			y.score += 5;
		}
	}
	
	private class Node{
		int row;
		int col;
		int w = yard.BLOCK_SIZE, h = yard.BLOCK_SIZE;
		Dir dir;
		Node next = null;
		Node prev = null;
		public Node(int row, int col, Dir dir){
			this.row = row;
			this.col = col;
			this.dir = dir;
		}
		
		public void draw(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.BLACK);
			g.fillRect(col * yard.BLOCK_SIZE, row * yard.BLOCK_SIZE, w, h);
			g.setColor(c);
		}
	}
}

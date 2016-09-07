import java.awt.*;
import java.awt.event.*;

public class yard extends Frame {
	
	public static final int ROWS = 30;
	public static final int COLS = 30;
	public static final int BLOCK_SIZE = 15;
	
	PaintThread paintThread = new PaintThread();
	
	private boolean gameover = false;
	int score = 0;
	private Snake snack = new Snake(this);
	private Egg egg = new Egg();
	private Font fontGameOver = new Font("Verdana", Font.BOLD, 50); 
	//prevent the screen from shining
	Image offScreenImage = null;
	
	public void launch(){
		this.setLocation(200, 200);
		this.setSize(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		new Thread(paintThread).start();
		this.addKeyListener(new KeyMonitor());
		this.setVisible(true);
	}
	
	public void stop(){
		gameover = true;
	}
	

	public void paint(Graphics g){
		Color c = g.getColor();
		//background color
		g.setColor(Color.gray);
		g.fillRect(0, 0, COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		g.setColor(Color.darkGray);
		//draw horizontal lines
		for(int i = 0; i < ROWS; i++){
			g.drawLine(0, i * BLOCK_SIZE, COLS * BLOCK_SIZE, i * BLOCK_SIZE);
		}
		//draw vertical lines
		for(int i = 0; i < COLS; i++){
			g.drawLine(i * BLOCK_SIZE, 0, i * BLOCK_SIZE , ROWS * BLOCK_SIZE);
		}
		
		snack.eat(egg);
		
		snack.draw(g);
		egg.draw(g);
		
		g.setColor(Color.yellow);
		g.drawString("score: " + score, 10, 60);
		
		if(gameover){
			g.setFont(fontGameOver);
			g.drawString("Game Over", 80, 220);
			paintThread.pause();
		}
		
		
		g.setColor(c);
		
		this.setVisible(true);
	}
	

	@Override
	public void update(Graphics g) {
		if(offScreenImage == null){
			offScreenImage = this.createImage(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		}
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	private class PaintThread implements Runnable{
		private boolean pause = false;
		@Override
		public void run() {
			while(true){
				if(pause) continue;
				else repaint();
				try{
					Thread.sleep(200);
				} catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			
		}
		
		public void pause(){
			pause = true;
		}
		
		public void restart(){
			this.pause = false;
			snack = new Snake(yard.this);
			gameover = false; 
		}
		
	}
	
	private class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			//if(key == KeyEvent.VK_F2)
			snack.KeyPressed(e);
			if(key == KeyEvent.VK_F2){
				paintThread.restart();
			}
		}

		
		
	}

	public static void main(String[] args) {
		new yard().launch();
	}


}

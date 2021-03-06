package Projeto;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Jogo extends Canvas implements Runnable, KeyListener{
	
	public Node[] nodeSnake = new Node[30];
	
	public boolean left,right,up,down;
	
	public int score = 0;
	
	public int macaX = 0, macaY = 0;
	
	public int spd = 1;
	
	public Jogo() {
		this.setPreferredSize(new Dimension(260,260));
		for(int i = 0; i < nodeSnake.length; i++) {
			nodeSnake[i] = new Node(0,0);
		}
		this.addKeyListener(this);
	}
	
	public void tick() {
		
		for(int i = nodeSnake.length - 1; i > 0; i--) {
			nodeSnake[i].x = nodeSnake[i-1].x;
			nodeSnake[i].y = nodeSnake[i-1].y;
		}
		
		if(nodeSnake[0].x + 10 < 0) {
			nodeSnake[0].x = 240;
		}else if(nodeSnake[0].x >= 240) {
			nodeSnake[0].x = -10;
		}
		
		if(nodeSnake[0].y + 10 < 0) {
			nodeSnake[0].y = 240;
		}else if(nodeSnake[0].y >= 240) {
			nodeSnake[0].y = -10;
		}
		
		if(right) {
			nodeSnake[0].x+=spd;
		}else if(up) {
			nodeSnake[0].y-=spd;
		}else if(down) {
			nodeSnake[0].y+=spd;
		}else if(left) {
			nodeSnake[0].x-=spd;
		}
		
		if(new Rectangle(nodeSnake[0].x,nodeSnake[0].y,10,10).intersects(new Rectangle(macaX, macaY,10,10))) {
			macaX = new Random().nextInt(260-10);
			macaY = new Random().nextInt(260-10);
			score++;
			spd++;
			System.out.println("Pontos: " + score);
		}
		
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, 260, 260);
		for(int i = 0; i < nodeSnake.length; i++) {
			g.setColor(Color.blue);
			g.fillRect(nodeSnake[i].x, nodeSnake[i].y, 10,10);
			
		}
		
		g.setColor(Color.red);
		g.fillRect(macaX, macaY, 10, 10);
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String args[]) {
		Jogo game = new Jogo();
		JFrame frame = new JFrame("Snake");
		frame.add(game);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		new Thread(game).start();
		
	}
	
	@Override
	public void run() {
		
		while(true) {
			tick();
			render();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
			left = false;
			up = false;
			down = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
			right = false;
			up = false;
			down = false;
		}else if(e.getKeyCode() == KeyEvent.VK_UP) {
			up = true;
			left = false;
			right = false;
			down = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = true;
			up = false;
			left = false;
			right = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}

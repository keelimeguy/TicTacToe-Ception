import entity.Player;
import graphics.Screen;
import graphics.Sprite;
import input.Keyboard;
import input.Mouse;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import level.Level;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static int width = 500;
	public static int height = width / 3 * 2;
	public static int scale = 2; // The game will be scaled up by this factor, so the actual window width and height will be the above values times this value
	public static String title = "TicTacToe-Ception";

	private Thread thread;
	private static JFrame frame;
	private Keyboard key;
	private boolean running = false;

	private Level level;
	private Screen screen;
	private Player[] player;

	// The image which will be drawn in the game window
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	/**
	 * Initiates the necessary variables of the game
	 */
	public Game() {

		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		frame = new JFrame();
		key = new Keyboard();

		player = new Player[2];
		player[0] = new Player(1, Sprite.markX, 0xFF00FF00, 0xFFFF0000);
		player[1] = new Player(2, Sprite.markO, 0xFF0000FF, 0xFFFF0000);

		level = new Level(width, height, 2, player);
		screen = new Screen(width, height);

		addKeyListener(key);

		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	/**
	 * Returns the height of the window with scaling.
	 * @return The width as an int value
	 */
	public static int getWindowWidth() {
		return frame.getContentPane().getWidth();
	}

	/**
	 * Returns the height of the window with scaling.
	 * @return The height as an int value
	 */
	public static int getWindowHeight() {
		return frame.getContentPane().getHeight();
	}

	/**
	 * Starts the game thread
	 */
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		createBufferStrategy(3);
		thread.start();
	}

	/**
	 * Stops the game thread
	 */
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Runs the game
	 */
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0, updates = 0;
		requestFocus();

		// The game loop
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			// Update 60 times a second
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			Graphics g = getGraphics();
			update(g);
			frames++;

			// Keep track of and display the game's ups and fps every second
			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				frame.setTitle(title + " | ups: " + updates + ", fps: " + frames);
				updates = 0;
				frames = 0;
			}
		}

		// If we get out of the game loop stop the game
		stop();
	}

	/**
	 * Update the game
	 */
	public void update() {
		key.update();
		level.update(getWindowWidth(), getWindowHeight(), screen);
	}

	public void update(Graphics g) {
		paint(g);
	}

	/**
	 * Render the game
	 */
	public void paint(Graphics g) {

		Screen scrn = new Screen(width, height);

		scrn.clear(0);

		// Render the level
		level.render(scrn);

		// Copy the screen pixels to the image to be drawn
		System.arraycopy(scrn.pixels, 0, screen.pixels, 0, screen.pixels.length);
		System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);

		Graphics gi = image.getGraphics();
		gi.setColor(Color.RED);
		gi.setFont(new Font(Font.SERIF, 20, 20));
		gi.drawString("Cur Board: (" + level.getCurX() + " , " + level.getCurY() + ")", 0, 20);
		gi.dispose();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
	}

	/**
	 * Start of the program
	 * @param args : Unused default arguments
	 */
	public static void main(String[] args) {

		System.setProperty("sun.awt.noerasebackground", "true");

		// Create the game
		Game game = new Game();
		game.frame.setResizable(true);
		game.frame.setTitle(Game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		// Start the game
		game.start();
	}
}

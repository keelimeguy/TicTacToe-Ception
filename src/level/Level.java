package level;

import entity.Player;
import graphics.Screen;

public class Level {

	protected int width, height, layers, curPlayer, numPlayers, curX, curY;
	protected Player[] player;
	protected Board board;

	public Level(int width, int height, int layers, Player[] player) {
		//if (layers > 4) layers = 4;
		if (layers < 1) layers = 1;
		this.width = width;
		this.height = height;
		this.layers = layers;
		this.player = player;
		numPlayers = player.length;
		curPlayer = 1;
		curX = curY = -1;

		int size = height;
		if (height > width) size = width;
		board = new Board(size, (width - size) / 2, (height - size) / 2, layers, this, 3, 3, null);
	}

	public void move(int x, int y) {
		curX = x;
		curY = y;
	}

	public int getCurX() {
		return curX;
	}

	public int getCurY() {
		return curY;
	}

	public void nextPlayer() {
		if (++curPlayer > numPlayers) curPlayer = 1;
	}

	public void win(int value) {
		System.out.println("Player " + value + " wins!");
	}

	public void update(int width, int height, Screen screen) {
		board.update(width, height, player[curPlayer - 1], screen);
	}

	public void render(Screen screen) {
		board.render(screen);
	}

}

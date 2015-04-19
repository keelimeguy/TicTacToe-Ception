package level;

import level.Cell;
import entity.Player;
import graphics.Screen;

public class Board {

	protected int size, x, y;
	protected Cell[] cells;

	public Board(int size, int x, int y, int layers) {
		this.size = size;
		this.x = x;
		this.y = y;

		cells = new Cell[3 * 3];

		for (int c = 0; c < 3; c++)
			for (int r = 0; r < 3; r++)

				cells[c + r * 3] = new Cell(size / 3 - 1, x + c * (size / 3 + 1), y + r * (size / 3 + 1), layers - 1);
	}

	public void update(int width, int height, Player player, Screen screen) {
		for (int c = 0; c < 3; c++)
			for (int r = 0; r < 3; r++)
				cells[r + c * 3].update(width, height, player, screen);
	}

	public void render(Screen screen) {
		for (int c = 0; c < 3; c++)
			for (int r = 0; r < 3; r++)
				cells[r + c * 3].render(screen);
	}
}

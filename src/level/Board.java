package level;

import level.Cell;
import entity.Player;
import graphics.Screen;

public class Board {

	protected int size, x, y, col, row;
	protected Cell parent;
	protected Level level;
	protected Cell[] cells;
	protected int[] marks;

	public Board(int size, int x, int y, int layers, Level level, int col, int row, Cell parent) {
		this.parent = parent;
		this.level = level;
		this.size = size;
		this.col = col;
		this.row = row;
		this.x = x;
		this.y = y;
		marks = new int[3 * 3];
		cells = new Cell[3 * 3];

		for (int c = 0; c < 3; c++)
			for (int r = 0; r < 3; r++)
				marks[c + r * 3] = -1;
		for (int c = 0; c < 3; c++)
			for (int r = 0; r < 3; r++)
				cells[c + r * 3] = new Cell(size / 3 - 1, x + c * (size / 3 + 1), y + r * (size / 3 + 1), layers - 1, level, this, c, r);
	}

	public void mark(int c, int r, int value, boolean next) {
		if (c >= 3 || r >= 3) return;
		if (marks[c + r * 3] == -1) {
			marks[c + r * 3] = value;
			
			if (next) level.nextPlayer();
			checkWin(c, r, value);
		} else if (next) level.nextPlayer();
	}

	public void update(int width, int height, Player player, Screen screen) {
		for (int c = 0; c < 3; c++)
			for (int r = 0; r < 3; r++)
				cells[c + r * 3].update(width, height, player, screen);
	}

	public void render(Screen screen) {
		for (int c = 0; c < 3; c++)
			for (int r = 0; r < 3; r++)
				cells[c + r * 3].render(screen);
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void checkWin(int x, int y, int value) {
		boolean win = false;
		
		if (marks[0] != -1 && marks[0] == marks[4] && marks[4] == marks[8]) win = true;
		if (marks[2] != -1 && marks[2] == marks[4] && marks[4] == marks[6]) win = true;
		if (marks[x] == marks[x + 3] && marks[x] == marks[x + 6]) win = true;
		if (marks[y * 3] == marks[1 + y * 3] && marks[y * 3] == marks[2 + y * 3]) win = true;
		
		if (parent != null && win)
			parent.mark(col, row, value, false);
		else if (win) level.win(value);

		level.move(x, y);
	}
}

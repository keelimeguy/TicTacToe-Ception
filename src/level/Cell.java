package level;

import entity.Player;
import graphics.Screen;
import graphics.Sprite;
import input.Mouse;

public class Cell {

	protected int size, x, y, clicks, col, row, layer;
	protected boolean clickable, clicked;
	protected Sprite sprite, dark, cpySprite;
	protected Board board, parent;
	protected Level level;
	protected Sprite mark, empty;

	public Cell(int size, int x, int y, int layers, Level level, Board parent, int col, int row) {
		this.size = size;
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.col = col;
		this.row = row;
		this.level = level;
		this.layer = layers;
		dark = null;
		cpySprite = null;
		clickable = false;
		clicked = false;
		clicks = 0;
		empty = new Sprite(size, size, Sprite.voidSprite);
		mark = empty;
		if (layers == 1)
			sprite = new Sprite(size, size, 0xFF1b87e0);
		else if (layers == 2)
			sprite = new Sprite(size, size, 0xFF22FF22);
		else if (layers == 3)
			sprite = new Sprite(size, size, 0xFFFFFF00);
		else {
			sprite = new Sprite(size, size, 0xFFFFFFFF);
			cpySprite = new Sprite(size, size, 0xFFFFFFFF);
			dark = new Sprite(size, size, 0xFFFFFFFF);
			dark.darker();
			clickable = true;
		}
		if (layers > 0)
			board = new Board(size, x, y, layers, level, col, row, this);
		else
			board = null;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void update(int width, int height, Player player, Screen screen) {
		player = new Player(player);
		if (layer >= 3) {
			player = new Player(player.getMarkValue(), new Sprite(5, 5, 0xFF000000), player.getColor(), player.getHoverColor());
		}
		if (level.getCurX() == parent.getCol() && level.getCurY() == parent.getRow() || level.getCurX() == -1) {
			if (cpySprite != null && level.getCurX() != -1) sprite = cpySprite;
			if (clickable) if (Mouse.getX() > (int) (x * width / screen.width) && Mouse.getX() < (int) ((x + size) * width / screen.width) && Mouse.getY() > (int) (y * height / screen.height) && Mouse.getY() < (int) ((y + size) * height / screen.height)) {
				mark = new Sprite(size, size, player.getHoverMark());
				if (Mouse.getB() == 1 && !clicked) {
					clicked = true;
					clicks++;
					if (clicks >= 1) {
						mark = new Sprite(size, size, player.getMark());
						mark(col, row, player.getMarkValue(), true);
						clickable = false;
					}
				}
			} else
				mark = empty;
		} else if (dark != null) sprite = dark;
		if (Mouse.getB() == -1) clicked = false;
		if (board != null) board.update(width, height, player, screen);
	}

	public void mark(int c, int r, int value, boolean next) {
		parent.mark(c, r, value, next);
	}

	public void render(Screen screen) {
		screen.renderCell(x, y, this);
		if (board != null)
			board.render(screen);
		else {
			screen.renderSprite(x, y, mark);
		}
	}
}

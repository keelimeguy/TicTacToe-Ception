package level;

import entity.Player;
import graphics.Screen;
import graphics.Sprite;
import input.Mouse;

public class Cell {

	protected int size, x, y, clicks;
	protected boolean clickable, clicked;
	protected Sprite sprite;
	protected Board board;
	protected Sprite mark;

	public Cell(int size, int x, int y, int layers) {
		this.size = size;
		this.x = x;
		this.y = y;
		clickable = false;
		clicked = false;
		clicks = 0;
		mark = new Sprite(size, size, Sprite.voidSprite);
		if (layers == 1)
			sprite = new Sprite(size, size, 0xFF1b87e0);
		else if (layers == 2)
			sprite = new Sprite(size, size, 0xFF00FF00);
		else if (layers == 3)
			sprite = new Sprite(size, size, 0xFFFFFF00);
		else {
			sprite = new Sprite(size, size, 0xFFFFFFFF);
			clickable = true;
		}
		if (layers > 0)
			board = new Board(size, x, y, layers);
		else
			board = null;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void update(int width, int height, Player player, Screen screen) {
		if (Mouse.getX() > (int) (x * width / screen.width) && Mouse.getX() < (int) ((x + size) * width / screen.width) && Mouse.getY() > (int) (y * height / screen.height) && Mouse.getY() < (int) ((y + size) * height / screen.height)) {
			if (Mouse.getB() == 1 && clickable && !clicked) {
				clicked = true;
				clicks++;
				if (clicks >= 1) {
					System.out.println(size);
					mark = new Sprite(size, size, player.getMark());
					clickable = false;
				}
			}
		}
		if (Mouse.getB() == -1) clicked = false;
		if (board != null) board.update(width, height, player, screen);
	}

	public void render(Screen screen) {
		screen.renderCell(x, y, this);
		if (board != null)
			board.render(screen);
		else
			screen.renderSprite(x, y, mark);
	}
}

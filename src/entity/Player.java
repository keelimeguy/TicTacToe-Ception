package entity;

import graphics.Sprite;

public class Player {

	private Sprite mark;

	public Player(Sprite mark) {
		this.mark = mark;
	}

	public Player(Sprite mark, int color) {
		this.mark = new Sprite(mark, 0xFF000000, color);
	}

	public Sprite getMark() {
		return mark;
	}
}

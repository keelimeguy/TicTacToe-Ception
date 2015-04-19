package entity;

import graphics.Sprite;

public class Player {

	private Sprite mark, hoverMark;
	private int markValue;

	public Player(int markValue, Sprite mark, int hoverColor) {
		this.markValue = markValue;
		this.mark = mark;
		hoverMark = new Sprite(mark, 0xFF000000, hoverColor);
	}

	public Player(int markValue, Sprite mark, int color, int hoverColor) {
		this.markValue = markValue;
		this.mark = new Sprite(mark, 0xFF000000, color);
		hoverMark = new Sprite(mark, 0xFF000000, hoverColor);
	}

	public Sprite getMark() {
		return mark;
	}

	public Sprite getHoverMark() {
		return hoverMark;
	}

	public int getMarkValue() {
		return markValue;
	}
}

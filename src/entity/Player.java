package entity;

import graphics.Sprite;

public class Player {

	private Sprite mark, hoverMark;
	private int markValue, color, hoverColor;

	public Player(Player player) {
		this.mark = player.mark;
		this.hoverMark = player.hoverMark;
		this.markValue = player.markValue;
		this.color = player.color;
		this.hoverColor = player.hoverColor;
	}

	public Player(int markValue, Sprite mark, int hoverColor) {
		this.markValue = markValue;
		this.mark = mark;
		hoverMark = new Sprite(mark, 0xFF000000, hoverColor);
		this.color = 0xFF000000;
		this.hoverColor = hoverColor;
	}

	public Player(int markValue, Sprite mark, int color, int hoverColor) {
		this.markValue = markValue;
		this.mark = new Sprite(mark, 0xFF000000, color);
		hoverMark = new Sprite(mark, 0xFF000000, hoverColor);
		this.color = color;
		this.hoverColor = hoverColor;
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

	public int getColor() {
		return color;
	}

	public int getHoverColor() {
		return hoverColor;
	}

	public String toString() {
		return "val:" + markValue;
	}
}

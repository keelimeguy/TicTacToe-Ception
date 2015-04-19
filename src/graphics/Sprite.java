package graphics;

import java.awt.Color;

public class Sprite {

	public final int SIZE_X, SIZE_Y;
	private int x, y;
	public int[] pixels;
	private SpriteSheet sheet;

	public static Sprite voidSprite = new Sprite(250, 250, 0xFFFF00FF);
	public static Sprite markO = new Sprite(250, 250, 0, 0, SpriteSheet.marks);
	public static Sprite markX = new Sprite(250, 250, 1, 0, SpriteSheet.marks);

	/**
	 * Creates a sprite from a sprite sheet
	 * @param size : Square size of the sprite (in pixels)
	 * @param x : The x coordinate of the sprite in the sprite sheet (in size units)
	 * @param y : The y coordinate of the sprite in the sprite sheet (in size units)
	 * @param sheet : The sprite sheet to take the sprite image from
	 */
	public Sprite(int sizex, int sizey, int x, int y, SpriteSheet sheet) {
		SIZE_X = sizex;
		SIZE_Y = sizey;
		this.x = x * sizex;
		this.y = y * sizey;
		this.sheet = sheet;
		pixels = new int[SIZE_X * SIZE_Y];
		load();
	}

	/**
	 * Creates a sprite of a given color
	 * @param size : Square size of the sprite (in pixels)
	 * @param color : The color to fill the sprite
	 */
	public Sprite(int sizex, int sizey, int color) {
		SIZE_X = sizex;
		SIZE_Y = sizey;
		pixels = new int[SIZE_X * SIZE_Y];
		setColor(color);
	}

	public Sprite(Sprite sprite, int oldColor, int newColor) {
		SIZE_X = sprite.SIZE_X;
		SIZE_Y = sprite.SIZE_Y;
		pixels = new int[SIZE_X * SIZE_Y];
		for (int y = 0; y < SIZE_Y; y++)
			for (int x = 0; x < SIZE_X; x++) {
				int col = sprite.pixels[x + y * SIZE_X];
				if (col != oldColor)
					pixels[x + y * SIZE_X] = col;
				else
					pixels[x + y * SIZE_X] = newColor;
			}
	}

	public Sprite(int sizex, int sizey, Sprite sprite) {
		SIZE_X = sizex;
		SIZE_Y = sizey;
		pixels = resizeSprite(sprite, sizex, sizey);
	}

	private int[] resizeSprite(Sprite sprite, int sizex, int sizey) {
		int[] data = getData(sprite);
		int[] newData = new int[sizex * sizey * 4];
		float scalex = (float) sizex / (float) sprite.SIZE_X;
		float scaley = (float) sizey / (float) sprite.SIZE_Y;
		System.out.println("scalex: " + scalex + ", scaley: " + scaley);
		for (int y = 0; y < sprite.SIZE_Y * scaley; y++)
			for (int x = 0; x < sprite.SIZE_X * scalex; x++) {
				int pxl = y * sizex * 4 + x * 4;
				int near = (int) (y / scaley) * (int) (sizex / scalex) * 4 + (int) (x / scalex) * 4;
				for (int i = 0; i < 4; i++)
					newData[pxl + i] = data[near + i];
			}

		return getPixelsFromData(newData, sizex, sizey);
	}

	private int[] getData(Sprite sprite) {
		int[] data = new int[sprite.SIZE_X * sprite.SIZE_Y * 4];
		for (int y = 0; y < sprite.SIZE_Y; y++)
			for (int x = 0; x < sprite.SIZE_X; x++) {
				int col = sprite.pixels[x + y * sprite.SIZE_X];
				Color c = new Color(col, true);
				int a = c.getAlpha();
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
				data[y * 4 * sprite.SIZE_X + x * 4] = a;
				data[y * 4 * sprite.SIZE_X + x * 4 + 1] = r;
				data[y * 4 * sprite.SIZE_X + x * 4 + 2] = g;
				data[y * 4 * sprite.SIZE_X + x * 4 + 3] = b;
			}
		return data;
	}

	private int[] getPixelsFromData(int[] data, int sizex, int sizey) {
		int[] pixels = new int[sizex * sizey];
		for (int y = 0; y < sizey; y++)
			for (int x = 0; x < sizex; x++)
				pixels[y * sizex + x] = (new Color(data[y * sizex * 4 + x * 4 + 1], data[y * sizex * 4 + x * 4 + 2], data[y * sizex * 4 + x * 4 + 3], data[y * sizex * 4 + x * 4]).hashCode());
		return pixels;
	}

	/**
	 * Fills the sprite with a color
	 * @param color : The color to fill the sprite
	 */
	private void setColor(int color) {
		for (int i = 0; i < SIZE_X * SIZE_Y; i++) {
			pixels[i] = color;
		}
	}

	/**
	 * Loads the sprite from its sprite sheet
	 */
	private void load() {
		for (int y = 0; y < SIZE_Y; y++) {
			for (int x = 0; x < SIZE_X; x++) {
				pixels[x + y * SIZE_X] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
			}
		}
	}
}

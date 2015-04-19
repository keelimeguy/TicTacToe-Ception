package graphics;

import level.Cell;

public class Screen {

	public int width, height;
	public int[] pixels;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public Screen(Screen scr) {
		width = scr.width;
		height = scr.height;
		pixels = new int[width * height];
		System.arraycopy(scr.pixels, 0, pixels, 0, pixels.length);
	}

	public void clear(int color) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}
	}

	public void renderCell(int xp, int yp, Cell cell) {
		Sprite sprite = cell.getSprite();
		for (int y = 0; y < sprite.SIZE_Y; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.SIZE_X; x++) {
				int xa = x + xp;
				if (xa < -sprite.SIZE_X || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.SIZE_X];
			}
		}
	}

	public void renderSprite(int xp, int yp, Sprite sprite) {
		for (int y = 0; y < sprite.SIZE_Y; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.SIZE_X; x++) {
				int xa = x + xp;
				if (xa < -sprite.SIZE_X || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int col = sprite.pixels[x + y * sprite.SIZE_X];
				if (col != 0xffff00ff) pixels[xa + ya * width] = col;
			}
		}
	}
}

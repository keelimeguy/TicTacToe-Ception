package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	private boolean[] keys = new boolean[120];
	public boolean space;

	/**
	 * Sets a flag to determine what action should occur given associated key presses
	 */
	public void update() {
		space = keys[KeyEvent.VK_SPACE];
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() < keys.length) keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() < keys.length) keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {

	}

}

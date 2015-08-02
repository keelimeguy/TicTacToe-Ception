import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JApplet;

public class GameApplet extends JApplet {
	private static final long serialVersionUID = 1L;
	private Game display = new Game();
	private Dimension appletSize;

	public void init() {
		setLayout(new BorderLayout());
		add(display);
		appletSize = this.getSize();
	}

	public void start() {
		System.out.println(appletSize.width);
		display.start(appletSize.width, appletSize.height);
	}

	public void stop() {
		display.stop();
	}
}
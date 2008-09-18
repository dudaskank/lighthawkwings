import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class TesteComponente extends JPanel {

	private static final long serialVersionUID = 1L;

	Dimension size = new Dimension();

	Dimension viewSize = new Dimension();

	public TesteComponente(int width, int height) {
		this(width, height, width, height);
	}

	public TesteComponente(int width, int height, int viewWidth, int viewHeight) {
		super();
		size.width = width;
		size.height = height;
		viewSize.width = viewWidth;
		viewSize.height = viewHeight;
		setPreferredSize(size);
	}

	public void repaint(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		super.repaint(x, y, width, height);
		Rectangle r = new Rectangle(x, y, width, height);
		System.out.println(r);
	}

	public void paint(Graphics g) {
		super.paint(g);
		System.out.print("paint");
		System.out.println(" " + g.getClipBounds());
		g.drawLine(0, 0, size.width, size.height);
		g.drawString("Shoryuken", 0, 0);
		g.drawString("Shoryuken2", 0, 20);
		g.drawString("Shoryuken3", 600, 420);
	}
}

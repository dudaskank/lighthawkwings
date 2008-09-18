import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/*
 * Criado em 04/04/2005 To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
/**
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 *
 * @author unknown
 */
public class TestaImagem extends JFrame {
	private static final long serialVersionUID = 394792844536827010L;
	SpriteMask mask;
	Image imagem;

	public TestaImagem() {
		super();
		imagem = loadImage("/data/images/cross.gif");
		mask = new SpriteMask(imagem);
		System.out.println(imagem);
		System.out.println(imagem.getWidth(null));
		System.out.println(imagem.getHeight(null));
		System.out.println(imagem.getSource());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(640, 480));
		setResizable(true);
		pack();
	}

	Image loadImage(String fileName) {
		Image image;
		MediaTracker mt = new MediaTracker(this);
		image = Toolkit.getDefaultToolkit().getImage(
				getClass().getResource((fileName)));
		mt.addImage(image, 0);
		try {
			mt.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (mt.isErrorAny()) {
			mt.removeImage(image);
			image = null;
		} else {
			mt.removeImage(image);
		}
		return image;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(imagem, 50, 50, this);
		mask.drawSpriteMask(g, 50, 100);
	}

	public static void main(String[] args) {
		TestaImagem teste = new TestaImagem();
		teste.setVisible(true);
	}

	public class SpriteMask {

		BufferedImage bufferedImage;

		public SpriteMask(Image imagem) {
			BufferedImage temp;
			int i, j, alpha;
			temp = new BufferedImage(imagem.getWidth(null), imagem
					.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			temp.getGraphics().drawImage(imagem, 0, 0, null);
			for (i = 0; i < imagem.getHeight(null); i++) {
				for (j = 0; j < imagem.getWidth(null); j++) {
					alpha = temp.getRGB(j, i) >>> 24;
					System.out.println("Alpha: " + alpha);
					if (alpha == 0) {
						temp.setRGB(j, i, Color.BLACK.getRGB());
					} else {
						temp.setRGB(j, i, Color.WHITE.getRGB());
					}
				}
			}
			bufferedImage = new BufferedImage(imagem.getWidth(null), imagem
					.getHeight(null), BufferedImage.TYPE_BYTE_BINARY);
			bufferedImage.getGraphics().drawImage(temp, 0, 0, null);
		}

		public void drawSpriteMask(Graphics g, int x, int y) {
			g.drawImage(bufferedImage, x, y, null);
		}
	}
}
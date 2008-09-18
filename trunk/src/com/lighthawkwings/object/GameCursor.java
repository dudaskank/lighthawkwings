package com.lighthawkwings.object;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import com.lighthawkwings.Game;

/**
 * Desenha uma imagem no lugar do cursor do mouse, usado quando se deseja que o cursor seja translucente por
 * exemplo.
 *
 * @author p554992
 *
 */
public class GameCursor extends AbstractGameObject<Game> {
	public static final float MOUSE_Z = Transition.TRANSITION_Z - 1f;

	protected Image image;

	protected Point hotSpot;

	protected Point mouseLocation;

	protected boolean show;

	public GameCursor(Game game, Image image, Point hotSpot) {
		super(game);
		setZ(MOUSE_Z);
		this.image = image;
		this.hotSpot = hotSpot;
		this.mouseLocation = new Point(0, 0);
		show = true;
	}

	public Point getHotSpot() {
		return hotSpot;
	}

	public void setHotSpot(Point hotSpot) {
		this.hotSpot = hotSpot;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void paint(Graphics2D g) {
		if (show && image != null) {
			g.drawImage(image, mouseLocation.x - hotSpot.x, mouseLocation.y - hotSpot.y, null);
		}
	}

	public void update(long elapsedTime) {
		mouseLocation.setLocation(game.getInputManager().getMouseX(), game.getInputManager().getMouseY());
	}

	public void hide() {
		show = false;
	}

	public void show() {
		show = true;
	}
}

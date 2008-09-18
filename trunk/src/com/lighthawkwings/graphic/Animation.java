package com.lighthawkwings.graphic;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * The Animation class manages a series of images (frames) and the amount of
 * time to display each frame.
 * </p>
 * <p>
 * Base on the code of the book Developing Games in Java, by David Brackeen
 * (Isbn: 1-59273-005-1)
 * </p>
 *
 * @author David Brackeen, Eduardo Oliveira
 *
 */
public class Animation {
	private List<AnimFrame> frames;
	private int currentFrameIndex;
	private long animTime;
	private long totalDuration;

	/**
	 * Creates a new, empty Animation.
	 */
	public Animation() {
		this(new ArrayList<AnimFrame>(), 0);
	}

	private Animation(List<AnimFrame> frames, long totalDuration) {
		this.frames = frames;
		this.totalDuration = totalDuration;
		start();
	}

	/**
	 * Creates a duplicate of this animation. The list of frames are shared
	 * between the two Animations, but each Animation can be animated
	 * independently.
	 */
	public Object clone() {
		return new Animation(frames, totalDuration);
	}

	/**
	 * Adds an image to the animation with the specified duration (time to
	 * display the image).
	 */
	public synchronized void addFrame(Image image, long duration) {
		totalDuration += duration;
		frames.add(new AnimFrame(image, totalDuration));
	}

	/**
	 * Starts this animation over from the beginning.
	 */
	public synchronized void start() {
		animTime = 0;
		if (frames.size() > 0) {
			currentFrameIndex = 0;// temp++ % frames.size();
		} else {
			currentFrameIndex = 0;
		}
	}

	/**
	 * Updates this animation's current image (frame), if neccesary.
	 */
	public synchronized void update(long elapsedTime) {
		if (frames.size() > 1) {
			animTime += elapsedTime;
			if (animTime >= totalDuration) {
				animTime = animTime % totalDuration;
				currentFrameIndex = 0;
			}
			while (animTime > getFrame(currentFrameIndex).endTime) {
				currentFrameIndex++;
			}
		}
	}

	/**
	 * Gets this Animation's current image. Returns null if this animation has
	 * no images.
	 */
	public synchronized Image getImage() {
		if (frames.size() == 0) {
			return null;
		} else {
			return getFrame(currentFrameIndex).image;
		}
	}

	private AnimFrame getFrame(int i) {
		return (AnimFrame) frames.get(i);
	}

	/**
	 * @return Returns the currentFrameIndex.
	 */
	public int getCurrentFrameIndex() {
		return this.currentFrameIndex;
	}

	/**
	 * @return Returns the total of frames in this animation.
	 */
	public int getTotalFrames() {
		return frames.size();
	}

	private class AnimFrame {
		Image image;
		long endTime;

		public AnimFrame(Image image, long endTime) {
			this.image = image;
			this.endTime = endTime;
		}
	}
}
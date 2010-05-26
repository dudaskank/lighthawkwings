package com.lighthawkwings.state;

import java.awt.Graphics2D;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.lighthawkwings.Game;
import com.lighthawkwings.object.AbstractGameObject;
import com.lighthawkwings.object.GameObject;

public abstract class AbstractGameState<G extends Game> extends AbstractGameObject<G> implements GameState {
	/** Lista contendo os objetos do jogo */
	private List<GameObject> objectsList;

	/** Lista contendo os objetos do jogo que serão adicionados na próxima atualização */
	private List<GameObject> addList;

	/** Lista contendo os objetos do jogo que serão removidos na próxima atualização */
	private List<GameObject> removeList;

	/** Logger usado para a classe */
	protected Logger logger = Logger.getLogger(this.getClass());

	long frames;

	long t1;

	long t2;

	int currentFrames;

	int currentFps;

	Timer timer;

	public AbstractGameState(G game) {
		super(game);
		objectsList = new LinkedList<GameObject>();
		addList = new LinkedList<GameObject>();
		removeList = new LinkedList<GameObject>();
		frames = 0;
		t1 = 0;
		t2 = 0;
		currentFrames = 0;
		currentFps = 0;
		timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
            public void run() {
	            currentFps = currentFrames;
	            currentFrames = 0;
            }
		}, 1000, 1000);
	}

	public void init() {
		t1 = System.currentTimeMillis();
		logger.debug("Initializing state " + getClass() + " at " + t1 + " ms");
	}

	public void finish() {
		t2 = System.currentTimeMillis();
		logger.debug("Ending state at " + t2 + " ms");
		logger.debug("Frames: " + frames);
		logger.debug("Total time: " + (t2 - t1) + " ms");
		logger.debug("Average fps: " + (t2 - t1 != 0 ? ((float) frames / (t2 - t1) * 1000) : "-"));

		// chama os métodos para indicar o fim para os objetos deste estado
		for (Iterator<GameObject> iter = objectsList.iterator(); iter.hasNext();) {
			GameObject gameObject = iter.next();
			gameObject.finish();
		}
		objectsList.clear();
		for (Iterator<GameObject> iter = addList.iterator(); iter.hasNext();) {
			GameObject gameObject = iter.next();
			gameObject.finish();
		}
		addList.clear();
		for (Iterator<GameObject> iter = removeList.iterator(); iter.hasNext();) {
			GameObject gameObject = iter.next();
			gameObject.finish();
		}
		removeList.clear();

		// finaliza com o timer
		timer.cancel();
	}

	/**
	 * Adiciona um <code>GameObject</code> ao jogo.
	 *
	 * @param gameObject
	 *            Objeto que será adicionado ao jogo.
	 */
	public void addGameObject(GameObject gameObject) {
		addList.add(gameObject);
	}

	/**
	 * Retira um <code>GameObject</code> ao jogo.
	 *
	 * @param gameObject
	 *            Objeto que será retirado do jogo.
	 */
	public void removeGameObject(GameObject gameObject) {
		removeList.add(gameObject);
	}

	/**
	 * Retira todos os <code>GameObject</code> do jogo na próxima atualização.
	 */
	public void clearGameObjects() {
		removeList.addAll(objectsList);
	}

	/**
	 * @return Retorna a lista de objetos.
	 */
	public Collection<GameObject> getObjects() {
		return this.objectsList;
	}

	public void paint(Graphics2D graphics) {
		Collections.sort(objectsList);
		for (GameObject gameObject : objectsList) {
			gameObject.paint(graphics);
		}
		frames++;
		currentFrames++;
	}

	/**
	 * <p>
	 * Método que atualiza os <code>GameObject</code>s do jogo.
	 * </p>
	 *
	 * @param elapsedTime
	 *            Tempo que se passou desde a última chamada ao método, em ms.
	 */
	public void update(long elapsedTime) {
		for (GameObject gameObject : objectsList) {
			gameObject.update(elapsedTime);
		}
	}

	/**
	 * Atualiza a lista de objetos antes de iterar por ela, adicionando e removendo os objetos que estão na fila e
	 * ordenando tudo.
	 */
	public synchronized void updateGameObjectsList() {
		// adiciona e remove os objetos que se encontram nas listas de espera
		objectsList.addAll(addList);
		addList.clear();
		objectsList.removeAll(removeList);
		removeList.clear();
	}

	public int getCurrentFps() {
    	return currentFps;
    }
}

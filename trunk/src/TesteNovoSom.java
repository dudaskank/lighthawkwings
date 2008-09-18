import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.lighthawkwings.sound.sampled.PlayingStreamed;
import com.lighthawkwings.sound.sampled.Sampled;
import com.lighthawkwings.sound.sampled.SampledSoundManager;
import com.lighthawkwings.sound.sampled.Sound;
import com.lighthawkwings.sound.sampled.DefaultSampledSoundManager;

public class TesteNovoSom {
	public void testa() {
		try {
			Sampled boom = new Sound(getClass().getResource("/data/sounds/boom.wav"));
			SampledSoundManager soundManager = new DefaultSampledSoundManager();
			soundManager.init();
			System.out.println("Tocando boom, formato " + boom.getFormat());
			PlayingStreamed boomPlaying = soundManager.play(boom);
			while (!boomPlaying.isFinished()) {
			}
			System.out.println("Fim");
			Sampled boom2 = new Sound(getClass().getResource("/data/sounds/boom2.wav"));
			System.out.println("Tocando boom2, formato " + boom2.getFormat());
			PlayingStreamed boom2Playing = soundManager.play(boom2);
			while (!boom2Playing.isFinished()) {
			}
			System.out.println("Fim 2");
			soundManager.finish();
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Sorry, no sound for you.");
		} catch (IOException e) {
			System.out.println("Sorry, no sound for you.");
		}
	}

	public static void main(String[] args) {
		TesteNovoSom som = new TesteNovoSom();
		som.testa();
	}
}

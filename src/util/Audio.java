package util;

import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Audio {

	private static Clip clip;
	private static AudioInputStream hit;
//	private static AudioInputStream pickup;

	
	public Audio(){
		try {
			clip = AudioSystem.getClip();
			hit = AudioSystem.getAudioInputStream(new FileInputStream("aud/hit.wav"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void init(){
		try {
			clip = AudioSystem.getClip();
			hit = AudioSystem.getAudioInputStream(new FileInputStream("aud/hit.wav"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void playHit(){
		new Thread(new Runnable() { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see comments
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(new FileInputStream("aud/hit.wav"));
					clip.open(inputStream);
					clip.start(); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}
	
}

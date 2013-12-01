package octos.scrummer;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

/**
 * Plays alarm sound. 
 */
public class AlarmSoundPlayer {

	private Context context;
	
	public AlarmSoundPlayer(Context context) {
		this.context = context;
	}
	
	public void playSound() {
		MediaPlayer mp = MediaPlayer.create(context, R.raw.alarm);
		mp.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
		mp.start();
	}
}
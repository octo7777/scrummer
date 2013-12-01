package octos.scrummer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ScrummerActivity extends Activity {

	private Counter counter;
	
	private TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    text = (TextView) findViewById(R.id.time_passed);
	}

	public synchronized void startCountingAction(View view) {
		stopCounter();
		startCounter();
	}

	public synchronized void stopCountingAction(View view) {
		stopCounter();
		text.setText(R.string.default_time);
	}

	synchronized void counterFinishedHandler() {
		new AlarmSoundPlayer(this).playSound();
		startCounter();
	}
	
	private void startCounter() {
		counter = new Counter(this);
		new Thread(counter).start();
	}

	private void stopCounter() {
		if (counter != null) {
			counter.stop();
		}
	}
	
	void updateTime(final String time) {
		text.post(new Runnable() {
			public void run() {
				text.setText(time);
			}
		});
	}
}



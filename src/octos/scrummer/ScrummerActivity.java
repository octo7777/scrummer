package octos.scrummer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import static octos.scrummer.SettingsActivity.DEFAULT_MAXIMUM_TIME;
import static octos.scrummer.SettingsActivity.MAXIMUM_TIME;;

public class ScrummerActivity extends Activity {

	static final String PREFERENCE_SETTINGS_KEY = "octos.scrummer.SETTINGS";
	
	private Counter counter;

	private TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text = (TextView) findViewById(R.id.time_passed);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_settings:
			openSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void openSettings() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
	
	@Override
	protected void onPause() {
		stopCounter();
		super.onPause();
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

	public int getTimeLimit() {
		return getSharedPreferences(PREFERENCE_SETTINGS_KEY, Context.MODE_PRIVATE)
				.getInt(MAXIMUM_TIME, DEFAULT_MAXIMUM_TIME);
	}
}

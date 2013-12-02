package octos.scrummer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import static octos.scrummer.ScrummerActivity.PREFERENCE_SETTINGS_KEY;

public class SettingsActivity extends Activity {

	static final String SECOND_WARNING = "second_warning";

	static final String FIRST_WARNING = "first_warning";

	static final String MAXIMUM_TIME = "maximum_time";

	static final Integer DEFAULT_MAXIMUM_TIME = 60;
	
	static final Integer FIRST_WARNING_AFTER = 30;
	
	static final Integer SECOND_WARNING_AFTER = 45;

	private EditText maximumTimeText;

	private EditText firstWarningText;

	private EditText secondWarningText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		maximumTimeText = ((EditText) findViewById(R.id.maximum_time));
		firstWarningText = ((EditText) findViewById(R.id.first_warning)); 
		secondWarningText = ((EditText) findViewById(R.id.second_warning)); 

		maximumTimeText.addTextChangedListener(new SettingsTextWatcher(MAXIMUM_TIME));
		firstWarningText.addTextChangedListener(new SettingsTextWatcher(FIRST_WARNING));
		secondWarningText.addTextChangedListener(new SettingsTextWatcher(SECOND_WARNING));
		
		refreshSettingsView();
	}

	public void resetToDefault(View view) {
		SharedPreferences.Editor editor = getSharedPreferences(PREFERENCE_SETTINGS_KEY, Context.MODE_PRIVATE).edit();
		editor.putInt(MAXIMUM_TIME, DEFAULT_MAXIMUM_TIME);
		editor.putInt(FIRST_WARNING, FIRST_WARNING_AFTER);
		editor.putInt(SECOND_WARNING, SECOND_WARNING_AFTER);
		editor.commit();
		
		refreshSettingsView();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    

	private void refreshSettingsView() {
		SharedPreferences sharedPref = getSharedPreferences(PREFERENCE_SETTINGS_KEY, Context.MODE_PRIVATE);
		
		int maximumTime = sharedPref.getInt(MAXIMUM_TIME, DEFAULT_MAXIMUM_TIME);
		int firstWarningAfter = sharedPref.getInt(FIRST_WARNING, FIRST_WARNING_AFTER);
		int secondWarningAfter = sharedPref.getInt(SECOND_WARNING, SECOND_WARNING_AFTER);
		
		maximumTimeText.setText(maximumTime + "");
		firstWarningText.setText(firstWarningAfter + "");
		secondWarningText.setText(secondWarningAfter + "");
	}
    
	private class SettingsTextWatcher implements TextWatcher {
		private String key;
		
		public SettingsTextWatcher(String key) {
			this.key = key;
		}

		public void afterTextChanged(Editable s) {
			try {
				saveValue(key, Integer.parseInt(s.toString()));
			} catch (NumberFormatException e) { }
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {}

		private void saveValue(String key, int value) {
			SharedPreferences.Editor editor = getSharedPreferences(PREFERENCE_SETTINGS_KEY, Context.MODE_PRIVATE).edit();
			editor.putInt(key, value);
			editor.commit();
		}
	}
}

package fnn.smirl.cardgame.subactivity;
import android.widget.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import fnn.smirl.cardgame.R;
import fnn.smirl.cardgame.MainActivity;
import fnn.smirl.cardgame.object.core.enums.AILevel;
import fnn.smirl.cardgame.util.Toaster;

public class OptionActivity extends Activity {
 @Override
 protected void onCreate(Bundle savedInstanceState) {
	// TODO: Implement this method
	super.onCreate(savedInstanceState);
	setContentView(R.layout.options_activity);
	init();
 }

 @Override
 protected void onStart() {
	// TODO: Implement this method
	super.onStart();
	MainActivity.play(3, MainActivity.table.musicSound);
 }



 private void init() {

	Button resetStatistics = (Button)findViewById(R.id.resetStatistics);
	resetStatistics.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View p1) {

		 MainActivity.table.reset();
		 MainActivity.update();
		 Toaster.
			toast(getApplicationContext(), "Statistics reset successfully", false);
		}
	 });

	final Spinner optionMaxScoreSpinner =(Spinner)
	 findViewById(R.id.optionsMaxScoreSpinner);
	ArrayAdapter<CharSequence> adapter =
	 ArrayAdapter.createFromResource(getApplicationContext(), R.array.max_score,
																	 R.layout.drop_down_model);
	adapter.setDropDownViewResource(R.layout.drop_down_model);
	optionMaxScoreSpinner.setAdapter(adapter);
	optionMaxScoreSpinner.setOnItemSelectedListener(new
	 OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4) {

		 MainActivity.table.max_score = Integer.parseInt(p1.getItemAtPosition(p3).toString().trim());
		 MainActivity.update();
		}

		@Override
		public void onNothingSelected(AdapterView<?> p1) {
		 // TODO: Implement this method
		}
	 });
	optionMaxScoreSpinner.setSelection(
	 adapter.getPosition("" + MainActivity.table.max_score));

	// volumes
	SeekBar seek_music_vol = (SeekBar)findViewById(R.id.seek_music_vol);
	seek_music_vol.setMax(100);
	seek_music_vol.setProgress((int)(MainActivity.table.musicSound * 100));
	seek_music_vol.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

		@Override
		public void onProgressChanged(SeekBar p1, int p2, boolean p3) {
		 // TODO: Implement this method
		 float f = (p2 / 100.0f);
		 MainActivity.gameSounds.setVolume(f);
		 MainActivity.table.musicSound = f;
		}

		@Override
		public void onStartTrackingTouch(SeekBar p1) {
		 // TODO: Implement this method
		}

		@Override
		public void onStopTrackingTouch(SeekBar p1) {
		 // TODO: Implement this method
		 MainActivity.update();
		}
	 });


	SeekBar seek_click_vol = (SeekBar)findViewById(R.id.seek_click_vol);
	seek_click_vol.setMax(100);
	seek_click_vol.setProgress((int)(MainActivity.table.clickSound * 100));
	seek_click_vol.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

		@Override
		public void onProgressChanged(SeekBar p1, int p2, boolean p3) {
		 // TODO: Implement this method
		 float f = (p2 / 100.0f);
		 // MainActivity.gameSounds.setVolume(f);
		 MainActivity.table.clickSound = f;
		}

		@Override
		public void onStartTrackingTouch(SeekBar p1) {
		 // TODO: Implement this method
		}

		@Override
		public void onStopTrackingTouch(SeekBar p1) {
		 // TODO: Implement this method
		 MainActivity.update();
		}
	 });

	// level spinner
	final Spinner levelSpinner =(Spinner)
	 findViewById(R.id.level_spinner);
	ArrayAdapter<Integer> levelAdapter =
	 new ArrayAdapter<Integer>(getApplicationContext(), R.layout.drop_down_model, AILevel.getLevels());
	levelAdapter.setDropDownViewResource(R.layout.drop_down_model);
	levelSpinner.setAdapter(levelAdapter);
	levelSpinner.setOnItemSelectedListener(new
	 OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4) {

		 int v = Integer.parseInt(p1.getItemAtPosition(p3).toString().trim());
		 switch (v) {
			case 0:
			 MainActivity.table.level = AILevel.ROOKIE;
			 break;
			case 1:
			 MainActivity.table.level = AILevel.BEGINNER;
			 break;
			case 2:
			 MainActivity.table.level = AILevel.INTERMEDIATE;
			 break;
			case 3:
			 MainActivity.table.level = AILevel.ADVANCED;
			 break;
			case 4:
			 MainActivity.table.level = AILevel.PROFESSIONAL;
			 break;
			default:
			 MainActivity.table.level = AILevel.ROOKIE;
		 }
		 MainActivity.update();
		}

		@Override
		public void onNothingSelected(AdapterView<?> p1) {
		 // TODO: Implement this method
		}
	 });
	levelSpinner.setSelection(
	 levelAdapter.getPosition(MainActivity.table.level.getLevel()));

 }

 @Override
 public void onBackPressed() {
	// TODO: Implement this method
	super.onBackPressed();
	MainActivity.pause(3);
 }



}

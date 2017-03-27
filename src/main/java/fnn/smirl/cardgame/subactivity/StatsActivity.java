package fnn.smirl.cardgame.subactivity;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import fnn.smirl.cardgame.R;
import fnn.smirl.cardgame.MainActivity;
import fnn.smirl.cardgame.util.Statistique;
import fnn.smirl.cardgame.util.adapters.StatsAdapter;

public class StatsActivity extends Activity {

 @Override
 protected void onCreate(Bundle savedInstanceState) {
	// TODO: Implement this method
	super.onCreate(savedInstanceState);
	setContentView(R.layout.stats_activity);
	init();
 }

 private void init() {
	ListView list = (ListView)findViewById(R.id.stats_list);
	StatsAdapter adapter = new StatsAdapter(getApplicationContext(),
																					MainActivity.table.stats);
	list.setAdapter(adapter);

	TextView win_v = (TextView)findViewById(R.id.stats_bw);
	TextView loss_v = (TextView)findViewById(R.id.stats_wl);

	Statistique bw = MainActivity.table.bestWin();
	Statistique wl = MainActivity.table.worstLoss();

	String ww = "Meilleur score : " + bw.user + "/" + bw.comp;
	String ll = "Pire score : " + wl.user + "/" + wl.comp;
	win_v.setText(ww);
	loss_v.setText(ll);

 }
 
}

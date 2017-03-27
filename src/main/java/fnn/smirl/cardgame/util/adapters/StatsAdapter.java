package fnn.smirl.cardgame.util.adapters;
import android.view.*;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fnn.smirl.cardgame.R;
import fnn.smirl.cardgame.util.Statistique;
import java.util.ArrayList;

public class StatsAdapter extends ArrayAdapter<Statistique>
{
 private Context ctx;
 
 public StatsAdapter(Context ctx, ArrayList<Statistique> stats){
	super(ctx, 0, stats);
	this.ctx = ctx;
 }

 @Override
 public View getView(int position, View convertView, ViewGroup parent) {
	// TODO: Implement this method
	final Statistique stat = getItem(position);
	if(convertView == null){
	 convertView = LayoutInflater.from(ctx)
	 .inflate(R.layout.stats_model, parent, false);
	}
	
	TextView tv1 = (TextView)convertView.findViewById(R.id.tv1);
	TextView tv2 = (TextView)convertView.findViewById(R.id.tv2);
	TextView tv3 = (TextView)convertView.findViewById(R.id.tv3);
	TextView tv4 = (TextView)convertView.findViewById(R.id.tv4);
	TextView tv5 = (TextView)convertView.findViewById(R.id.tv5);
	
	tv1.setText(stat.id + "");
	tv2.setText(stat.user + "");
	tv3.setText(stat.comp + "");
	tv4.setText(stat.diff + "");
	tv5.setText((int)(stat.percent * 100) + "%");
	
	return convertView;
 }
 
 
}

package dam.project.movieproj.data;

import java.util.ArrayList;


import dam.project.movieproj.*;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavDrawerListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<NavigationDrawerItem> navDrawerItems;
	
	public NavDrawerListAdapter(Context context,ArrayList<NavigationDrawerItem> navDrawerItems){
		this.context=context;
		this.navDrawerItems=navDrawerItems;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			LayoutInflater mInflater=(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView=mInflater.inflate(R.layout.drawer_list_item, null);
		}
		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
		TextView textTitle = (TextView) convertView.findViewById(R.id.title);
		TextView textCount = (TextView) convertView.findViewById(R.id.counter);
		
		imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
		textTitle.setText(navDrawerItems.get(position).getTitle());
		 if(navDrawerItems.get(position).isCounterVisible()){
	            textCount.setText(navDrawerItems.get(position).getCount());
	        }else{
	            // hide the counter view
	            textCount.setVisibility(View.GONE);
	        }
	         
	        return convertView;
		
	}

}

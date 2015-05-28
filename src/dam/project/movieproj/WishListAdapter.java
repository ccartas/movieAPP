package dam.project.movieproj;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import dam.project.movieproj.data.DatabaseHandler;
import dam.project.movieproj.data.Movie;
import dam.project.movieproj.data.SessionManager;
import dam.project.movieproj.data.User;

public class WishListAdapter {
/*extends ArrayAdapter<Movie> {

	private Context context;
	private List<Movie> objects;
	DatabaseHandler db;
	User user = new User();
	SessionManager session;

	public WishListAdapter(Context context, int resource, List<Movie> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.objects = objects;
		db = DatabaseHandler.getSingleton(context);
		session = new SessionManager(context);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		Movie movie = objects.get(position);
		user = session.getUserDetails();
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.wishlist_items_layout, null);

		ImageView image = (ImageView) view.findViewById(R.id.seen_item_pic);

		image.setImageResource(movie.getId_pic());
		TextView tv = (TextView) view.findViewById(R.id.item_name);
		tv.setText(movie.getTitle());
		ImageButton btnSeen = (ImageButton) view.findViewById(R.id.btnSeen);
		btnSeen.setFocusable(false);
		btnSeen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				db.insertMovieFromWishToSeen(objects.get(position),
						db.getUser(user.getUsername(), user.getPassword()));

			}
		});
		return view;
	}
*/
}

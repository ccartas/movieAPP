package dam.project.movieproj.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import dam.project.movieproj.LoginActivity;
import dam.project.movieproj.R;
import dam.project.movieproj.SeenAdapter;
import dam.project.movieproj.data.DatabaseHandler;
import dam.project.movieproj.data.Movie;
import dam.project.movieproj.data.SessionManager;
import dam.project.movieproj.data.User;

public class SeenList extends Fragment {
/*	private List<Movie> items = new ArrayList<Movie>();
	User user = new User();
	SessionManager session;

	public SeenList() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//

		View view = inflater.inflate(R.layout.fragment_seenlist, container,
				false);

		return view;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		session = new SessionManager(getActivity());

		user = session.getUserDetails();
		try {
			DatabaseHandler db = DatabaseHandler.getSingleton(getActivity());
			items = db.getAllSeenMovies(db.getUser(user.getUsername(),
					user.getPassword()));

			ListView lv = (ListView) getActivity().findViewById(
					R.id.listViewSeen);
			SeenAdapter adapter = new SeenAdapter(getActivity(),
					R.layout.seen_items_layout, items);
			lv.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int location, long id) {
					// Movie movie = pareng.getItemAtPosition(position);
					int idMovie = items.get(location).getId_pic();
					String title = items.get(location).getTitle();
					String plot = items.get(location).getDescription();
					String year = items.get(location).getYear();
					String category = items.get(location).getCategory();
					String trailer = items.get(location).getTrailer();
					Intent intent = new Intent(getActivity(),
							MovieDetailsActivity.class);
					intent.putExtra("idMovie", idMovie);
					intent.putExtra("title", title);
					intent.putExtra("plot", plot);
					intent.putExtra("year", year);
					intent.putExtra("category", category);
					intent.putExtra("trailer", trailer);
					startActivity(intent);

				}
			});
		} catch (Exception ex) {

			Toast.makeText(getActivity(), "Please login!", Toast.LENGTH_LONG)
					.show();
			Intent i = new Intent(getActivity(), LoginActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			// Staring Login Activity
			getActivity().startActivity(i);
		}

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
*/
}

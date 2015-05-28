package dam.project.movieproj.fragments;

import org.json.JSONException;
import org.json.JSONObject;

import dam.project.movieproj.JSONParser;
import dam.project.movieproj.R;
import dam.project.movieproj.data.DatabaseHandler;
import dam.project.movieproj.data.Movie;
import dam.project.movieproj.data.MovieFactory;
import dam.project.movieproj.data.SessionManager;
import dam.project.movieproj.data.User;
import dam.project.movieproj.exceptions.NameContainsNumbersException;
import dam.project.movieproj.exceptions.NameTooShortException;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

public class AddMovie extends Fragment {

	Button addMovie;
	Button cancelMovie;
	EditText movieTitle;
	
	DatabaseHandler db;
	MovieFactory factory;
	User user = new User();
	SessionManager session;
	public String baseURL = "http://www.omdbapi.com/?t=";
	Uri uri;
	public JSONParse asyncTask;
	public static Movie m ;
	
	public AddMovie(){
		
	}

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		session = new SessionManager(getActivity());

		try {
			user = session.getUserDetails();
		} catch (NameTooShortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NameContainsNumbersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		View view = inflater.inflate(R.layout.fragment_addmovie, container, false);
		view.setBackground(getResources().getDrawable(user.getUserTheme().setUserTheme()));
		return view;
		
	}



	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		/*user = session.getUserDetails();*/
		db = DatabaseHandler.getSingleton(getActivity());
		factory = new MovieFactory();
		addMovie = (Button) getActivity().findViewById(R.id.addMovieBTN);
		cancelMovie = (Button) getActivity().findViewById(R.id.cancelMovieBTN);
		movieTitle = (EditText) getActivity().findViewById(R.id.movieTitleET);
		
		addMovie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*if(radioHorror.isChecked()){
					m = factory.createInstance(radioHorror.getText().toString());
					m.setCategory(radioHorror.getText().toString());
				}
				m.setYear(movieYear.getSelectedItem().toString());
				m.setTitle(movieTitle.getText().toString());
				m.setUsername(user.getUsername());
				db.insertRow(m);*/
				baseURL = baseURL + (movieTitle.getText().toString());
				baseURL = baseURL.replaceAll(" ", "%20");
				System.out.println("unde face request" + baseURL);
				if (isNetworkAvailable() == true) {
					asyncTask = (JSONParse) new JSONParse().execute();
			}
			}
		});
	
	
	
	}
	public  class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Getting data...");
			pDialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			JSONParser jParser = new JSONParser();
			// Getting JSON from URL
			JSONObject json = jParser.getJSONFromUrl(baseURL);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			pDialog.dismiss();
			try {
				/*String imdbRating = json.getString("imdbRating");
				String actors = json.getString("Actors");
				tvRating.setText(imdbRating);
				tvActors.setText("Actors:" + actors);*/
				if(json.getString("Response").equals("False")){
				Toast.makeText(getActivity(), "FAIL", Toast.LENGTH_LONG).show();
				}
				else
				{
					if(json.getString("Response").equals("True")){
						m=factory.createInstance(json.getString("Genre"));
						if(m!=null){
							try{
						//m.setCategory(json.getString("Genre"));
						m.setTitle(json.getString("Title"));
						m.setYear(json.getString("Year"));
						m.setDescription(json.getString("Plot"));
						m.setpicSource(json.getString("Poster"));
						m.setRating(Float.parseFloat(json.getString("imdbRating")));
						m.setUsername(user.getUsername());
							}
							catch(Exception ex){
								
							}
						db.insertRow(m);
						baseURL = "http://www.omdbapi.com/?t=";
						}
						
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}


	}

	
}
	/*@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (asyncTask != null)
			asyncTask.cancel(true);
	}
*/
	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	
	}
}

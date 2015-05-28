package dam.project.movieproj.fragments;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import dam.project.movieproj.JSONParser;
import dam.project.movieproj.R;

public class MovieDetailsActivity extends Activity {
	ImageView ivPhoto;
	TextView tvName, tvRating, tvCategory, tvYear, tvPlot, tvActors, tvTrailer;
	String baseURL = "http://www.omdbapi.com/?t=";
	Uri uri;
	JSONParse asyncTask;
	RelativeLayout myLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_details);
		
		//ivPhoto = (ImageView) findViewById(R.id.ivMoviePhoto);
		tvName = (TextView) findViewById(R.id.tvMovieName);
		tvRating = (TextView) findViewById(R.id.tvRating);
		tvCategory = (TextView) findViewById(R.id.tvCategory);
		tvPlot = (TextView) findViewById(R.id.tvPlot);
		tvYear = (TextView) findViewById(R.id.tvYear);
		tvActors = (TextView) findViewById(R.id.tvActors);
		//tvTrailer = (TextView) findViewById(R.id.tvClickToWatch);

		Bundle extra = getIntent().getExtras();
		//ivPhoto.setImageResource(extra.getInt("idMovie"));
		tvName.setText(extra.getString("title"));
		tvCategory.setText("Category: " + extra.getString("category"));
		tvPlot.setText(extra.getString("plot")); // e gol
		tvYear.setText(extra.getString("year"));
		//tvTrailer.setText("Click to watch");
		//uri = Uri.parse(extra.getString("trailer"));

		/*tvTrailer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(uri);
				startActivity(i);

			}
		});*/

		baseURL = baseURL + (tvName.getText().toString());
		baseURL = baseURL.replaceAll(" ", "%20");
		System.out.println("unde face request" + baseURL);
		if (isNetworkAvailable() == true) {
			asyncTask = (JSONParse) new JSONParse().execute();
		} else {
			tvRating.setText("Rating: Enable internet to see the ratings");
			tvActors.setText("Actors: Enable internet to see the actors");
		}
	}

	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MovieDetailsActivity.this);
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
				String imdbRating = json.getString("imdbRating");
				String actors = json.getString("Actors");
				tvRating.setText(imdbRating);
				tvActors.setText("Actors:" + actors);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (asyncTask != null)
			asyncTask.cancel(true);
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}

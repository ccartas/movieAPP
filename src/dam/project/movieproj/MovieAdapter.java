package dam.project.movieproj;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
import dam.project.movieproj.exceptions.NameContainsNumbersException;
import dam.project.movieproj.exceptions.NameTooShortException;

public class MovieAdapter extends ArrayAdapter<Movie> {

	private Context context;
	private List<Movie> objects;
	DatabaseHandler db;
	SessionManager session;
	User user = new User();
	Movie movie;
	TextView rating;

	public MovieAdapter(Context context, int resource, List<Movie> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.objects = objects;
		db = DatabaseHandler.getSingleton(context);
		session = new SessionManager(context);

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		movie = objects.get(position);
		try {
			user = session.getUserDetails();
		} catch (NameTooShortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NameContainsNumbersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.item_layout, null);

		ImageView image = (ImageView) view.findViewById(R.id.seen_item_pic);
		ImageButton btnWishlist = (ImageButton) view
				.findViewById(R.id.btnWishlist);
		ImageButton btnSeen = (ImageButton) view.findViewById(R.id.btnSeen);

		
		ImageLoadTask im = new ImageLoadTask(movie.getpicSource(), image);
		im.execute();
		TextView tv = (TextView) view.findViewById(R.id.item_name);
		tv.setText(movie.getTitle());

		rating = (TextView) view.findViewById(R.id.RatingImdb);
		btnWishlist.setFocusable(false);
		btnSeen.setFocusable(false);
		btnWishlist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// functia pentru wishlist aici
				// objects.get(position).setStatus("Wishlist");
				// db.addToWish(objects.get(position).getTitle());
				// Log.v("Adapter",StaticFields.username + " " +
				// objects.get(position).getTitle() + " " );

				try {
					db.insertRowsToWishList(objects.get(position),
							db.getUser(user.getUsername(), user.getPassword()));
				} catch (NameTooShortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NameContainsNumbersException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		btnSeen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// objects.get(position).setStatus("Seen");
				// db.addToSeen(objects.get(position).getTitle());
				// Log.v("Adapter",StaticFields.username + " " +
				// objects.get(position).getTitle() + " " );
				try {
					db.insertRowsToSeen(objects.get(position),
							db.getUser(user.getUsername(), user.getPassword()));
				} catch (NameTooShortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NameContainsNumbersException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		return view;
	}
	public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

	    private String url;
	    private ImageView imageView;

	    public ImageLoadTask(String url, ImageView imageView) {
	        this.url = url;
	        this.imageView = imageView;
	    }

	    @Override
	    protected Bitmap doInBackground(Void... params) {
	        try {
	            URL urlConnection = new URL(url);
	            HttpURLConnection connection = (HttpURLConnection) urlConnection
	                    .openConnection();
	            connection.setDoInput(true);
	            connection.connect();
	            InputStream input = connection.getInputStream();
	            Bitmap myBitmap = BitmapFactory.decodeStream(input);
	            return myBitmap;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    @Override
	    protected void onPostExecute(Bitmap result) {
	        super.onPostExecute(result);
	        imageView.setImageBitmap(result);
	    }

	}
}

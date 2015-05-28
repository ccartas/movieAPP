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
import dam.project.movieproj.MovieAdapter;
import dam.project.movieproj.R;
import dam.project.movieproj.data.ActionMovie;
import dam.project.movieproj.data.AnimationMovie;
import dam.project.movieproj.data.DatabaseHandler;
import dam.project.movieproj.data.DramaMovie;
import dam.project.movieproj.data.Movie;
import dam.project.movieproj.data.MovieFactory;
import dam.project.movieproj.data.MysteryMovie;
import dam.project.movieproj.data.RomanceMovie;
import dam.project.movieproj.data.SciFiMovie;
import dam.project.movieproj.data.SessionManager;
import dam.project.movieproj.data.ThrillerMovie;
import dam.project.movieproj.data.User;
import dam.project.movieproj.data.WarMovie;
import dam.project.movieproj.exceptions.NameContainsNumbersException;
import dam.project.movieproj.exceptions.NameTooShortException;

public class AllMovies extends Fragment {

	private List<Movie> items = new ArrayList<Movie>();
	private MovieFactory mFactory= new MovieFactory();
	User user = new User();
	SessionManager session;

	public AllMovies() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// DatabaseHandler db = new
		// DatabaseHandler(getActivity().getApplicationContext());
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
		View view = inflater.inflate(R.layout.fragment_all_movies, container,
				false);
		view.setBackground(getResources().getDrawable(user.getUserTheme().setUserTheme()));
		return view;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		DatabaseHandler db =  DatabaseHandler.getSingleton(getActivity());
		
		/*db.insertRow(new SciFiMovie(
				"Interstellar",
				R.drawable.interstelar,
				"2014",
				"Sci-Fi",
				9,
				"A team of explorers travel through a wormhole in an attempt to find a potentially habitable planet that will sustain humanity.",
				"http://www.imdb.com/title/tt0816692/"));
		db.insertRow(new MysteryMovie(
				"Gone Girl",
				R.drawable.gonegirl,
				"2014",
				"Mystery",
				8,
				"With his wife's disappearance having become the focus of an intense media circus, a man sees the spotlight turned on him when it's suspected that he may not be innocent",
				"http://www.imdb.com/title/tt2267998/"));
		db.insertRow(new SciFiMovie(
				"Divergent",
				R.drawable.divergent,
				"2014",
				"Sci-Fi",
				9,
				"In a world divided by factions based on virtues, Tris learns she's Divergent and won't fit in. When she discovers a plot to destroy Divergents, Tris and the mysterious Four must find out what makes Divergents dangerous before it's too late.",
				"http://www.imdb.com/title/tt1840309/"));

		db.insertRow(new RomanceMovie(
				"The Notebook",
				R.drawable.notebook,
				"2004",
				"Romance",
				8,
				"A poor and passionate young man falls in love with a rich young woman and gives her a sense of freedom. They soon are separated by their social differences.",
				"https://www.youtube.com/watch?v=S3G3fILPQAU"));
		db.insertRow(new ThrillerMovie(
				"Taken",
				R.drawable.taken,
				"2008",
				"Thriller",
				8,
				"A retired CIA agent travels across Europe and relies on his old skills to save his estranged daughter, who has been kidnapped while on a trip to Paris.",
				"http://www.imdb.com/title/tt0936501/"));
		db.insertRow(new RomanceMovie(
				"Serendipity",
				R.drawable.serendipity,
				"2001",
				"Romance",
				7,
				"A couple reunite years after the night they first met, fell in love, and separated, convinced that one day they'd end up together.",
				"https://www.youtube.com/watch?v=ePU2Ux9JIMM"));
		db.insertRow(new ActionMovie(
				"Lucy",
				R.drawable.lucy,
				"2014",
				"Action",
				6,
				"A woman, accidentally caught in a dark deal, turns the tables on her captors and transforms into a merciless warrior evolved beyond human logic.",
				"http://www.imdb.com/title/tt2872732/"));
		db.insertRow(new WarMovie(
				"Fury",
				R.drawable.fury,
				"2014",
				"War",
				8,
				"April, 1945. As the Allies make their final push in the European Theatre, a battle-hardened army sergeant named Wardaddy commands a Sherman tank and his five-man crew on a deadly mission behind enemy lines. Out-numbered, out-gunned, and with a rookie soldier thrust into their platoon, Wardaddy and his men face overwhelming odds in their heroic attempts to strike at the heart of Nazi Germany.",
				"http://www.imdb.com/title/tt2713180/?ref_=fn_al_tt_1"));
		db.insertRow(new AnimationMovie(
				"Frozen",
				R.drawable.frozen,
				"2013",
				"Animation",
				8,
				"When a princess with the power to turn things into ice curses her home in infinite winter, her sister, Anna teams up with a mountain man, his playful reindeer, and a snowman to change the weather condition.",
				"http://www.imdb.com/title/tt2294629/?ref_=nv_sr_1"));

		db.insertRow(new DramaMovie(
				"The Vow",
				R.drawable.thevow,
				"2012",
				"Drama",
				8,
				"A car accident puts Paige in a coma, and when she wakes up with severe memory loss, her husband Leo works to win her heart again.",
				"http://www.imdb.com/title/tt1606389/?ref_=fn_al_tt_1"));
		db.insertRow(new DramaMovie(
				"A Walk To Remember",
				R.drawable.walk,
				"2002",
				"Drama",
				8,
				"The story of two North Carolina teens, Landon Carter and Jamie Sullivan, who are thrown together after Landon gets into trouble and is made to do community service.",
				" "));
		db.insertRow(new SciFiMovie(
				"Inception ",
				R.drawable.inception,
				"2010",
				"Sci-Fi",
				9,
				"A thief who steals corporate secrets through use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.",
				"http://www.imdb.com/title/tt1375666/"));
		db.insertRow(new MysteryMovie(
				"The Prestige",
				R.drawable.prestige,
				"2006",
				"Mystery",
				8,
				"Two stage magicians engage in competitive one-upmanship in an attempt to create the ultimate stage illusion.",
				""));
		db.insertRow(new DramaMovie(
				"Fight Club",
				R.drawable.fight,
				"1999",
				"Drama",
				9,
				"An insomniac office worker looking for a way to change his life crosses paths with a devil-may-care soap maker and they form an underground fight club that evolves into something much, much more...",
				""));
		db.insertRow(new DramaMovie(
				"If I Stay",
				R.drawable.stay,
				"2014",
				"Drama",
				7,
				"Life changes in an instant for young Mia Hall after a car accident puts her in a coma. During an out-of-body experience, she must decide whether to wake up and live a life far different than she had imagined.The choice is hers if she can go on.",
				"http://www.imdb.com/title/tt1355630/"));
		db.insertRow(new DramaMovie(
				"The Godfather",
				R.drawable.godfather,
				"1972",
				"Drama",
				10,
				"The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
				"http://www.imdb.com/title/tt0068646/"));
		db.insertRow(new DramaMovie(
				"Titanic",
				R.drawable.titanic,
				"1997",
				"Drama",
				8,
				"A seventeen-year-old aristocrat, expecting to be married to a rich claimant by her mother, falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.",
				"http://www.imdb.com/title/tt0120338/"));
				*/
		items = db.getAllMovies(user);

		ListView lv = (ListView) getActivity()
				.findViewById(R.id.listViewFromDB);
		MovieAdapter adapter = new MovieAdapter(getActivity(),
				R.layout.item_layout, items);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int location, long id) {
				String picSource = items.get(location).getpicSource();
				String title = items.get(location).getTitle();
				String plot = items.get(location).getDescription();
				String year = items.get(location).getYear();
				String category = items.get(location).getCategory();
				//String trailer = items.get(location).getTrailer();

				Intent intent = new Intent(getActivity(),
						MovieDetailsActivity.class);
				intent.putExtra("picSource", picSource);
				intent.putExtra("title", title);
				intent.putExtra("plot", plot);
				intent.putExtra("year", year);
				intent.putExtra("category", category);
				//intent.putExtra("trailer", trailer);
				startActivity(intent);

			}
		});

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	/*
	 * @Override public void onCreateOptionsMenu(Menu menu, MenuInflater
	 * inflater) { // TODO Auto-generated method stub
	 * 
	 * inflater.inflate(R.menu.allmovies_main_menu, menu); SearchManager
	 * searchManager = (SearchManager)
	 * getActivity().getSystemService(Context.SEARCH_SERVICE); SearchView
	 * searchView = (SearchView) menu.findItem(R.id.action_search)
	 * .getActionView(); searchView.setSearchableInfo(searchManager
	 * .getSearchableInfo(getActivity().getComponentName()));
	 * super.onCreateOptionsMenu(menu, inflater);
	 * 
	 * }
	 */

}

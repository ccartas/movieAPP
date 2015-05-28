package dam.project.movieproj.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dam.project.movieproj.exceptions.NameContainsNumbersException;
import dam.project.movieproj.exceptions.NameTooShortException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "movieManager";

	// USERS TABLE
	private static final String TABLE_USERS = "users";
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_FACEBOOK = "facebook_profile";
	private static final String KEY_SEX = "sex";
	private static final String KEY_VARSTA = "varsta";

	// MOVIES TABLE

	public static final String TABLE_MOVIES = "movies";
	public static final String KEY_IDM = "id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_PICTURE = "pic_source";
	public static final String KEY_YEAR = "year";
	public static final String KEY_CATEGORY = "category";
	public static final String KEY_RATING = "rating";
	private static final String KEY_STATUS = "status";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_TRAILER = "trailer";

	// USER_MOVIES TABLE
	public static final String TABLE_USER_MOVIES = "user_movies";
	public static final String KEY_ID_UM = "id";

	private static DatabaseHandler self;
	public static MovieFactory factory = new MovieFactory();
	
	private DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public static DatabaseHandler getSingleton(Context context){
		if(self == null){
			self = new DatabaseHandler(context);
		}
		return self;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS
				+ "(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + KEY_NAME
				+ " TEXT NOT NULL, " + KEY_USERNAME + " TEXT NOT NULL, "
				+ KEY_PASSWORD + " TEXT NOT NULL," + KEY_EMAIL
				+ " TEXT NOT NULL, " + KEY_FACEBOOK + " TEXT, " + KEY_SEX
				+ " TEXT, " + KEY_VARSTA + " INTEGER " + ")";
		db.execSQL(CREATE_USERS_TABLE);
		String CREATE_MOVIES_TABLE = "CREATE TABLE IF NOT EXISTS "
				+ TABLE_MOVIES + "(" + KEY_IDM
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + KEY_TITLE
				+ " TEXT , " + KEY_PICTURE + " TEXT, " + KEY_YEAR
				+ " TEXT," + KEY_CATEGORY + " TEXT, " + KEY_RATING
				+ " NUMERIC, " + KEY_DESCRIPTION + " TEXT, " + KEY_TRAILER
				+ " TEXT " + ", " + KEY_USERNAME + " TEXT" +")";
		db.execSQL(CREATE_MOVIES_TABLE);
		String CREATE_USER_MOVIES_TABLE = "CREATE TABLE IF NOT EXISTS "
				+ TABLE_USER_MOVIES + " ( " + KEY_ID_UM
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + KEY_TITLE
				+ " TEXT, " + KEY_USERNAME + " TEXT, " + KEY_STATUS + " TEXT, "
				+ KEY_PICTURE + " TEXT, " + KEY_YEAR + " TEXT, " + KEY_CATEGORY
				+ " TEXT, " + KEY_DESCRIPTION + " TEXT, " + KEY_TRAILER
				+ " TEXT" + ")";
		db.execSQL(CREATE_USER_MOVIES_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_MOVIES);
		onCreate(db);
	}

	public void addUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, user.getName());
		values.put(KEY_USERNAME, user.getUsername());
		values.put(KEY_PASSWORD, user.getPassword());
		values.put(KEY_EMAIL, user.getEmail());
		values.put(KEY_FACEBOOK, user.getFacebookProf());
		values.put(KEY_SEX, user.getSex());
		values.put(KEY_VARSTA, user.getVarsta());

		db.insert(TABLE_USERS, null, values);
		db.close();

	}

	public User getUser(String username, String password) throws NameTooShortException, NameContainsNumbersException {
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * from users where username='" + username
				+ "' and password='" + password + "'";
		Cursor cursor = db.rawQuery(query, null);
		User user = new User();
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			try{
			user.setId(Integer.parseInt(cursor.getString(0)));
			user.setName(cursor.getString(1));
			user.setUsername(cursor.getString(2));
			user.setPassword(cursor.getString(3));
			user.setEmail(cursor.getString(4));
			user.setFacebookProf(cursor.getString(5));
			user.setSex(cursor.getString(6));
			user.setVarsta(Integer.parseInt(cursor.getString(7)));
			}
			catch(Exception ex){
				
			}
			cursor.close();
		} else {
			user = null;
			cursor.close();
		}

		return user;
	}

	public User checkUser(String username) throws NameTooShortException, NameContainsNumbersException {
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * from users where username='" + username + "'";
		Cursor cursor = db.rawQuery(query, null);
		User user = new User();
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			try{
			user.setId(Integer.parseInt(cursor.getString(0)));
			user.setName(cursor.getString(1));
			user.setUsername(cursor.getString(2));
			user.setPassword(cursor.getString(3));
			user.setEmail(cursor.getString(4));
			user.setFacebookProf(cursor.getString(5));
			user.setSex(cursor.getString(6));
			user.setVarsta(Integer.parseInt(cursor.getString(7)));
			}
			catch(Exception ex){
				
			}
			cursor.close();
		} else {
			user = null;
			cursor.close();
		}

		return user;
	}

	public boolean updateUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EMAIL, user.getEmail());
		values.put(KEY_FACEBOOK, user.getFacebookProf());
		values.put(KEY_PASSWORD, user.getPassword());

		int i = db.update(TABLE_USERS, values, KEY_USERNAME + "=?",
				new String[] { user.getUsername() });
		return i > 0;
	}

	public String getMaxCategory(User user){
		String categorie ="";
		int nr_rows = 0;
		String selectQuery1 = "SELECT COUNT(*) FROM " + TABLE_MOVIES;
		String selectQuery = "SELECT MAX(some_count), " + KEY_CATEGORY + " FROM (SELECT COUNT(DISTINCT "+ KEY_TITLE + ") as some_count,"+ KEY_CATEGORY + " FROM " + TABLE_MOVIES + " WHERE " + KEY_USERNAME + "='"+user.getUsername()+"' GROUP BY "+ KEY_CATEGORY+");";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c1 = db.rawQuery(selectQuery1, null);
		if(c1.moveToFirst()){
			do{
				nr_rows = Integer.parseInt(c1.getString(0));
			} while (c1.moveToNext());
		}
		if(nr_rows > 0){
		Cursor cursor = db.rawQuery(selectQuery, null);
		if(cursor.moveToFirst()){
			do{
				categorie = cursor.getString(1);
			} while (cursor.moveToNext());
		}
		}
		return categorie;
	}
	public List<Movie> getAllMovies(User user) {
		List<Movie> movieList = new ArrayList<Movie>();
		String selectQuery = "SELECT * FROM " + TABLE_MOVIES + " WHERE " + KEY_USERNAME + "='"+user.getUsername()+"' GROUP BY " + KEY_TITLE;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
			
				Movie m = factory.createInstance(cursor.getString(4));
				try{
				m.setId(Integer.parseInt(cursor.getString(0)));
				m.setTitle(cursor.getString(1));
				m.setpicSource(cursor.getString(2));
				m.setYear(cursor.getString(3));
				m.setCategory(cursor.getString(4));
				m.setRating(Float.parseFloat(cursor.getString(5)));
				m.setDescription(cursor.getString(6));
				}
				catch(Exception ex){
					
				}
				//m.setTrailer(cursor.getString(7));
				movieList.add(m);
			} while (cursor.moveToNext());
		}
		return movieList;
		/*
		 * if(cursor != null){ cursor.moveToFirst(); } return cursor;
		 */
	}

	public List<Movie> getAllSeenMovies(User user) {
		List<Movie> movieList = new ArrayList<Movie>();
		String selectQuery = "SELECT DISTINCT title,id_picture,year,category,description,trailer FROM "
				+ TABLE_USER_MOVIES
				+ " WHERE "
				+ KEY_USERNAME
				+ " = '"
				+ user.getUsername() + "'" + " AND " + KEY_STATUS + " = 'Seen'";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Movie m = factory.createInstance(cursor.getString(4));
				try{
				m.setTitle(cursor.getString(0));
				m.setpicSource(cursor.getString(1));
				m.setYear(cursor.getString(2));
				m.setCategory(cursor.getString(3));
				m.setDescription(cursor.getString(4));
				m.setTrailer(cursor.getString(5));
				}
				catch(Exception ex){
					
				}
				movieList.add(m);
			} while (cursor.moveToNext());
		}
		return movieList;
	}

	public List<Movie> getAllWishedMovies(User user) {
		List<Movie> movieList = new ArrayList<Movie>();
		String selectQuery = "SELECT DISTINCT title,id_picture,year,category,description,trailer FROM "
				+ TABLE_USER_MOVIES
				+ " WHERE "
				+ KEY_USERNAME
				+ " = '"
				+ user.getUsername()
				+ "'"
				+ " AND "
				+ KEY_STATUS
				+ " = 'Wishlist'";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Movie m = factory.createInstance(cursor.getString(4));
				try{
				m.setTitle(cursor.getString(0));
				m.setpicSource(cursor.getString(1));
				m.setYear(cursor.getString(2));
				m.setCategory(cursor.getString(3));
				m.setDescription(cursor.getString(4));
				m.setTrailer(cursor.getString(5));
				}
				catch(Exception ex){
					
				}
				movieList.add(m);
			} while (cursor.moveToNext());
		}
		return movieList;

	}

	public void insertRow(Movie movie) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, movie.getTitle());
		values.put(KEY_PICTURE, movie.getpicSource());
		values.put(KEY_YEAR, movie.getYear());
		values.put(KEY_CATEGORY, movie.getCategory());
		values.put(KEY_RATING, movie.getRating());
		// values.put(KEY_STATUS, movie.getStatus());
		values.put(KEY_DESCRIPTION, movie.getDescription());
		//values.put(KEY_TRAILER, movie.getTrailer());
		values.put(KEY_USERNAME, movie.getUsername());

		db.insert(TABLE_MOVIES, null, values);

		db.close();

	}

	public void insertRowsToWishList(Movie movie, User user) {
		// Log.v("DHANDLER",StaticFields.username + " " );
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, movie.getTitle());
		values.put(KEY_USERNAME, user.getUsername());
		values.put(KEY_STATUS, "Wishlist");
		values.put(KEY_PICTURE, movie.getpicSource());
		values.put(KEY_YEAR, movie.getYear());
		values.put(KEY_CATEGORY, movie.getCategory());
		values.put(KEY_DESCRIPTION, movie.getDescription());
		values.put(KEY_TRAILER, movie.getTrailer());
		String query = "SELECT * from user_movies where title='"
				+ movie.getTitle() + "' and username='" + user.getUsername()
				+ "' and status='Seen'";
		Cursor cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			Log.d("In on insert rows", "deja exista");
			cursor.close();
			db.close();
		} else {

			cursor.close();
			db.insert(TABLE_USER_MOVIES, null, values);
			db.close();
		}
		SQLiteDatabase db1 = this.getWritableDatabase();
		String query1 = "SELECT * from user_movies where title='"
				+ movie.getTitle() + "' and username='" + user.getUsername()
				+ "' ";
		Cursor cursor1 = db1.rawQuery(query1, null);

		if (cursor1 != null && cursor1.getCount() > 0) {
			Log.d("In on insert rows", "deja exista");
			cursor.close();
			db1.close();
		} else {
			cursor.close();
			db1.insert(TABLE_USER_MOVIES, null, values);
			db1.close();
		}

	}

	public void insertRowsToSeen(Movie movie, User user) {
		// Log.v("DHANDLER",StaticFields.username + " " );
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, movie.getTitle());
		values.put(KEY_USERNAME, user.getUsername());
		values.put(KEY_STATUS, "Seen");
		values.put(KEY_PICTURE, movie.getpicSource());
		values.put(KEY_YEAR, movie.getYear());
		values.put(KEY_CATEGORY, movie.getCategory());
		values.put(KEY_DESCRIPTION, movie.getDescription());
		values.put(KEY_TRAILER, movie.getTrailer());

		String query = "SELECT * from user_movies where title='"
				+ movie.getTitle() + "' and username='" + user.getUsername()
				+ "' and status='Wishlist'";
		Cursor cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			Log.d("In on insert rows", "deja exista");
			cursor.close();
			db.close();
		} else {
			cursor.close();
			db.insert(TABLE_USER_MOVIES, null, values);
			db.close();
		}
		SQLiteDatabase db1 = this.getWritableDatabase();
		String query1 = "SELECT * from user_movies where title='"
				+ movie.getTitle() + "' and username='" + user.getUsername()
				+ "' ";
		Cursor cursor1 = db1.rawQuery(query1, null);
		if (cursor1 != null && cursor1.getCount() > 0) {
			cursor.close();
			db1.close();
		} else {
			cursor1.close();
			db1.insert(TABLE_USER_MOVIES, null, values);
			db1.close();
		}
	}

	public void insertMovieFromWishToSeen(Movie movie, User user) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_STATUS, "Seen");
		// return db.update(TABLE_USER_MOVIES, values,KEY_TITLE + "=? AND " +
		// KEY_USERNAME + "=?", new String[]
		// {movie.getTitle(),user.getUsername()});
		String sql = "UPDATE " + TABLE_USER_MOVIES
				+ " SET status='Seen' where " + KEY_TITLE + "='"
				+ movie.getTitle() + "' and " + KEY_USERNAME + "='"
				+ user.getUsername() + "' and " + KEY_STATUS + "='Wishlist'";
		db.execSQL(sql);
	}

	public int addToSeen(String title) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_STATUS, "Seen");

		// updating row
		return db.update(TABLE_MOVIES, values, KEY_TITLE + " = ?",
				new String[] { String.valueOf(title) });
	}

	public int addToWish(String title) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_STATUS, "Wishlist");

		// updating r
		return db.update(TABLE_MOVIES, values, KEY_TITLE + " = ?",
				new String[] { String.valueOf(title) });

	}

	public List<User> returnAllUsers() throws NameTooShortException, NameContainsNumbersException {
		List<User> userList = new ArrayList<User>();
		String query = "SELECT * FROM users";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				User x = new User();
				try{
				x.setId(Integer.parseInt(cursor.getString(0)));
				x.setName(cursor.getString(1));
				x.setUsername(cursor.getString(2));
				x.setPassword(cursor.getString(3));
				x.setEmail(cursor.getString(4));
				x.setFacebookProf(cursor.getString(5));
				x.setSex(cursor.getString(6));
				x.setVarsta(Integer.parseInt(cursor.getString(7)));
				}
				catch(Exception ex){
					
				}
				userList.add(x);
			} while (cursor.moveToNext());
		}
		return userList;

	}

	public int getAllUsers() {
		String selectQuery = "SELECT id FROM users";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor c = database.rawQuery(selectQuery, null);
		c.moveToFirst();
		int total = c.getCount();
		c.close();

		return total;
	}

	public int returnAllSeenMovies(User user) {
		String selectQuery = "SELECT count(DISTINCT title) FROM "
				+ TABLE_USER_MOVIES + " WHERE " + KEY_USERNAME + " = '"
				+ user.getUsername() + "'" + " AND " + KEY_STATUS + " = 'Seen'";
		SQLiteDatabase db = this.getWritableDatabase();
		int total = (int) DatabaseUtils.longForQuery(db, selectQuery, null);
		return total;

	}

	public int getAllMaleUsers() {
		String selectQuery = "SELECT id FROM users WHERE sex='M'";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor c = database.rawQuery(selectQuery, null);
		c.moveToFirst();
		int total = c.getCount();
		c.close();

		return total;
	}

	public int getAllFemaleUsers() {
		String selectQuery = "SELECT id FROM users WHERE sex='F'";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor c = database.rawQuery(selectQuery, null);
		c.moveToFirst();
		int total = c.getCount();
		c.close();

		return total;
	}

}
package dam.project.movieproj.data;

import dam.project.movieproj.exceptions.InvalidPicSourceException;
import dam.project.movieproj.exceptions.InvalidRatingException;
import dam.project.movieproj.exceptions.InvalidTitleException;
import dam.project.movieproj.exceptions.InvalidYearException;

public abstract class Movie {

	private int id;
	private String title;
	private String picSource;
	private String year;
	private String category;
	private float rating;
	private String description;
	private String trailer;
	private String username;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws InvalidTitleException  {
		if(title.trim().length() >=3)
		this.title = title;
		else
		throw new InvalidTitleException("Invalid title");
		
	}

	public String getpicSource() {
		return this.picSource;
	}

	public void setpicSource(String picSource) throws InvalidPicSourceException {
		if(picSource.trim().length() == 0)
			throw new InvalidPicSourceException("Sursa trebuie sa nu fie nula");
		if(picSource.contains("http"))
		this.picSource = picSource;
		else
			throw new InvalidPicSourceException("Sursa trebuie sa contina http");
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) throws InvalidYearException {
		if(!year.matches("[0-9]+"))
			throw new InvalidYearException("Anul trebuie sa contina doar cifre");
		if(year.trim().length()!=4)
			 throw new InvalidYearException("Anul este invalid");
		else
		this.year = year;
		
	   
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) throws InvalidRatingException {
		if(rating<0)
			throw new InvalidRatingException("Ratingul trebuie sa fie cuprins intre 0 si 10");
		if(rating == 0)
			throw new InvalidRatingException("Ratingul trebuie sa fie cuprins intre 0 si 10");
		if(rating >10)
			throw new InvalidRatingException("Ratingul trebuie sa fie cuprins intre 0 si 10");
		else
		this.rating = rating;
		
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Movie() {

	}

	public void setUsername(String user){
		this.username = user;
	}
	public String getUsername(){
		return this.username;
	}
	public Movie(String title, String picSource) {
		this.title = title;
		this.picSource = picSource;
	}

	public Movie(String title, String picSouce, String year, String category,
			float rating, String description, String trailer,String username) {

		this.title = title;
		this.picSource = picSource;
		this.year = year;
		this.category = category;
		this.rating = rating;
		// this.status = status;
		this.description = description;
		this.trailer = trailer;
		this.username = username;
	}

}

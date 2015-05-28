package dam.project.movieproj.data;

import dam.project.movieproj.exceptions.InvalidPicSourceException;
import dam.project.movieproj.exceptions.InvalidRatingException;
import dam.project.movieproj.exceptions.InvalidTitleException;
import dam.project.movieproj.exceptions.InvalidYearException;

public class AnimationMovie extends Movie {

	public AnimationMovie(){
		super();
		super.setCategory("Animation");
	}
	public AnimationMovie(String title, String picSource, String year, String category,
			float rating, String description, String trailer,String username) {
		super(title, picSource, year, category, rating, description, trailer,username);
	}
	public int getId() {
		return super.getId();
	}

	public void setId(int id) {
		super.setId(id);
	}

	public String getTitle() {
		return super.getTitle();
	}

	public void setTitle(String title) throws InvalidTitleException {
		super.setTitle(title);
	}

	public void setpicSource(String picSource) throws InvalidPicSourceException{
		super.setpicSource(picSource);
	}
	public void getpicSource(String picSource){
		super.getpicSource();
	}
	public String getYear() {
		return super.getYear();
	}

	public void setYear(String year) throws InvalidYearException {
		super.setYear(year);
	}

	public String getCategory() {
		return super.getCategory();
	}

	public void setCategory(String category) {
		super.setCategory(category);
	}

	public float getRating() {
		return super.getRating();
	}

	public void setRating(float rating) throws InvalidRatingException {
		super.setRating(rating);
	}

	public String getTrailer() {
		return super.getTrailer();
	}

	public void setTrailer(String trailer) {
		super.setTrailer(trailer);
	}

	public String getDescription() {
		return super.getDescription();
	}

	public void setDescription(String description) {
		super.setDescription(description);
	}
	public void setUsername(String username){
		super.setUsername(username);
	}
	public String getUsername(){
		return super.getUsername();
	}
}

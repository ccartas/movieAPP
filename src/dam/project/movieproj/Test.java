package dam.project.movieproj;

import dam.project.movieproj.data.Movie;
import dam.project.movieproj.data.MovieFactory;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MovieFactory f = new MovieFactory();
		Movie m =f.createInstance("Sci-Fi");
		m.setId(1);
		System.out.println(m.getId());
	}

}

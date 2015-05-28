package dam.project.movieproj.data;

import android.view.animation.Animation;

public class MovieFactory {

	
	public  Movie createInstance(String movieType){
		Movie m = null;
		if(movieType == null){
			return null;
		}
		else
		{
	    
		if(movieType.equals("Action")){
			m = new ActionMovie();
		}
		if(movieType.equals("Comedy")){
			m =new ComedyMovie();
		}
		if(movieType.equals("Drama")){
			m = new DramaMovie();
		}
		if(movieType.equals("Horror")){
			m = new HorrorMovie();
		}
		
		if(movieType.equals("Mystery")){
			m = new MysteryMovie();
		}
		
		if(movieType.equals("War")){
			m= new WarMovie();
		}
		
		if(movieType.equals("Romance")){
			m= new RomanceMovie();
		}
		
		if(movieType.equals("Thriller")){
			m= new ThrillerMovie();
		}
		
		if(movieType.equals("Animation")){
			m=new AnimationMovie(); 
		}
		if(movieType.equals("Sci-Fi")){
			m= new SciFiMovie();
		}
		if(movieType.contains("Drama")){
			m = new DramaMovie();
		}
		
		}
		
	return m;
	}
}

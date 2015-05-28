package dam.project.movieproj.test;

import dam.project.movieproj.data.Movie;
import dam.project.movieproj.data.MovieFactory;
import dam.project.movieproj.exceptions.InvalidPicSourceException;
import dam.project.movieproj.exceptions.InvalidRatingException;
import dam.project.movieproj.exceptions.InvalidTitleException;
import dam.project.movieproj.exceptions.InvalidYearException;
import junit.framework.TestCase;

public class TestCaseMovie extends TestCase {

	Movie mComedy;
	Movie mAction;
	Movie mDrama;
	Movie mHorror;
	Movie mSciFi;
	Movie mAnimation;
	Movie mRomance;
	Movie mThriller;
	Movie mWar;
	MovieFactory factory;
	public void setUp(){
		factory = new MovieFactory();
		mComedy = factory.createInstance("Comedy");
		mAction = factory.createInstance("Action");
		mDrama = factory.createInstance("Drama");
		mHorror = factory.createInstance("Horror");
		mSciFi = factory.createInstance("Sci-Fi");
		mAnimation = factory.createInstance("Animation");
		mRomance = factory.createInstance("Romance");
		mThriller = factory.createInstance("Thriller");
		mWar = factory.createInstance("War");
	}
	
	//Test testare lungime titlu
	public void testMovieTitle(){
	try{
		mComedy.setTitle("TT");
		fail("Nu a fost introdusa exceptie pentru titlu");
	}
	catch(InvalidTitleException ex){
		
	}
	}
	
	//testare categorie factory
	public void testCategoryComedy(){
		assertEquals("Testare Categorie Film Comedie", "Comedy", mComedy.getCategory());
	}
	public void testCategoryAction(){
		assertEquals("Testare Categorie FIlm Actiune", "Action", mAction.getCategory());
	}
	public void testCategoryDrama(){
		assertEquals("Testare Categorie Film Drama", "Drama", mDrama.getCategory());
	}
	public void testCategoryHorror(){
		assertEquals("Testare Categorie Film Horror", "Horror", mHorror.getCategory());
	}
	public void testCategorySciFi(){
		assertEquals("Testare Categorie Film SciFi", "Sci-Fi", mSciFi.getCategory());
	}
	public void testCategoryAnimation(){
		assertEquals("Testare Categorie Film Animation", "Animation", mAnimation.getCategory());
	}
	public void testCategoryRomance(){
		assertEquals("Testare Categorie film Romance","Romance",mRomance.getCategory());
	}
	public void testCategoryThriller(){
		assertEquals("Testare Categorie film Thriller", "Thriller", mThriller.getCategory());
	}
	public void testCategoryWar(){
		assertEquals("Test Categorie film War", "War", mWar.getCategory());
	}
	
	//testare an doar cifre
	public void testYear(){
		try{
			mComedy.setYear("aaaa");
			fail("Nu s-a implementat exceptie pentru validarea anului");
		}
		catch(InvalidYearException ex){
			
		}
	}
	//testare lungime an
	public void testYearLength(){
		try{
			mComedy.setYear("199");
			fail("Nu s-a implementat exceptie pentru validarea anului");
		}
		catch(InvalidYearException ex){
			
		}
	}
	//testare rating negativ
	public void testRatingNegativ(){
		try{
			mComedy.setRating(-2);
			fail("Nu s-a implementat exceptie pentru rating negativ");
		}
		catch(InvalidRatingException ex){
			
		}
	}
	//testare rating 0
	public void testRatingNul(){
		try{
			mComedy.setRating(0);
			fail("Nu s-a implementat exceptie pentru rating egal cu zero");
		}
		catch(InvalidRatingException ex){
			
		}
	}
	//testare rating mai mare decat 10
	public void testRatingGtTen(){
		try{
			mComedy.setRating(11);
			fail("Nu s-a implementat exceptie pentru rating mai mare decat 10");
		}
		catch(InvalidRatingException ex){
			
		}
	}
	
	//testare sursa nula
	public void testSourceEmpty(){
		try{
			mComedy.setpicSource("");
			fail("Nu s-a implementat exceptie pentru sursa nula");
		}
		catch(InvalidPicSourceException ex){
			
		}
	}
	public void testSourceValid(){
		try{
			mComedy.setpicSource("htt://");
			fail("Nu s-a implementat exceptie pentru sursa invalida");
		}
		catch(InvalidPicSourceException ex){
			
		}
	}
}

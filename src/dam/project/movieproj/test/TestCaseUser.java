package dam.project.movieproj.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import dalvik.annotation.TestTarget;
import dam.project.movieproj.data.User;
import dam.project.movieproj.exceptions.InvalidAgeException;
import dam.project.movieproj.exceptions.InvalidEmailException;
import dam.project.movieproj.exceptions.InvalidSexException;
import dam.project.movieproj.exceptions.InvalidUserIdException;
import dam.project.movieproj.exceptions.InvalidUsernameException;
import dam.project.movieproj.exceptions.NameContainsNumbersException;
import dam.project.movieproj.exceptions.NameTooShortException;
import junit.framework.TestCase;

public class TestCaseUser extends TestCase {
	private User user;
	private ArrayList<String> valoriTest;
	
	public void setUp(){
		this.user = new User();
		this.valoriTest = new ArrayList<String>();
		File f = new File("userNameTest.txt");
		FileReader fr;
		try {
			fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String linie="";
			while((linie=br.readLine())!=null){
				if(linie.startsWith("#"))
					continue;
				
				valoriTest.add(linie);
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//Test for name too short
	public void testNameLength() throws NameContainsNumbersException{
		
		try{
			this.user.setName(valoriTest.get(0));
			fail("Nu s-a implementat exceptie pentru nume mai mic de 4 caractere");
		}
		catch(NameTooShortException e){
			
		}
	}
	//Test for name containing numbers
	public void testNameNumbers() throws NameTooShortException{
		try{
			this.user.setName(valoriTest.get(1));
			fail("Nu s-a implementat exceptie pentru nume ce contine numere");
		}
		catch(NameContainsNumbersException ex){
			
		}
	}
	//Test for name first letter uppercase
	public void testFirstLetter() throws NameTooShortException{
		try{
			this.user.setName(valoriTest.get(2));
			fail("Nu s-a implementat exceptie pentru prima litera");
		}
		catch(NameContainsNumbersException e){
			
		}
	}
	//Test password length
	public void testPasswordLength(){
		try{
			this.user.setPassword(valoriTest.get(3));
			fail("Nu s-a implementat exceptie pentru lungimea parolei");
		}
		catch(Exception ex){
			
		}
	}
	//Test email validation
	public void testEmail(){
		try{
			this.user.setEmail(valoriTest.get(4));
			fail("Nu s-a implementat exceptie pentru validarea email-ului");
		}
		catch(InvalidEmailException ex){
			
		}
	}
	//Test sex
	public void testSex(){
		try{
			this.user.setSex(valoriTest.get(5));
			fail("Nu s-a implementat exceptie pentru validarea sexului");
		}
		catch(InvalidSexException ex){
			
		}
	}
	//Test varsta 0 value
	public void testVarstaZeroValue(){
		try{
			this.user.setVarsta(Integer.parseInt(valoriTest.get(6)));
			fail("Nu s-a implementat exceptie pentru validarea varstei");
		}
		catch(InvalidAgeException ex){
			
		}
	}
	//Test varsta valori negative
	public void testVarstaNegativeValues(){
		try{
			this.user.setVarsta(Integer.parseInt(valoriTest.get(7)));
			fail("Nu s-a implementat exceptie pentru validarea varstei");
		}
		catch(InvalidAgeException ex){
			
		}
	}
	//Test username valid
	public void testUsername(){
		try{
			this.user.setUsername(valoriTest.get(8));
			fail("Nu s-a implementat exceptie pentru validarea username-ului");
		}
		catch(InvalidUsernameException ex){
			
		}
	}
	//Test user id negativ
	public void testUserNegativeId(){
		try{
			this.user.setId(Integer.parseInt(valoriTest.get(9)));
			fail("Nu s-a implementat exceptie pentru id negativ");
		}
		catch(InvalidUserIdException ex){
			
		}
	}
	public void testUserZero(){
		try{
			this.user.setId(Integer.parseInt(valoriTest.get(10)));
			fail("Nu s-a implementat exceptie pentru id negativ");
		}
		catch(InvalidUserIdException ex){
			
		}
	}
	
}

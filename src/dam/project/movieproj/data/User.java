package dam.project.movieproj.data;

import dam.project.movieproj.exceptions.InvalidAgeException;
import dam.project.movieproj.exceptions.InvalidEmailException;
import dam.project.movieproj.exceptions.InvalidSexException;
import dam.project.movieproj.exceptions.InvalidUserIdException;
import dam.project.movieproj.exceptions.InvalidUsernameException;
import dam.project.movieproj.exceptions.NameContainsNumbersException;
import dam.project.movieproj.exceptions.NameTooShortException;
import dam.project.movieproj.exceptions.PasswordTooShortException;

public class User {

	private int id;
	private String name;
	private String username;
	private String email;
	private String password;
	private String facebookProf;
	private String sex;
	private int Varsta;
	private UserTheme tipUser;
	
	public void setUserTheme(UserTheme tipUser){
		this.tipUser = tipUser;
	}
	public UserTheme getUserTheme(){
		return this.tipUser;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) throws InvalidUserIdException {
		if(id<0 || id ==0)
			throw new InvalidUserIdException("Id-ul userului este invalid");
		else
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) throws NameTooShortException, NameContainsNumbersException {
		if(name.trim().length() < 4){
			throw new NameTooShortException("Name must contain atleast 4 characters");
		}
		if(!name.matches("[a-zA-Z]+") && Character.isUpperCase(name.codePointAt(0)))
		{
		this.name = name;
		}
		else
		{
			throw new NameContainsNumbersException("Name must contains only letters and first must be uppercase");
		}
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) throws InvalidUsernameException {
		if(username.trim().length() <= 3)
			throw new InvalidUsernameException("Username must be atleast 4 characters long");
		else
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) throws InvalidEmailException {
		if(email.contains("@")  && email.trim().length()>=6)
		this.email = email;
		else
			throw new InvalidEmailException("Email-ul trebuie sa fie unul valid");
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) throws PasswordTooShortException {
		if(password.trim().length() < 6)
			throw new PasswordTooShortException("Password must contain atleast 6 characters");
		else
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) throws InvalidSexException {
		if(sex.equals("M") || sex.equals("F"))
		this.sex = sex;
		else
		{
			throw new InvalidSexException("Sexul trebuie sa fie M sau F");
		}
	}
	public int getVarsta() {
		return Varsta;
	}
	public void setVarsta(int varsta) throws InvalidAgeException {
		if(varsta ==0 || varsta < 0)
			throw new InvalidAgeException("Varsta invalida");
		else
		Varsta = varsta;
	}
	
	
	public String getFacebookProf() {
		return facebookProf;
	}
	public void setFacebookProf(String facebookProf) {
		
		this.facebookProf = facebookProf;
	}
	public User(){
		
	}
	
	
	
	
}

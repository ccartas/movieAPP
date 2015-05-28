package dam.project.movieproj;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import dam.project.movieproj.data.DatabaseHandler;
import dam.project.movieproj.data.SessionManager;
import dam.project.movieproj.data.User;
import dam.project.movieproj.exceptions.NameContainsNumbersException;
import dam.project.movieproj.exceptions.NameTooShortException;

public class LoginActivity extends Activity {

	EditText username = null;
	EditText password = null;
	Button btnLogin = null;
	Button btnRegister = null;
	User user = new User();
	DatabaseHandler db = DatabaseHandler.getSingleton(this);
	SessionManager session;
	public static String tipUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		session = new SessionManager(this);
		

		btnRegister = (Button) findViewById(R.id.buttonRegister);
		btnLogin = (Button) findViewById(R.id.buttonLogin);
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent registerInt = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(registerInt);
			}
		});

		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				username = (EditText) findViewById(R.id.editTextUsername);
				password = (EditText) findViewById(R.id.editTextPassword);
				try {
					user = db.getUser(username.getText().toString(), password
							.getText().toString());
				} catch (NameTooShortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NameContainsNumbersException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				if (user != null) {
					if(db.getMaxCategory(user)!= null)
					tipUser = db.getMaxCategory(user);
					else
						tipUser= "";
					
					session.createLoginSession(user.getName(),
							user.getUsername(), user.getEmail(),
							user.getFacebookProf(), user.getPassword(),
							user.getSex(),tipUser);
					Intent dashBoard = new Intent(LoginActivity.this,
							DashboardActivity.class);
					startActivity(dashBoard);
					Toast.makeText(getApplicationContext(),
							"Logged in succesfully!", Toast.LENGTH_LONG).show();
					finish();
				}

				else {
					Toast.makeText(getApplicationContext(), "Login Failed",
							Toast.LENGTH_LONG).show();
				}
			}
		});

	}
}

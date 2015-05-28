package dam.project.movieproj;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import dam.project.movieproj.data.DatabaseHandler;
import dam.project.movieproj.data.User;
import dam.project.movieproj.exceptions.InvalidAgeException;
import dam.project.movieproj.exceptions.InvalidEmailException;
import dam.project.movieproj.exceptions.InvalidSexException;
import dam.project.movieproj.exceptions.InvalidUsernameException;
import dam.project.movieproj.exceptions.NameContainsNumbersException;
import dam.project.movieproj.exceptions.NameTooShortException;
import dam.project.movieproj.exceptions.PasswordTooShortException;

public class RegisterActivity extends Activity {

	DatabaseHandler db = DatabaseHandler.getSingleton(this);
	EditText name = null;
	EditText username = null;
	EditText password = null;
	EditText email = null;
	EditText facebook = null;
	RadioGroup rg = null;
	Spinner spinnerVarsta = null;
	Button registerButton = null;
	User user = new User();
	String spinnerAge = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		name = (EditText) findViewById(R.id.registerName);
		username = (EditText) findViewById(R.id.registerUsername);
		password = (EditText) findViewById(R.id.regPassword);
		email = (EditText) findViewById(R.id.registerEmail);
		facebook = (EditText) findViewById(R.id.registerFacebook);
		spinnerVarsta = (Spinner) findViewById(R.id.spinnerVarsta);
		registerButton = (Button) findViewById(R.id.RegisterBTNRegister);
		rg = (RadioGroup) findViewById(R.id.rgSex);

		spinnerVarsta.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				spinnerAge = parent.getItemAtPosition(position).toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (name.getText().toString().trim().length() >= 4) {
					try {
						user.setName(name.getText().toString());
					} catch (NameTooShortException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NameContainsNumbersException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Name must be atleast 4 characters long!",
							Toast.LENGTH_SHORT).show();
				}
				if (username.getText().toString().trim().length() >= 4) {
					try {
						user.setUsername(username.getText().toString());
					} catch (InvalidUsernameException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"User must be atleast 4 characters long!",
							Toast.LENGTH_SHORT).show();
				}
				if (password.getText().toString().trim().length() >= 6) {
					try {
						user.setPassword(password.getText().toString());
					} catch (PasswordTooShortException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Password must be atleast 6 characters long!",
							Toast.LENGTH_SHORT).show();
				}
				if (email.getText().toString().contains("@")
						&& email.getText().toString().trim().length() >= 6) {
					try {
						user.setEmail(email.getText().toString());
					} catch (InvalidEmailException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Email must be atleast 6 characters long!",
							Toast.LENGTH_SHORT).show();
				}
				user.setFacebookProf(facebook.getText().toString());
				if (((RadioButton) findViewById(R.id.radioMale)).isChecked() == true) {
					try {
						user.setSex("M");
					} catch (InvalidSexException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						user.setSex("F");
					} catch (InvalidSexException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					user.setVarsta(Integer.parseInt(spinnerAge));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvalidAgeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				User user1 = new User();
				try {
					user1 = db.checkUser(user.getUsername());
				} catch (NameTooShortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NameContainsNumbersException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (user1 != null) {
					Toast.makeText(getApplicationContext(),
							"Your account already exist", Toast.LENGTH_LONG)
							.show();
				} else if (user.getName() != null && user.getUsername() != null
						&& user.getPassword() != null
						&& user.getEmail() != null) {

					db.addUser(user);
					Toast.makeText(getApplicationContext(),
							"Your account was created succesfully!",
							Toast.LENGTH_LONG).show();
					finish();

				}

			}
		});

	}
}

package dam.project.movieproj.fragments;

import java.io.InputStream;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import dam.project.movieproj.DashboardActivity;
import dam.project.movieproj.LoginActivity;
import dam.project.movieproj.R;
import dam.project.movieproj.data.DatabaseHandler;
import dam.project.movieproj.data.SessionManager;
import dam.project.movieproj.data.User;
import dam.project.movieproj.exceptions.InvalidEmailException;
import dam.project.movieproj.exceptions.NameContainsNumbersException;
import dam.project.movieproj.exceptions.NameTooShortException;
import dam.project.movieproj.exceptions.PasswordTooShortException;

public class Profile extends Fragment {

	SessionManager session;
	DatabaseHandler db;
	User user = new User();
	EditText newEmail;
	EditText newFacebook;
	EditText newPassword;
	int k = 0;

	public Profile() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		session = new SessionManager(getActivity().getApplicationContext());
		try {
			user = session.getUserDetails();
		} catch (NameTooShortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NameContainsNumbersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		View view = inflater.inflate(R.layout.fragment_profile, container,
				false);
		view.setBackground(getResources().getDrawable(user.getUserTheme().setUserTheme()));
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		try {
			TextView tvName = (TextView) getActivity().findViewById(
					R.id.textViewName);
			TextView tvUser = (TextView) getActivity().findViewById(
					R.id.textViewUser);
			TextView tvEmail = (TextView) getActivity().findViewById(
					R.id.textViewEmail);
			TextView tvFacebook = (TextView) getActivity().findViewById(
					R.id.textViewFacebook);

			Button btnEditEmail = (Button) getActivity().findViewById(
					R.id.btnEditEmail);
			Button btnEditFacebook = (Button) getActivity().findViewById(
					R.id.btnEditFacebook);
			Button btnEditPassword = (Button) getActivity().findViewById(
					R.id.btnEditPassword);
			Button btnSave = (Button) getActivity().findViewById(
					R.id.btnSaveChanges);
			Button btnCancelChanges = (Button) getActivity().findViewById(
					R.id.btnCancelChange);
			tvName.setText(user.getName().toString());
			tvUser.setText(user.getUsername().toString());
			tvEmail.setText(user.getEmail().toString());
			if (user.getFacebookProf() != null) {
				tvFacebook.setText(user.getFacebookProf().toString());
			} else {
				tvFacebook.setText("");
			}
			if (haveNetworkConnection() == true
					&& user.getFacebookProf() != null) {
				new DownloadImageTask((ImageView) getActivity().findViewById(
						R.id.avatar)).execute("https://graph.facebook.com/"
						+ user.getFacebookProf() + "/picture?type=large");
			} else {
				if (user.getSex().equals("M")) {
					ImageView iv = (ImageView) getActivity().findViewById(
							R.id.avatar);
					iv.setImageResource(R.drawable.ic_nointernetboy);
				} else {
					ImageView iv = (ImageView) getActivity().findViewById(
							R.id.avatar);
					iv.setImageResource(R.drawable.ic_nointernetgirl);
				}
			}

			btnEditEmail.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final Dialog dialog = new Dialog(getActivity());

					dialog.setContentView(R.layout.change_email);
					dialog.setTitle("Edit Your Email");
					newEmail = (EditText) dialog.findViewById(R.id.newValue);
					Button saveEmail = (Button) dialog
							.findViewById(R.id.saveEmail);
					Button cancelEmail = (Button) dialog
							.findViewById(R.id.cancelEmail);
					saveEmail.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (newEmail.getText().toString().trim().length() >= 8
									&& newEmail.getText().toString()
											.contains("@")
									&& !newEmail.getText().toString()
											.equals(user.getEmail())) {
								k = 1;
								try {
									user.setEmail(newEmail.getText().toString());
								} catch (InvalidEmailException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								dialog.dismiss();

							} else {
								Toast.makeText(getActivity(), "Failed!",
										Toast.LENGTH_LONG).show();
							}
						}
					});

					cancelEmail.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			});

			btnEditFacebook.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					final Dialog dialog = new Dialog(getActivity());
					dialog.setContentView(R.layout.change_facebook);
					dialog.setTitle("Edit Your Facebook Profile");
					newFacebook = (EditText) dialog.findViewById(R.id.newValue);
					Button saveFacebook = (Button) dialog
							.findViewById(R.id.saveNewFacebook);
					Button cancelFacebook = (Button) dialog
							.findViewById(R.id.cancelFacebook);
					saveFacebook.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							if (!newFacebook.getText().toString()
									.equals(user.getFacebookProf())) {
								k = 1;
								user.setFacebookProf(newFacebook.getText()
										.toString());
								dialog.dismiss();
							} else {
								Toast.makeText(getActivity(), "Failed!",
										Toast.LENGTH_LONG).show();
							}

						}
					});
					cancelFacebook.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();

				}
			});
			btnEditPassword.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final Dialog dialog = new Dialog(getActivity());
					dialog.setContentView(R.layout.change_password);
					dialog.setTitle("Edit Your Password");
					newPassword = (EditText) dialog
							.findViewById(R.id.newPassword);
					Button savePassword = (Button) dialog
							.findViewById(R.id.savePassword);
					Button cancelPassword = (Button) dialog
							.findViewById(R.id.cancelPassword);
					savePassword.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (newPassword.getText().toString().trim()
									.length() >= 6
									&& !newPassword.getText().toString()
											.equals(user.getPassword())) {
								try {
									user.setPassword(newPassword.getText()
											.toString());
								} catch (PasswordTooShortException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								k = 1;
								dialog.dismiss();
							} else {
								Toast.makeText(getActivity(), "Failed!",
										Toast.LENGTH_LONG).show();
							}

						}
					});
					cancelPassword.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();

				}
			});

			btnSave.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					db = DatabaseHandler.getSingleton(getActivity());
					if (db.updateUser(user) && k == 1) {
						session.createLoginSession(user.getName(),
								user.getUsername(), user.getEmail(),
								user.getFacebookProf(), user.getPassword(),
								user.getSex(),
								LoginActivity.tipUser);
						/*
						 * Fragment fragment = new HomeFragment();
						 * FragmentManager fragmentManager =
						 * getFragmentManager();
						 * fragmentManager.beginTransaction()
						 * .replace(R.id.frame_container, fragment).commit();
						 * getActivity().setTitle("Home");
						 */
						Intent dashBoard = new Intent(getActivity(),
								DashboardActivity.class);
						startActivity(dashBoard);
						Toast.makeText(getActivity(), "SUCCESS!",
								Toast.LENGTH_LONG).show();

					} else {
						Intent dashBoard = new Intent(getActivity(),
								DashboardActivity.class);
						startActivity(dashBoard);
						Toast.makeText(getActivity(), "NO CHANGES WERE MADE",
								Toast.LENGTH_LONG).show();

					}
				}
			});
			btnCancelChanges.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					k = 0;
					Intent dashBoard = new Intent(getActivity(),
							DashboardActivity.class);
					startActivity(dashBoard);
					Toast.makeText(getActivity(), "NO CHANGES WERE MADE",
							Toast.LENGTH_LONG).show();

				}
			});
		} catch (Exception ex) {
			Toast.makeText(getActivity(), "Please login!", Toast.LENGTH_LONG)
					.show();
			Intent i = new Intent(getActivity(), LoginActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			// Staring Login Activity
			getActivity().startActivity(i);
		}
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {

				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			if (result != null)
				bmImage.setImageBitmap(result);
			else {
				if (user.getSex().equals("M"))
					result = BitmapFactory.decodeResource(getResources(),
							R.drawable.ic_nointernetboy);
				else
					result = BitmapFactory.decodeResource(getResources(),
							R.drawable.ic_nointernetgirl);
				bmImage.setImageBitmap(result);
			}
		}
	}

	private boolean haveNetworkConnection() {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}
}

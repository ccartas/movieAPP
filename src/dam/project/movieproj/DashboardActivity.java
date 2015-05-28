package dam.project.movieproj;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import dam.project.movieproj.data.NavDrawerListAdapter;
import dam.project.movieproj.data.NavigationDrawerItem;
import dam.project.movieproj.data.SessionManager;
import dam.project.movieproj.data.User;
import dam.project.movieproj.exceptions.NameContainsNumbersException;
import dam.project.movieproj.exceptions.NameTooShortException;
import dam.project.movieproj.fragments.AddMovie;
import dam.project.movieproj.fragments.AllMovies;
import dam.project.movieproj.fragments.HomeFragment;
import dam.project.movieproj.fragments.Profile;
import dam.project.movieproj.fragments.SeenList;
import dam.project.movieproj.fragments.WishList;

@SuppressWarnings("deprecation")
public class DashboardActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavigationDrawerItem> navDrawItems;
	private NavDrawerListAdapter adapter;
	SessionManager session;
	User u=new User();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);

		session = new SessionManager(this);
		 try {
			u = session.getUserDetails();
		} catch (NameTooShortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NameContainsNumbersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		mTitle = mDrawerTitle = getTitle();

		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setBackground(getResources().getDrawable(u.getUserTheme().setUserTheme()));
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawItems = new ArrayList<NavigationDrawerItem>();
		navDrawItems.add(new NavigationDrawerItem(navMenuTitles[0],
				navMenuIcons.getResourceId(0, -1)));
		navDrawItems.add(new NavigationDrawerItem(navMenuTitles[6],
				navMenuIcons.getResourceId(2,-1)));
		navDrawItems.add(new NavigationDrawerItem(navMenuTitles[1],
				navMenuIcons.getResourceId(1, -1)));
		/*navDrawItems.add(new NavigationDrawerItem(navMenuTitles[2],
				navMenuIcons.getResourceId(2, -1)));
		navDrawItems.add(new NavigationDrawerItem(navMenuTitles[3],
				navMenuIcons.getResourceId(3, -1)));*/
		/*
		 * navDrawItems.add(new NavigationDrawerItem(navMenuTitles[4],
		 * navMenuIcons.getResourceId(4, -1), true, "50+"));
		 */
		navDrawItems.add(new NavigationDrawerItem(navMenuTitles[4],
			navMenuIcons.getResourceId(4, -1)));
		navDrawItems.add(new NavigationDrawerItem("Logout", navMenuIcons
				.getResourceId(5, -1)));

		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawItems);
		mDrawerList.setAdapter(adapter);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {

			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if (savedInstanceState == null) {
			displayView(0);
		}

	}

	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			displayView(position);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new AddMovie();
			break;
		case 2:
			fragment = new AllMovies();
			break;
		case 3:
			fragment = new Profile();
			break;
		/*case 4:
			fragment = new Profile();
			break;*/
		case 4:

			session.logoutUser();
			break;
		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			// Log.e("MainActivity", "Error in creating fragment");
			Toast.makeText(getApplicationContext(), "You have logged out!",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
}

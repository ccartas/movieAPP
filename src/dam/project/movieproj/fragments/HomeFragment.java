package dam.project.movieproj.fragments;

import java.util.HashMap;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import dam.project.movieproj.R;
import dam.project.movieproj.data.DatabaseHandler;
import dam.project.movieproj.data.SessionManager;
import dam.project.movieproj.data.User;
import dam.project.movieproj.exceptions.NameContainsNumbersException;
import dam.project.movieproj.exceptions.NameTooShortException;

public class HomeFragment extends Fragment {
	Button btnPie, btnBarChart;
	int nrGuys, nrGals, nrTotal;
	HashMap<User, Integer> hMap;
	List<User> userList;
	int[] numbers;
	User user = new User();
	SessionManager session;
	public HomeFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		session = new SessionManager(getActivity());

		try {
			user = session.getUserDetails();
		} catch (NameTooShortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NameContainsNumbersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);
		rootView.setBackground(getResources().getDrawable(user.getUserTheme().setUserTheme()));
		btnPie = (Button) rootView.findViewById(R.id.btnPie);

		
		btnBarChart = (Button) rootView.findViewById(R.id.btnHistogram);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		DatabaseHandler db = DatabaseHandler.getSingleton(getActivity());
		nrGuys = db.getAllMaleUsers();
		nrGals = db.getAllFemaleUsers();
		nrTotal = db.getAllUsers();
		try {
			userList = db.returnAllUsers();
		} catch (NameTooShortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NameContainsNumbersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		numbers = new int[userList.size()];
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = db.returnAllSeenMovies(userList.get(i));
		}

		btnPie.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openPieChart();

			}
		});

		btnBarChart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openBarChart();

			}
		});
	}

	private void openPieChart() {

		// Pie Chart Section Names
		String[] code = new String[] { "Male", "Female" };

		// Pie Chart Section Value
		double[] distribution = new double[] { nrGuys, nrGals };
		// Color of each Pie Chart Sections
		int[] colors = { Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN,
				Color.RED, Color.YELLOW };

		// Instantiating CategorySeries to plot Pie Chart
		CategorySeries distributionSeries = new CategorySeries(
				"User genre distribution");
		for (int i = 0; i < distribution.length; i++) {
			// Adding a slice with its values and name to the Pie Chart
			distributionSeries.add(code[i], distribution[i]);
		}

		// Instantiating a renderer for the Pie Chart
		DefaultRenderer defaultRenderer = new DefaultRenderer();
		for (int i = 0; i < distribution.length; i++) {
			SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
			seriesRenderer.setColor(colors[i]);
			seriesRenderer.setDisplayChartValues(true);
			// Adding a renderer for a slice
			defaultRenderer.addSeriesRenderer(seriesRenderer);
		}

		defaultRenderer.setChartTitle("User genre distribution");
		defaultRenderer.setChartTitleTextSize(40);
		defaultRenderer.setLabelsTextSize(40);
		defaultRenderer.setLegendTextSize(40);
		defaultRenderer.setZoomButtonsVisible(false);

		// Creating an intent to plot bar chart using dataset and
		// multipleRenderer
		Intent intent = ChartFactory.getPieChartIntent(getActivity()
				.getApplicationContext(), distributionSeries, defaultRenderer,
				"AChartEnginePieChartDemo");

		// Start Activity
		startActivity(intent);

	}

	private void openBarChart() {
		XYSeries userSeries = new XYSeries("Users");
		// Adding data to Income and Expense Series
		for (int i = 0; i < userList.size(); i++) {
			userSeries.add(i, numbers[i]);
		}

		// Creating a dataset to hold each series
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		// Adding Income Series to the dataset
		dataset.addSeries(userSeries);

		// Creating XYSeriesRenderer to customize incomeSeries
		XYSeriesRenderer userRenderer = new XYSeriesRenderer();
		userRenderer.setColor(Color.rgb(130, 130, 230));
		userRenderer.setFillPoints(true);
		userRenderer.setChartValuesTextSize(40);
		userRenderer.setLineWidth(1);
		userRenderer.setChartValuesSpacing(5);
		userRenderer.setDisplayChartValues(true);

		// Creating a XYMultipleSeriesRenderer to customize the whole chart
		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
		multiRenderer.setXLabels(0);
		multiRenderer.setChartTitle("Number of seen movies by user");
		multiRenderer.setChartTitleTextSize(40);
		multiRenderer.setXTitle("Users");
		multiRenderer.setBarSpacing(10);
		multiRenderer.setAxisTitleTextSize(40);
		multiRenderer.setLegendTextSize(40);
		multiRenderer.setLabelsTextSize(40);
		multiRenderer.setYTitle("Number of seen movies");
		multiRenderer.setAxisTitleTextSize(40);

		// astea cam fac scala la care se vede, chiar daca ele defapt ofera
		// minim si maxim pt valori
		multiRenderer.setXAxisMin(-5);
		multiRenderer.setXAxisMax(userList.size() + 5);
		multiRenderer.setYAxisMin(-5);
		multiRenderer.setYAxisMax(100);
		// astea de mai sus

		multiRenderer.setZoomButtonsVisible(false);
		for (int i = 0; i < userList.size(); i++) {
			multiRenderer.addXTextLabel(i, userList.get(i).getUsername());
		}

		multiRenderer.addSeriesRenderer(userRenderer);

		Intent intent = ChartFactory.getBarChartIntent(getActivity()
				.getApplicationContext(), dataset, multiRenderer, Type.DEFAULT);

		startActivity(intent);

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	public void populate() {

	}
}

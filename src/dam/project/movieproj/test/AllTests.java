package dam.project.movieproj.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(TestCaseMovie.class);
		suite.addTestSuite(TestCaseUser.class);
		//$JUnit-END$
		return suite;
	}

}

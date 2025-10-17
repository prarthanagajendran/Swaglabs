package base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import utils.Utility;

public class Projectspecificationmethod extends Utility {
	 

	@BeforeSuite
	public ExtentReports reportIniatialization() {
		
		ExtentSparkReporter reporter = new ExtentSparkReporter("C:\\Users\\Pradeep\\eclipse-workspace\\Swaglabsproject\\reports\\Reporter.html");
		reporter.config().setReportName("Adactin report");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		return extent;
	}
	@BeforeClass
	public void testDetails() {
		
		test = extent.createTest(testName,testDescription);
		test.assignCategory(testCategory);
		test.assignAuthor(testAuthor);

	}
 
	
	@Parameters({"browser","url"})
	@BeforeMethod
	public void browserlaunch(String browser, String url) throws Exception {
		launchingBrowser(browser, url);
		Thread.sleep(2000);
	}
	
	
	
	@AfterMethod
	public void shuttingBrowser() throws Exception {
		Thread.sleep(2000);
		closingbrowser();
	}
	
	@AfterSuite
public void closeReport() {
	
	extent.flush();
}
}





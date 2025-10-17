package utils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class Utility {
	
	public static WebDriver driver;
	public static Properties prop;
    public String sheetname;
    public static ExtentReports extent;
 	public static ExtentTest test;
 	public String testName, testDescription, testCategory, testAuthor;
	public String filepath;

 	
	
	public void launchingBrowser(String browser , String url) {
 		//driver = new ChromeDriver();
		if (browser.equalsIgnoreCase("Chrome")) {
	        driver = new ChromeDriver();
	    } else if (browser.equalsIgnoreCase("Firefox")) {
	        driver = new FirefoxDriver();
	    }
 		driver.get(url);
 		driver.manage().window().maximize();
 		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
 		}
 	public void closingbrowser() {
 		driver.close();
 
 
 	}
 
 public static String screenshot(String name) throws IOException {
        String path = "C:\\Users\\Pradeep\\eclipse-workspace\\Swaglabsproject\\screenshots\\" + name + ".png";
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File dest = new File(path);
		FileUtils.copyFile(src, dest);
		return path;
	}
}
 	
 
 



package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


import io.github.bonigarcia.wdm.WebDriverManager;

	public class ProjectSpecificationMethod {
	    protected WebDriver driver;

	    @BeforeMethod
	    public void setUp() throws InterruptedException {
	        //download chromedriver
	        WebDriverManager.chromedriver().setup();
	        driver = new ChromeDriver();
	        driver.manage().window().maximize();

	        // open the AUT
	        driver.get("https://www.saucedemo.com/");


	        System.out.println("Browser launched and navigated to saucedemo");
	    }

	    @AfterMethod
	    public void tearDown() {
	        if (driver != null) {
	            driver.quit();
	            System.out.println("Browser closed");
	        }
	    }
	}
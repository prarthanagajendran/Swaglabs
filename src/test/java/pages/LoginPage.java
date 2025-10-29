package pages;




import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class LoginPage  {

	 private WebDriver driver;

	    //  Locators
	    private By usernameField = By.id("user-name");
	    private By passwordField = By.id("password");
	    private By loginButton = By.id("login-button");

	    //  Constructor
	    public LoginPage(WebDriver driver) {
	        this.driver = driver;
	    }

	    // Login method
	    public void login(String username, String password) {
	        driver.findElement(usernameField).sendKeys(username);
	        driver.findElement(passwordField).sendKeys(password);
	        driver.findElement(loginButton).click();
	        System.out.println("🔐 Logged in with user: " + username);
	    }
	}
package pages;


import com.aventstack.extentreports.Status;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Utility;

public class LoginPage extends Utility {

	

	    private final By username = By.id("user-name");
	    private final By password = By.id("password");
	    private final By loginBtn = By.id("login-button");
	    private final By productHeader = By.xpath("//span[text()='Products']");
	    private final By errorMsg = By.cssSelector("[data-test='error']");

	    // timeout for waits
	    private final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

	    public LoginPage() {
	        // nothing else — driver comes from Utility
	    }

	    // helper to get WebDriverWait, safely
	    private WebDriverWait getWait() {
	        if (driver == null) {
	            throw new IllegalStateException("WebDriver is null. Ensure browser is launched before creating page objects.");
	        }
	        return new WebDriverWait(driver, WAIT_TIMEOUT);
	    }

	    public void enterUsername(String user) throws TimeoutException {
	        WebElement u = getWait().until(ExpectedConditions.visibilityOfElementLocated(username));
			u.clear();
			u.sendKeys(user);
			test.log(Status.INFO, "Entered username: " + user);
	    }

	    public void enterPassword(String pass) throws TimeoutException {
	        WebElement p = getWait().until(ExpectedConditions.visibilityOfElementLocated(password));
			p.clear();
			p.sendKeys(pass);
			test.log(Status.INFO, "Entered password");
	    }

	    public void clickLogin() throws TimeoutException {
	        WebElement btn = getWait().until(ExpectedConditions.elementToBeClickable(loginBtn));
			btn.click();
			test.log(Status.INFO, "Clicked Login button");
	    }

	    public void loginAs(String user, String pass) throws Exception {
	        // Defensive: ensure driver is set
	        if (driver == null) {
	            test.log(Status.FAIL, "WebDriver is null in loginAs — browser not launched");
	            throw new IllegalStateException("WebDriver is null. Call launchingBrowser(...) before using LoginPage.");
	        }

	        try {
	            enterUsername(user);
	            enterPassword(pass);
	            clickLogin();

	            // wait for Products header; if not visible -> fail
	            boolean landed;
	            getWait().until(ExpectedConditions.visibilityOfElementLocated(productHeader));
				landed = true;

	            if (landed) {
	                test.log(Status.PASS, "Login successful - Products page displayed.");
	            } else {
	                test.log(Status.FAIL, "Login did not land on Products page.");
	                String path = screenshot("LoginFailure");
	                test.addScreenCaptureFromPath(path, "Login failure screenshot");
	                throw new AssertionError("Products header not found after login");
	            }

	        } catch (Exception e) {
	            // attempt screenshot and rethrow
	            try {
	                String p = screenshot("LoginException");
	                test.addScreenCaptureFromPath(p, "Exception during login");
	            } catch (Exception ignore) {}
	            throw e;
	        }
	    }

	    // optional helper for negative cases
	    public String getLoginError() {
	        try {
	            WebElement em = driver.findElement(errorMsg);
	            return em.getText();
	        } catch (Exception e) {
	            return "";
	        }
	    }
	}

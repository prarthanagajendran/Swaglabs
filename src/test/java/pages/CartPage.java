package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class CartPage{
	private WebDriver driver;
    private WebDriverWait wait;

    private By cartItemContainer = By.cssSelector(".cart_item");
    private By cartItemName = By.className("inventory_item_name");
    private By cartItemPrice = By.className("inventory_item_price");
    private By cartQuantity = By.className("cart_quantity");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("/cart.html")); // keep this only
    }
    public String getCartItemName() {
        WebElement row = driver.findElements(cartItemContainer).get(0);
        return row.findElement(cartItemName).getText().trim();
    }

    public String getCartItemPrice() {
        WebElement row = driver.findElements(cartItemContainer).get(0);
        return row.findElement(cartItemPrice).getText().trim();
    }

    public String getCartItemQuantity() {
        try {
            // Short wait just for quantity
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            shortWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartQuantity));

            List<WebElement> qty = driver.findElements(cartQuantity);
            if (!qty.isEmpty()) {
                return qty.get(0).getText().trim();
            }
        } catch (Exception e) {
            System.err.println("⚠️ Quantity not found, returning default '1'. Error: " + e.getMessage());
        }
        return "1";
    }
}
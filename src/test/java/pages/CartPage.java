package pages;

import org.openqa.selenium.By;

import com.aventstack.extentreports.Status;

import utils.Utility;

public class CartPage extends Utility{
	private final By cartItemName = By.className("inventory_item_name");
    private final By cartItemPrice = By.className("inventory_item_price");
    private final By cartQuantity = By.className("cart_quantity");

    public void verifyCartItem(String expectedName, String expectedPrice) {
        String actualName = driver.findElement(cartItemName).getText();
        String actualPrice = driver.findElement(cartItemPrice).getText();
        String quantity = driver.findElement(cartQuantity).getText();

        if (actualName.equals(expectedName) && actualPrice.equals(expectedPrice) && quantity.equals("1")) {
            test.log(Status.PASS, "Cart verified ✅ " + actualName + " | " + actualPrice + " | Qty=" + quantity);
        } else {
            test.log(Status.FAIL, "Cart verification failed ❌ \nExpected: " + expectedName + ", " + expectedPrice +
                    "\nActual: " + actualName + ", " + actualPrice + ", Qty=" + quantity);
        }
    }
}



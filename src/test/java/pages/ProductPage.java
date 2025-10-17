package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.Status;

import utils.Utility;

public class ProductPage extends Utility {
	 private final By sortDropdown = By.className("product_sort_container");
	    private final By productNames = By.className("inventory_item_name");
	    private final By productPrices = By.className("inventory_item_price");
	    private final By addToCartButtons = By.xpath("//button[contains(text(),'Add to cart')]");
	    private final By cartIcon = By.className("shopping_cart_link");

	    // ✅ Select a sorting option
	    public void sortProducts(String sortOption) {
	        Select select = new Select(driver.findElement(sortDropdown));
	        select.selectByVisibleText(sortOption);
	        test.log(Status.INFO, "Sorted products by: " + sortOption);
	    }

	    // ✅ Get all product names
	    public List<String> getAllProductNames() {
	        List<WebElement> elements = driver.findElements(productNames);
	        List<String> names = new ArrayList<>();
	        for (WebElement e : elements) names.add(e.getText().trim());
	        return names;
	    }

	    // ✅ Get all product prices
	    public List<Double> getAllProductPrices() {
	        List<WebElement> elements = driver.findElements(productPrices);
	        List<Double> prices = new ArrayList<>();
	        for (WebElement e : elements) {
	            String text = e.getText().replace("$", "").trim();
	            prices.add(Double.parseDouble(text));
	        }
	        return prices;
	    }

	    // ✅ Add first (cheapest/highest) product in current sort
	    public String addFirstProductToCart() {
	        WebElement firstName = driver.findElements(productNames).get(0);
	        WebElement firstPrice = driver.findElements(productPrices).get(0);
	        String productName = firstName.getText();
	        String productPrice = firstPrice.getText();

	        driver.findElements(addToCartButtons).get(0).click();
	        test.log(Status.PASS, "Added to cart → " + productName + " | Price: " + productPrice);
	        return productName + "|" + productPrice;
	    }

	    // ✅ Click Cart icon
	    public void openCart() {
	        driver.findElement(cartIcon).click();
	        test.log(Status.INFO, "Opened Cart page");
	    }

	    // ✅ Extract top 3 items dynamically after sorting (Task 2)
	    public List<String> extractTop3Products() {
	        List<WebElement> names = driver.findElements(productNames);
	        List<WebElement> prices = driver.findElements(productPrices);

	        List<String> output = new ArrayList<>();
	        int limit = Math.min(3, names.size());
	        for (int i = 0; i < limit; i++) {
	            output.add(names.get(i).getText() + " - $" + prices.get(i).getText().replace("$", ""));
	        }
	        return output;
	    }
	}



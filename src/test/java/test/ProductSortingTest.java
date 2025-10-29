package test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import pages.CartPage;
import pages.LoginPage;
import pages.ProductPage;
import base.ProjectSpecificationMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


	public class ProductSortingTest extends ProjectSpecificationMethod {

	    // ✅ DataProvider for multiple sorting types
	    @DataProvider(name = "sortOptions")
	    public Object[][] getSortOptions() {
	        return new Object[][]{
	                {"Price (low to high)"},
	                {"Price (high to low)"},
	                {"Name (A to Z)"}
	        };
	    }

	    // Main test method
	    @Test(dataProvider = "sortOptions")
	    public void verifyProductSorting(String sortOption) throws InterruptedException {
	        System.out.println(" Executing Test for: " + sortOption + " ");

	        // Step 1: Login
	        LoginPage login = new LoginPage(driver);
	        login.login("standard_user", "secret_sauce");

	        // Step 2: Sort products
	        ProductPage productPage = new ProductPage(driver);
	        productPage.sortBy(sortOption);

	        // Step 3: Capture product details before adding to cart
	        String expectedName = productPage.getFirstProductName();
	        String expectedPrice = productPage.getFirstProductPrice();
	        System.out.println("Expected Product: " + expectedName + " | Price: " + expectedPrice);

	        // Step 4: Add to cart and open cart
	        productPage.addFirstProductToCart();
	        productPage.openCart();
	        System.out.println(" Waiting for cart page to load...");
	        Thread.sleep(1500);
	        // Step 5: Verify cart details
	        CartPage cart = new CartPage(driver);
	        try {
	            Assert.assertEquals(cart.getCartItemName(), expectedName, " Product name mismatch");
	            Assert.assertEquals(cart.getCartItemPrice(), expectedPrice, "Price mismatch");
	            Assert.assertEquals(cart.getCartItemQuantity(), "1", "Quantity should be 1");
	            System.out.println(" Test Passed for: " + sortOption);
	        } catch (AssertionError e) {
	            System.err.println(" Test failed for: " + sortOption + " -> " + e.getMessage());
	            throw e; // keep failure visible in report
	        }
	        Thread.sleep(1000); // small pause after navigation
	        List<WebElement> items = driver.findElements(By.className("cart_item"));
	        System.out.println("🧾 Cart items found: " + items.size());

	    }
	}
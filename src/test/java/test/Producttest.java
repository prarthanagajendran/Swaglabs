package test;

import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.LoginPage;
import pages.ProductPage;

import base.Projectspecificationmethod;

public class Producttest extends Projectspecificationmethod  {
	

    @DataProvider(name = "sortOptions")
    public Object[][] sortOptions() {
        return new Object[][]{
                {"Price (low to high)"},
                {"Price (high to low)"},
                {"Name (A to Z)"}
        };
    }

   //Filter and add item to cart for each sort option
    @Test(dataProvider = "sortOptions")
    public void verifyAddToCartWithSorting(String sortOption) throws Exception {
        testName = "Verify Add to Cart with Sort: " + sortOption;
        testDescription = "Sort products and verify cheapest/highest product in cart";
        testCategory = "Regression";
        testAuthor = "Prarthana";

        // Step 1: Login
        LoginPage login = new LoginPage();
        login.loginAs("standard_user", "secret_sauce");

        // Step 2: Sort & add
        ProductPage product = new ProductPage();
        product.sortProducts(sortOption);
        String[] productData = product.addFirstProductToCart().split("\\|");
        String name = productData[0];
        String price = productData[1];

        // Step 3: Open cart & verify
        product.openCart();
        CartPage cart = new CartPage();
        cart.verifyCartItem(name, price);
    }

    // Extract first 3 products after sorting
    @Test
    public void extractProductsByHeader() throws Exception {
        testName = "Extract Top 3 Products After Sorting";
        testDescription = "Extract first 3 product names and prices dynamically";
        testCategory = "Exploratory";
        testAuthor = "Prarthana";

        // Login
        LoginPage login = new LoginPage();
        login.loginAs("standard_user", "secret_sauce");

        // Sort by Name (Z to A)
        ProductPage product = new ProductPage();
        product.sortProducts("Name (Z to A)");

        List<String> top3 = product.extractTop3Products();
        System.out.println("Top 3 products after sorting Z→A:");
        for (String entry : top3) {
            System.out.println(entry);
        }
    }
}




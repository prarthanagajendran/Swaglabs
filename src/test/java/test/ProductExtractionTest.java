package test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.ProjectSpecificationMethod;
import pages.LoginPage;
import pages.ProductPage;
import pages.ProductPage.ProductInfo;

public class ProductExtractionTest extends ProjectSpecificationMethod {
	 
	    @Test
	    public void extractTop3Products_NameZtoA() throws InterruptedException {
	        // Step 1: Login
	        LoginPage login = new LoginPage(driver);
	        login.login("standard_user", "secret_sauce");

	        // Step 2: Sort products (Name Z to A)
	        ProductPage productPage = new ProductPage(driver);
	        String sortOption = "Name (Z to A)";
	        productPage.sortBy(sortOption);
	        Thread.sleep(1000);

	        // Step 3: Extract top 3 products 
	        List<ProductInfo> top3 = productPage.getTopNProducts(3);

	        // Print the extracted results 
	        System.out.println(" Top " + top3.size() + " products after sorting by: " + sortOption + " ");
	        int idx = 1;
	        for (ProductInfo p : top3) {
	            System.out.println(idx++ + ". " + p.getName() + "  |  " + p.getPrice());
	        }

	        Assert.assertTrue(top3.size() >= 3, "Expected at least 3 products after sorting, but found " + top3.size());

	        top3.forEach(p -> {
	            Assert.assertFalse(p.getName().isEmpty(), "Product name should not be empty");
	            Assert.assertFalse(p.getPrice().isEmpty(), "Product price should not be empty");
	        });
	    }
	}



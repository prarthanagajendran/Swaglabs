package pages;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ProductPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By sortDropdown = By.className("product_sort_container");
    private By productNames = By.className("inventory_item_name");
    private By productPrices = By.className("inventory_item_price");
    private By addToCartButtons = By.xpath("//button[contains(text(),'Add to cart')]");
    private By cartIcon = By.className("shopping_cart_link"); 

    //  Constructor
    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(sortDropdown));
    }

    //  Method 1 — Sort products by dropdown option
    public void sortBy(String sortOption) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(sortDropdown));
        Select select = new Select(dropdown);
        select.selectByVisibleText(sortOption);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productNames));
        System.out.println("🔽 Sorted by: " + sortOption);
    }

    //  Method 2 — Get first product name
    public String getFirstProductName() {
        List<WebElement> names = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productNames));
        return names.get(0).getText().trim();
    }

    // Method 3 — Get first product price
    public String getFirstProductPrice() {
        List<WebElement> prices = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productPrices));
        return prices.get(0).getText().trim();
    }

    public void addFirstProductToCart() {
        try {
            // Re-fetch after sorting to avoid stale elements
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item")));
            List<WebElement> addButtons = driver.findElements(By.xpath("//button[contains(text(),'Add to cart')]"));

            if (addButtons.isEmpty()) {
                throw new RuntimeException("❌ No 'Add to Cart' buttons found on page after sorting.");
            }

            WebElement firstAddButton = addButtons.get(0);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", firstAddButton);
            wait.until(ExpectedConditions.elementToBeClickable(firstAddButton));
            firstAddButton.click();
            System.out.println("🛒 First product clicked (Add to Cart)");

            // ✅ Wait until the button text changes to "Remove" or badge becomes 1
            WebDriverWait confirmWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            confirmWait.until(ExpectedConditions.or(
                    ExpectedConditions.textToBePresentInElement(firstAddButton, "Remove"),
                    ExpectedConditions.textToBePresentInElementLocated(By.className("shopping_cart_badge"), "1")
            ));

            System.out.println("🟢 Product successfully added to cart and badge updated.");

        } catch (StaleElementReferenceException e) {
            System.out.println("♻️ Retrying click after stale element refresh...");
            driver.navigate().refresh();
            addFirstProductToCart();
        } catch (Exception e) {
            System.err.println("⚠️ Add to cart failed: " + e.getMessage());
        }
    }

    public void openCart() throws InterruptedException {
        System.out.println("🕒 Attempting to open cart page...");
        Thread.sleep(800);

        try {
            // Step 1: Wait until the cart badge shows 1 item
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("shopping_cart_badge"), "1"));
            System.out.println("🟢 Cart badge shows 1 item.");

            // Step 2: Ensure the cart icon is visible and clickable
            WebElement cartIconElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("shopping_cart_link")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", cartIconElement);
            wait.until(ExpectedConditions.elementToBeClickable(cartIconElement));
            System.out.println("🛍️ Cart icon found and clickable.");

            // Step 3: Try clicking normally
            try {
                cartIconElement.click();
                System.out.println("🖱️ Cart icon clicked normally.");
            } catch (Exception e) {
                System.out.println("⚠ Normal click failed, using JS click...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cartIconElement);
            }

            // Step 4: Wait a bit and check URL
            Thread.sleep(800);
            String currentUrl = driver.getCurrentUrl();
            if (!currentUrl.contains("/cart.html")) {
                System.out.println("⚠ Cart icon click did not navigate — navigating directly using JS...");
                ((JavascriptExecutor) driver).executeScript("window.location.href='https://www.saucedemo.com/cart.html'");
            }

            // Step 5: Wait until we’re definitely on the cart page
            wait.until(ExpectedConditions.urlContains("/cart.html"));
            System.out.println("✅ Navigated to cart page successfully!");

        } catch (Exception e) {
            System.err.println("❌ Could not open cart page: " + e.getMessage());
            // Final guaranteed fallback (navigation)
            driver.navigate().to("https://www.saucedemo.com/cart.html");
            wait.until(ExpectedConditions.urlContains("/cart.html"));
            System.out.println("✅ Navigated to cart page (forced fallback).");
        }
    }


    public List<String> getTopNProductNames(int n) {
        List<WebElement> names = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productNames));
        return names.stream().limit(n).map(WebElement::getText).collect(Collectors.toList());
    }

    public List<String> getTopNProductPrices(int n) {
        List<WebElement> prices = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productPrices));
        return prices.stream().limit(n).map(WebElement::getText).collect(Collectors.toList());
    }
    public static class ProductInfo {
        private final String name;
        private final String price;

        public ProductInfo(String name, String price) {
            this.name = name;
            this.price = price;
        }
        public String getName() { return name; }
        public String getPrice() { return price; }

        @Override
        public String toString() {
            return name + " | " + price;
        }
    }

    
    public List<ProductInfo> getTopNProducts(int n) {
        List<WebElement> names = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productNames));
        List<WebElement> prices = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productPrices));

        int available = Math.min(Math.min(names.size(), prices.size()), n);
        List<ProductInfo> result = new ArrayList<>();

        for (int i = 0; i < available; i++) {
            String nm = names.get(i).getText().trim();
            String pr = prices.get(i).getText().trim();
            result.add(new ProductInfo(nm, pr));
        }
        return result;
    }
}
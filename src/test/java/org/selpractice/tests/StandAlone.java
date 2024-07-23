package org.selpractice.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.selpractice.testcomponent.LandingPage;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class StandAlone {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        String productName = "ADIDAS ORIGINAL";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://rahulshettyacademy.com/client");
        LandingPage landingPage = new LandingPage(driver);
        driver.findElement(By.id("userEmail")).sendKeys("gouri.pusapati@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Gouri@31748");
        driver.findElement(By.id("login")).click();
        driver.manage().window().maximize();

        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));


        WebElement prod = products.stream().filter(product ->
                product.findElement(By.cssSelector("b")).getText().equals("ADIDAS ORIGINAL")).findFirst().orElse(null);
        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
        Thread.sleep(3000);
        // WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        //  wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));

        driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();

        List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
        Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(match);

        driver.findElement(By.cssSelector(".totalRow button")).click();

        Actions a = new Actions(driver);
        a.sendKeys(driver.findElement(By.cssSelector("input[placeholder='Select Country']")), "india").build().perform();

        Thread.sleep(3000);

        driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
        driver.findElement(By.cssSelector(".action__submit")).click();

        Thread.sleep(2000);

        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        driver.close();
    }
}

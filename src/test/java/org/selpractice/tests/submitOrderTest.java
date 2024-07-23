package org.selpractice.tests;

import com.fasterxml.jackson.databind.ser.Serializers;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.selpractice.testcomponent.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.internal.BaseTestMethod;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public class submitOrderTest extends BaseTest {

    String productName = "ADIDAS ORIGINAL";


    //@Test(dataProvider = "getData", groups = "Purchase")
    @Test(dataProvider = "getData", groups = "Purchase")
    public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {
        {

            ProductCatalogue productCatalogue = landingPage.loginApplication
                    (input.get("email"), input.get("password"));
            List<WebElement> products = productCatalogue.getProductList();
            productCatalogue.addProductToCart(input.get("product"));
            CartPage cartPage = productCatalogue.goToCartPage();
            Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
            Assert.assertTrue(match);
            CheckoutPage checkoutPage = cartPage.goToCheckout();
            checkoutPage.selectCountry("india");
            ConfirmationPage confirmationPage = checkoutPage.submitOrder();
            String confirmMessage = confirmationPage.getConfirmationMessage();
            Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        }
    }

    @Test(dependsOnMethods = {"submitOrder"})
    public void ordersHistory() {
        ProductCatalogue productCatalogue = landingPage.loginApplication
                ("gouri.pusapati@gmail.com", "Gouri@31748");
        OrderPage orderPage = productCatalogue.goToOrdersPage();
        Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
        System.out.println(productName);

    }


    @DataProvider
    public Object[][] getData() throws IOException {

        /*HashMap<String , String> map = new HashMap<String , String>();
        map.put("email" , "gouri.pusapati@gmail.com");
        map.put("password" , "Gouri@31748");
        map.put("product" , "ADIDAS ORIGINAL");


        HashMap<String , String> map1 = new HashMap<String , String>();
        map1.put("email" , "gouri.raju@gmail.com");
        map1.put("password" , "Praveen@1234");
        map1.put("product" , "IPHONE 13 PRO");
*/
        List<HashMap<String, String>> data = getDataFromJson("src/test/java/Data/Purchase.json");
        return new Object[][]{{data.get(0)}, {data.get(1)}};
    }


    //return new Object[][]{
    //       {"gouri.pusapati@gmail.com", "Gouri@31748", "ADIDAS ORIGINAL"},
    //       {"gouri.raju@gmail.com" , "Praveen@1234" , "IPHONE 13 PRO"}
}

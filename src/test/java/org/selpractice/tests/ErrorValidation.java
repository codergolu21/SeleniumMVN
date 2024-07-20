package org.selpractice.tests;

import org.openqa.selenium.WebElement;
import org.selpractice.testcomponent.CartPage;
import org.selpractice.testcomponent.CheckoutPage;
import org.selpractice.testcomponent.ConfirmationPage;
import org.selpractice.testcomponent.ProductCatalogue;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class ErrorValidation extends BaseTest {

    @Test(groups = "ErrorHandling",retryAnalyzer = Retry.class)
    public void loginErrorValidation() throws IOException, InterruptedException {
        {
            String productName = "ADIDAS ORIGINAL";
            landingPage.loginApplication("gouri.pusapati@gmail.net", "Gouri@31748");
            Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());

        }
    }

    @Test
    public void productErrorValidation() throws IOException, InterruptedException {
        {
            String productName = "ADIDAS ORIGINAL";
            ProductCatalogue productCatalogue = landingPage.loginApplication
                    ("gouri.pusapati@gmail.com", "Gouri@31748");
            List<WebElement> products = productCatalogue.getProductList();
            productCatalogue.addProductToCart(productName);
            CartPage cartPage = productCatalogue.goToCartPage();
            Boolean match = cartPage.VerifyProductDisplay(productName);
            Assert.assertTrue(match , "Didn't match");
        }

    }
}

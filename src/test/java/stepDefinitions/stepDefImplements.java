package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.selpractice.testcomponent.*;
import org.selpractice.tests.BaseTest;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

public class stepDefImplements extends BaseTest {
    public ConfirmationPage confirmationPage;
    public ProductCatalogue productCatalogue;
    public LandingPage landingPage;

    @Given("Land to login page through URL")
    public void land_to_login_page() throws IOException {
        landingPage = launchApplication();
    }

    @Given("login through {} and {}")
    public void login_through_email_password(String email, String password) {
        productCatalogue = landingPage.loginApplication
                (email, password);
    }

    @When("^click on (.+) and add to cart$")
    public void click_on_productName_add_to_cart(String productName) throws InterruptedException {
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
    }

    @And("^Checkout (.+) and submit order$")
    public void checkout_and_submit_order(String productName) {
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.VerifyProductDisplay(productName);
        Assert.assertTrue(match);
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("india");
        confirmationPage = checkoutPage.submitOrder();
    }

    @Then("{string} is displayed on Confirmation page")
    public void message_displayed_on_confirmation_page(String success) {
        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase(success));
    }
    @Then("{string} is displayed")
    public void message_displayed(String success) {
        Assert.assertEquals(success, landingPage.getErrorMessage());
        driver.close();
    }




}

package org.selpractice.testcomponent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends AbstractComponent {

    WebDriver driver;

    public LandingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver , this);
    }

    @FindBy(id = "userEmail")
    WebElement text_userEmail;

    @FindBy(id = "userPassword")
    WebElement text_userPassword;

    @FindBy(id = "login")
    WebElement button_submit;

    @FindBy(xpath ="//div[@aria-label='Incorrect email or password.']")
    WebElement errorMessage;



    public ProductCatalogue loginApplication(String email , String password)
    {
        text_userEmail.sendKeys(email);
        text_userPassword.sendKeys(password);
        button_submit.click();
        ProductCatalogue productCatalogue = new ProductCatalogue(driver);
        return productCatalogue;
    }

    public void goTo()
    {
        driver.get("https://rahulshettyacademy.com/client");
    }

    public String getErrorMessage(){
        waitForWebElementToAppear(errorMessage);
       return errorMessage.getText();
    }

}
//div[@aria-label='Incorrect email or password.']
package org.selpractice.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.selpractice.testcomponent.LandingPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;


public class BaseTest {
    public WebDriver driver;
    public LandingPage landingPage;

    public WebDriver initializeDriver() throws IOException {

        Properties prop = new Properties();
        FileInputStream fs = new FileInputStream("src/main/java/resources/GlobalData.properties");
        prop.load(fs);
        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("BROWSER");
        System.out.println(browserName);
        String headless = System.getProperty("headless") != null ? System.getProperty("headless") : prop.getProperty("HEADLESS");
        if (browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
                WebDriverManager.chromedriver().setup();
                if (headless.equalsIgnoreCase("true")) {
                    options.addArguments("headless");
                }
                driver = new ChromeDriver(options);
                driver.manage().window().setSize(new Dimension(1440, 900));
            } else if (browserName.equalsIgnoreCase("firefox")) {
                System.out.println("FireFox Driver");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            } else if (browserName.equalsIgnoreCase("edge")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
            return driver;
        }

        public List<HashMap<String, String>> getDataFromJson (String filepath) throws IOException {
            String jsonContent = FileUtils.readFileToString(new File(filepath));

            ObjectMapper mapper = new ObjectMapper();
            List<HashMap<String, String>> stringData = mapper.readValue(jsonContent,
                    new TypeReference<List<HashMap<String, String>>>() {
                    });
            return stringData;
        }

        public String getScreenshot (String testCaseName, WebDriver driver) throws IOException {
            TakesScreenshot snap = (TakesScreenshot) driver;
            File source = snap.getScreenshotAs(OutputType.FILE);
            String screenshot = System.getProperty("user.dir") + "/src/test/Results/" + testCaseName + ".png";
            File file = new File(screenshot);
            FileUtils.copyFile(source, file);
            return screenshot;

        }


        @BeforeMethod(alwaysRun = true)
        public LandingPage launchApplication () throws IOException {
            driver = initializeDriver();
            landingPage = new LandingPage(driver);
            landingPage.goTo();
            return landingPage;
        }

        @AfterMethod(alwaysRun = true)
        public void endingCode () {
            driver.close();
        }


    }


package duco;

import com.codeborne.selenide.Configuration;
import duco.pages.HomePage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import static com.codeborne.selenide.Selenide.*;

public class BaseTest {

    protected static final String TEST = "test";

    @BeforeSuite
    public void setUp() {
        Configuration.headless = true;
        Configuration.baseUrl = "https://www.google.com/";
    }

    @BeforeMethod
    public static void openPage() {
        open("");
        HomePage.acceptCookies();
    }

    @AfterMethod
    public void closeBrowser() {
        closeWebDriver();
    }

}

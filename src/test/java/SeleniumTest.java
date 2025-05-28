import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import java.util.*;  

import java.net.URL;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;


public class SeleniumTest {
    public WebDriver driver;
    public MainPage mainPage;
    
    @Before
    public void setup()  throws MalformedURLException  {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();

        mainPage = new MainPage(driver);
    }

    @Test
    public void titleTest(){
        assertTrue(mainPage.getTitleText().contains("Hervis | Főoldal"));
    }
    
    @Test
    public void testSearch() {
        SearchResultPage searchResultPage = mainPage.search("Nike");
        String bodyText = searchResultPage.getBodyText();
        Assert.assertTrue(bodyText.contains("Nike"));
        Assert.assertTrue(bodyText.contains("TERMÉKEK"));
        Assert.assertTrue(bodyText.contains("futónadrág"));
        Assert.assertTrue(bodyText.contains("Kulacs"));
    }
    
    @Test
    public void testSearch2() {
        String[] searchQueries={"Adidas","futó cipő"};  
        for(String searchQuery : searchQueries) {
            MainPage mainP = new MainPage(driver);
            SearchResultPage searchResultPage = mainP.search(searchQuery);
            String bodyText = searchResultPage.getBodyText();
            Assert.assertTrue(bodyText.contains("cipő"));
            Assert.assertTrue(bodyText.contains("FT"));
        }
    }

    @Test
    public void testCorrectLogin(){
        Properties props = new Properties();
        //config.properties file ignored by git
        try {
            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String username = props.getProperty("username");
        String password = props.getProperty("password");

        LoginResultPage loginResultPage = mainPage.login(username, password);
        String bodyText = loginResultPage.getBodyText();
        Assert.assertTrue(bodyText.contains("Fiókom"));
    }
    

    
    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}

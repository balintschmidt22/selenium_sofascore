import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.util.*;  
import java.net.URL;
import static org.junit.Assert.assertTrue;
import java.io.FileInputStream;
import java.net.MalformedURLException;


public class SeleniumTest {
    public WebDriver driver;
    public MainPage mainPage;
    private String username;
    private String password;
    
    @Before
    public void setup()  throws MalformedURLException  {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        mainPage = new MainPage(driver);

        Properties props = new Properties();
        //config.properties file ignored by git
        try {
            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        username = props.getProperty("username");
        password = props.getProperty("password");
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
        LoginResultPage loginResultPage = mainPage.login(username, password);
        String bodyText = loginResultPage.getBodyText();
        Assert.assertTrue(bodyText.contains("Fiókom"));
    }

    @Test
    public void testLogout(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        LogoutResultPage logoutResultPage = loginPage.logout();
        Assert.assertEquals(logoutResultPage.getTitleText(), "Hervis | Főoldal");
        String bodyText = logoutResultPage.getBodyText();
        Assert.assertTrue(bodyText.contains("SportsClub Fiókom"));
    }

    @Test
    public void testShoppingCart(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        ProductPage productPage = new ProductPage(driver, "Felszerel%C3%A9s/Fitneszg%C3%A9pek-%C3%A9s-kieg%C3%A9sz%C3%ADt%C5%91k/Kieg%C3%A9sz%C3%ADt%C5%91k/Essential/p/COLOR-3317298");

        productPage.putInShoppingCart("M");
        Assert.assertTrue(Integer.parseInt(productPage.value) >= 1);
    }

    @Test
    public void testFaqPage(){
        FaqPage faqPage = new FaqPage(driver);
        Assert.assertTrue(faqPage.getTitleText().contains("Hervis gyakran ismételt kérdések"));
        Assert.assertFalse(faqPage.getFaqItems().isEmpty());
    }
    
    
    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}

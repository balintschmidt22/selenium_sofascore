import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;


class MainPage extends PageBase {
    private By myAccountBy = By.xpath("//div[@class='my-account__top']");
    private By searchBarBy = By.xpath("//input[@type='search' and @placeholder='Termékkeresés...']");
    private By usernameBy = By.id("gigya-loginID-28776771034217040");
    private By passwordBy = By.id("gigya-password-152373988132129340");
    private By loginBy = By.cssSelector("input[type='submit'][value='Bejelentkezés']");

    public MainPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.hervis.hu/shop/");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement shadowHost = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cmpwrapper")));

        WebElement acceptButton = (WebElement) js.executeScript(
            "return arguments[0].shadowRoot.querySelector('a.cmpboxbtnyes')", shadowHost
        );

        if(acceptButton != null){
            acceptButton.click();
        }

        try
        {
            Thread.sleep(2000);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public String getTitleText() {
        return driver.getTitle();
    }
    
    public SearchResultPage search(String searchQuery) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBarBy));

        searchInput.click();

        searchInput.sendKeys(searchQuery + "\n");

        return new SearchResultPage(this.driver);
    }

    public LoginResultPage login(String username, String password){
        this.waitAndReturnElement(myAccountBy).click();

        WebDriverWait wait = new WebDriverWait(driver, 15);

        WebElement usernameInput = wait.until(ExpectedConditions.elementToBeClickable(usernameBy));
        usernameInput.sendKeys(username);

        WebElement passwordInput = wait.until(ExpectedConditions.elementToBeClickable(passwordBy));
        passwordInput.sendKeys(password);

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(loginBy));
        loginButton.click();
        
        return new LoginResultPage(this.driver);
    }
}

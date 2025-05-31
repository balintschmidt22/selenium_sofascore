import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;


class LoginPage extends PageBase {
    private By myAccountBy = By.xpath("//span[@id='tosca-headerLogin']");
    private By usernameBy = By.id("gigya-loginID-28776771034217040");
    private By passwordBy = By.id("gigya-password-152373988132129340");
    private By loginBy = By.cssSelector("input[type='submit'][value='Bejelentkezés']");
    private By logoutBy = By.xpath("//a[@href='/store/logout']");

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.hervis.hu/store/login");

        try{
            WebDriverWait wait = new WebDriverWait(driver, 8);
            WebElement shadowHost = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cmpwrapper")));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            WebElement acceptButton = (WebElement) js.executeScript(
                "return arguments[0].shadowRoot.querySelector('a.cmpboxbtnyes')", shadowHost
            );
            
            if (acceptButton != null) {
                acceptButton.click();
            }
        }
        catch (Exception e) {
            
        }
    }

    public LoginResultPage login(String username, String password){
        WebDriverWait wait = new WebDriverWait(driver, 15);

        WebElement usernameInput = wait.until(ExpectedConditions.elementToBeClickable(usernameBy));
        usernameInput.sendKeys(username);

        WebElement passwordInput = wait.until(ExpectedConditions.elementToBeClickable(passwordBy));
        passwordInput.sendKeys(password);

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(loginBy));
        loginButton.click();
        
        return new LoginResultPage(this.driver);
    }

    public LogoutResultPage logout(){
        WebDriverWait wait = new WebDriverWait(driver, 15);

        WebElement myAccount = wait.until(ExpectedConditions.elementToBeClickable(myAccountBy));
        myAccount.click();

        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(logoutBy));
        logoutButton.click();

        return new LogoutResultPage(this.driver);
    }
}

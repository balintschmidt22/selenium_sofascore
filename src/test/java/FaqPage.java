import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import org.openqa.selenium.By;


class FaqPage extends PageBase {
    private By pageTitle = By.xpath("//div[contains(@class, 'paragraphHeader')]");
    private By faqItems = By.className("MsoNormal");

    public FaqPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.hervis.hu/store/faq");

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

    public String getTitleText() {
        return driver.findElement(pageTitle).getText();
    }

    public List<WebElement> getFaqItems() {
        return driver.findElements(faqItems);
    }
}

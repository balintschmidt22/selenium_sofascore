import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;


class ProductPage extends PageBase {
    private By selectBy = By.xpath("//select");
    private By addToCartBy = By.xpath("//hv-add-to-cart[button]");
    private By cartBy = By.xpath("//div[contains(@class, 'mini-cart__amount')]");
    public String productInCart = "";

    public ProductPage(WebDriver driver, String product) {
        super(driver);
        this.driver.get("https://www.hervis.hu/shop/" + product);

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

    public ProductResultPage putInShoppingCart(String size){
        WebDriverWait wait = new WebDriverWait(driver, 15);

        WebElement select = wait.until(ExpectedConditions.elementToBeClickable(selectBy));
        Select dropdown = new Select(select);
        dropdown.selectByVisibleText(size);

        WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(addToCartBy));
        addToCart.click();

        WebElement cart = wait.until(ExpectedConditions.elementToBeClickable(cartBy));
        productInCart = cart.getText();
        
        return new ProductResultPage(driver); 
    }
}

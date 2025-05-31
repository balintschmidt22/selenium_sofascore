import org.openqa.selenium.WebDriver;


class LogoutResultPage extends PageBase {

    public LogoutResultPage(WebDriver driver) {
        super(driver);
    }    

    public String getTitleText() {
        return driver.getTitle();
    }
           
}
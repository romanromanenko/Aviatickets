package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchResultPage {

    private By regionChange = By.xpath("//div[@class='region-change']/a");
    private By fromInput = By.name("fromName");
    private By toInput = By.name("toName");
    private By whenInput = By.name("when");
    private By fromInputClear = By.xpath("//div[input[@name='when']]/span");
    private By whenDataHolder = By.xpath("//div[input[@name='when']]/input");
    private By searchTicket = By.xpath("//a[contains(@class, 'find-offers')]");
    private By moreLabel = By.xpath("//div[contains(@class, 'navigation__more-label')]");

    private WebDriver driver;

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getCurrentRegion() {

        WebElement regionChangeLable = driver.findElement(regionChange);
        return regionChangeLable.getText();
    }

    public String getFromText(){

        WebElement fromInputField = driver.findElement(fromInput);
        return fromInputField.getAttribute("value");

    }

    public String getToPlaceholder(){
        WebElement toField = driver.findElement(By.name("toName"));
        return toField.getAttribute("value");

    }

    public String getwhenDate(){

        WebElement whenInputField = driver.findElement(whenInput);
        return whenInputField.getAttribute("value");
    }

    public boolean isFromInputClearDisplate (){

        return driver.findElement(fromInputClear).isDisplayed();
    }

    public String getWhenDataHolder(){

        return driver.findElement(whenDataHolder).getAttribute("value");
    }

    public boolean isSearchTicketButtonDisplayd(){

        return driver.findElement(searchTicket).isDisplayed();
    }

 //   public boolean

}

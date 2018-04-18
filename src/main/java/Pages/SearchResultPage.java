//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchResultPage {
    private By regionChange = By.xpath("//div[@class='region-change']/a");
    private By fromInput = By.name("fromName");
    private By toInput = By.name("toName");
    private By whenInput = By.name("when");
    private By fromInputClear = By.xpath("//div[input[@name='when']]/span");
    private By whenDateHolder = By.xpath("//div[input[@name='when']]/input");
    private By searchTicket = By.xpath("//a[contains(@class, 'find-offers')]");
    private By moreLabel = By.xpath("//div[contains(@class, 'navigation__more-label')]");
    private By switchElement = By.xpath("//div[@class='geo-route__switcher']");
    private WebDriver driver;

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getCurrentRegion() {
        WebElement regionChangeLabel = this.driver.findElement(this.regionChange);
        return regionChangeLabel.getText();
    }

    public String getFromText() {
        WebElement fromInputField = this.driver.findElement(this.fromInput);
        return fromInputField.getAttribute("value");
    }

    public String getToPlaceholder() {
        WebElement toField = this.driver.findElement(this.toInput);
        return toField.getAttribute("value");
    }

    public String getWhenDate() {
        WebElement whenInputField = this.driver.findElement(this.whenInput);
        return whenInputField.getAttribute("value");
    }

    public boolean isFromInputClearButtonDisplayed() {
        return this.driver.findElement(this.fromInputClear).isDisplayed();
    }

    public String getWhenDateHolder() {

        return this.driver.findElement(this.whenDateHolder).getAttribute("value");
    }

    public boolean isSearchTicketButtonDisplayed() {

        return this.driver.findElement(this.searchTicket).isDisplayed();
    }

    public boolean isSwitchButtonDisplayed() {

        return this.driver.findElement(this.switchElement).isDisplayed();
    }

    public void clearFromInputByBackspace() {
        WebElement fromInputField = this.driver.findElement(this.fromInput);
        String fromValue = fromInputField.getAttribute("value");
        int numberOfLetters = fromValue.length();

        for(int i = 0; i < numberOfLetters; ++i) {
            fromInputField.sendKeys(new CharSequence[]{Keys.BACK_SPACE});
        }

    }

    public boolean isMoreLabelDisplayed() {

        return this.driver.findElements(this.moreLabel).size() > 0;
    }
}

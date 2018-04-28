//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Pages;


import elements.Calendar;
import org.openqa.selenium.*;

public class SearchResultPage {
    private By regionChange = By.xpath("//div[@class='region-change']/a");
    private By fromInput = By.name("fromName");
    private By toInput = By.name("toName");
    private By whenInput = By.name("when");
    private By whenInputClear = By.xpath("//div[input[@name='when']]/span");
    private By whenDateHolder = By.xpath("//div[input[@name='when']]/input");
    private By searchTicket = By.xpath("//a[contains(@class, 'find-offers')]");
    private By moreLabel = By.xpath("//div[contains(@class, 'navigation__more-label')]");
    private By switchElement = By.xpath("//div[@class='geo-route__switcher']");
    private By fromInputClear = By.xpath("//span[input[@name='fromName']]/span");
    private By fromCityDropDown = By.xpath("//div[contains(@data-bem, 'from')]/div[contains(@class, 'suggest2__content')]");
    private By body = By.tagName("body");
    private WebDriver driver;
    private By toInputClear = By.xpath("//span[input[@name='toName']]/span");
    private By whenButton = By.xpath("//div[input[@name='when']]/button");
    private By returnButton = By.xpath("//div[input[@name='return_date']]/button");
    private By whenCalendar = By.xpath("//div[contains(@class, 'when')]");
    private By returnCalendar = By.xpath("//div[contains(@class, 'return')]");

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
        return this.driver.findElement(this.whenInputClear).isDisplayed();
    }

    public String getWhenDateHolder() { return this.driver.findElement(this.whenDateHolder).getAttribute("value"); }

    public boolean isSearchTicketButtonDisplayed() { return this.driver.findElement(this.searchTicket).isDisplayed(); }

    public boolean isSwitchButtonDisplayed() { return this.driver.findElement(this.switchElement).isDisplayed(); }

    public void clearFromInputByBackspace() {
        WebElement fromInputField = this.driver.findElement(this.fromInput);
        String fromValue = fromInputField.getAttribute("value");
        int numberOfLetters = fromValue.length();

        for(int i = 0; i < numberOfLetters; ++i) {
            fromInputField.sendKeys(new CharSequence[]{Keys.BACK_SPACE});
        }
    }

    public boolean isMoreLabelDisplayed() { return this.driver.findElements(this.moreLabel).size() > 0; }

    public void clickClearButton() {
        WebElement fromInputClearButon = driver.findElement(fromInputClear);
        fromInputClearButon.click();
    }

    public void clickFromField() { driver.findElement(fromInput).click(); }

    public String getDropDownTest() { return driver.findElement(fromCityDropDown).getText(); }

    public void clickToInput() { driver.findElement(toInput).click(); }

    public void sendKeys(String value) { driver.findElement(toInput).sendKeys(value); }

    public void clearFromeField() { driver.findElement(fromInput).clear(); }

    public void fillFromField(String value) { driver.findElement(fromInput).sendKeys(value); }

    public void inputStrinpInField(String value){ driver.findElement(fromInput).sendKeys(value); }

    public void inputNameCity(String value){ driver.findElement(fromInput).sendKeys(value); }

    public void clearFieldWhereByButton(String value){
        driver.findElement(toInput).sendKeys(value);
        driver.findElement(toInputClear).click();
    }

    public void inputNoCity(){

        String currentURL = null;

        driver.findElement(fromInput).sendKeys("nocity1");
        driver.findElement(toInput).sendKeys("nocity2");
        currentURL = driver.getCurrentUrl();
        System.out.println(currentURL);
    }

    public void inputName(){
        driver.findElement(fromInput).clear();
        driver.findElement(fromInput).sendKeys("МаХачКала");
        driver.findElement(toInput).sendKeys("МинсК");
    }

    public void scrollToFromField() {
        WebElement fromField = driver.findElement(fromInput);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", fromField);
    }


    public void clicWhenButton() {
        driver.findElement(whenButton).click();
    }

    public void worcingWithCalendar() {
        WebElement whenCalendarField = driver.findElement(whenCalendar);
        Calendar whenCalendar = new Calendar(whenCalendarField);

        System.out.println(whenCalendar.getCalendarTitle());
        whenCalendar.goToNextMonth();
        System.out.println(whenCalendar.getCalendarTitle());
        whenCalendar.goToPreviouseMontht();
        System.out.println(whenCalendar.getCalendarTitle());
    }

    public void clicreturnButton() {
        driver.findElement(returnButton).click();
    }
}

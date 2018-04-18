import helpers.helper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import Pages.MainPage;
import Pages.SearchResultPage;

import java.util.HashMap;
import java.util.Map;

public class YandexTestObject {
    private WebDriver driver;

    private MainPage mainPage;
    private SearchResultPage searchResultPage;

    @BeforeMethod(alwaysRun = true)
    @Parameters("Browser")
    public void start(@Optional String browserName){
        if (browserName == null) {
            browserName = "chrome";
        }

        if (browserName.contentEquals("chrome")) {
            driver = new ChromeDriver();
        } else  if (browserName.contentEquals("mobile")) {
            Map<String, String> mobileEmulation = new HashMap<String, String>();
            mobileEmulation.put("deviceName", "Galaxy S5");

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

            driver = new ChromeDriver(chromeOptions);
        } else if (browserName.contentEquals("ff")) {
            driver = new FirefoxDriver();
        } else if (browserName.contentEquals("ie")) {
            driver = new InternetExplorerDriver();
        } else {
            throw new IllegalArgumentException("Unknown browser");
        }

        mainPage = new MainPage(driver);
        searchResultPage = new SearchResultPage(driver);

        driver.get("https://www.yandex.by/");

        mainPage.search("Авиабилеты");
    }

    @AfterMethod
    public void finish(){
        driver.quit();
    }


    @Test
    public void verifySearchInAddress() {
        String currentURL = driver.getCurrentUrl();

        Assert.assertTrue(currentURL.contains("search"), "Search result page should be opened");
    }

    @Test
    public void defaultLocationInFromField(){
        Assert.assertEquals(searchResultPage.getFromText(),
                searchResultPage.getCurrentRegion(), "Values should be the same");
    }

    @Test(description = "Check placeholder")
    public void toPlaceholderVerifying() {
        Assert.assertTrue(searchResultPage.getToPlaceholder().isEmpty(), "Placeholder is visible");
    }

    @Test
    public void whenDefaultDateCheck(){
        String defaultDate = searchResultPage.getWhenDate();
        String expectedDate = helper.getCurrentDate("yyyy-MM-dd", 3);

        Assert.assertEquals(defaultDate, expectedDate, "Default date should be correct");
    }

    @Test(groups = {"search", "regression"})
    public void whenHasClearButton() {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(searchResultPage.isFromInputClearButtonDisplayed(), "When input should have clear button");
        softAssert.assertFalse(searchResultPage.getWhenDateHolder().isEmpty(), "When input shouldn't have calendar icon");

        softAssert.assertAll();
    }

    @Test(groups = {"search", "smoke"})
    public void searchTicketButtonIsPresent() {
        Assert.assertTrue(searchResultPage.isSearchTicketButtonDisplayed(), "Search ticket button should be displayed");
    }

//    @Test
//    public void switchButtonIsDisplayed() {
//        if (!isDesktop()) {
//            throw new SkipException("Doesn't work for mobile");
//        }
//        WebElement switchButton = driver.findElement(By.xpath("//div[@class='geo-route__switcher']"));
//        Assert.assertTrue(switchButton.isDisplayed(), "Switch button should be displayed");
//    }

    @Test
    public void switchButtonIsDisplayed() {
        if (!isDesktop()) {
            throw new SkipException("Doesn't work for mobile");
        }
        Assert.assertTrue(searchResultPage.isSwitchButtonDisplayed(), "Switch button should be displayed");
    }

//    @Test
//    public void clearFromByBackSpace(){
//        WebElement fromInputField = driver.findElement(By.name("fromName"));
//        String fromValue = fromInputField.getAttribute("value");
//
//        int numberOfLetters = fromValue.length();
//
//        for (int i = 0; i < numberOfLetters ; i++) {
//            fromInputField.sendKeys(Keys.BACK_SPACE);
//        }
//
//        fromValue = fromInputField.getAttribute("value");
//
//        Assert.assertTrue(fromValue.isEmpty(), "Value should be empty");
//    }

    @Test
    public void clearFromByBackSpace(){
        searchResultPage.clearFromInputByBackspace();

        Assert.assertTrue(searchResultPage.getFromText().isEmpty(), "Value should be empty");
    }

//    public boolean isDesktop() {
//        List<WebElement> moreLabels = driver.findElements(By.xpath("//div[contains(@class, 'navigation__more-label')]"));
//
//        return moreLabels.size() > 0;
//    }

    public boolean isDesktop() {
        return searchResultPage.isMoreLabelDisplayed();
    }



}

import helpers.helper;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import java.util.concurrent.TimeUnit;

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

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

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
        Assert.assertTrue(searchResultPage.isSearchTicketButtonDisplayed(),"Search ticket button should be displayed");
    }

    @Test
    public void switchButtonIsDisplayed() {
        if (!isDesktop()) {
            throw new SkipException("Doesn't work for mobile");
        }
        Assert.assertTrue(searchResultPage.isSwitchButtonDisplayed(), "Switch button should be displayed");
    }

    @Test
    public void clearFromByBackSpace(){
        searchResultPage.clearFromInputByBackspace();

        Assert.assertTrue(searchResultPage.getFromText().isEmpty(), "Value should be empty");
    }

    @Test
    public void clearFromInputByClearButton(){
        searchResultPage.clickClearButton();

        Assert.assertTrue(searchResultPage.getFromText().isEmpty(), "Value should be empty");
    }

    @Test
    public void fromDropDownIsAppear() throws InterruptedException {
        searchResultPage.clickFromField();
        String ddText = searchResultPage.getDropDownTest();
        Assert.assertTrue(ddText.split("\n").length > 0, "Drop down list should contain ");

    }

    @Test
    public void fromDropDownIsAppwaerByTab(){
        searchResultPage.clickToInput();
        searchResultPage.sendKeys(Keys.SHIFT.toString() + Keys.TAB.toString());
    }

    @Test
    public void fromDropDwnWalidation() throws InterruptedException {

       // searchResultPage.clearFromeField();
        searchResultPage.clickClearButton();
        searchResultPage.fillFromField("М");
        Thread.sleep(500);
        searchResultPage.fillFromField("и");
        Thread.sleep(500);
        searchResultPage.fillFromField("н");
        Thread.sleep(500);


        String ddText = searchResultPage.getDropDownTest();

        String[] cities = ddText.split("\n");
        boolean allCitiesAreOk = true;

        for (int i = 1; i <cities.length ; i++) {
            allCitiesAreOk = allCitiesAreOk && cities[i].startsWith("Мин");
        }

        Assert.assertTrue(allCitiesAreOk, "All cities should start with 'Мин'");
    }

    public boolean isDesktop() {
        return searchResultPage.isMoreLabelDisplayed();
    }

}
